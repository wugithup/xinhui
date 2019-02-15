package com.shuto.mam.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import psdi.server.DBManager;
import bsh.EvalError;
import bsh.Interpreter;

import com.alibaba.fastjson.JSON;
import com.shuto.mam.servlet.selecttemplate.JqueryXML;
import com.shuto.mam.servlet.selecttemplate.SelectSession;

//import net.sf.json.JSONObject;

public class SelectTemplate extends HttpServlet {
	HashMap<String, HashMap<String, String>> session = new HashMap<String, HashMap<String, String>>();
	SelectSession ss = null;

	static Connection conn = null;

	private Interpreter whereBeanShell = new Interpreter(); // where參數 使用的 bsh主類
															// ,因為實例化需要話費10多毫秒

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ss = new SelectSession(req);
		if (req.getParameter("oplogtype") != null) {
			System.out.println("oplogtype="
					+ java.net.URLDecoder.decode(req.getParameter("oplogtype"),
							"UTF-8"));
			;
		}
		// System.out.println("CharacterEncoding="+req.getCharacterEncoding());;
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Pragma", "No-cache");
		resp.setContentType("text/json;charset=utf-8");
		JqueryXML jxml = new JqueryXML();
		try {
			getOraclConn();

			int page = 1;
			int pageSize = 14;
			pageSize = 1000;
			String selectTemplateTalbe = req
					.getParameter("SelectTemplateTalbe");
			String method = req.getParameter("method");
			// System.out.println("SelectTemplateTalbe="+selectTemplateTalbe);
			if ("exportexcel".equals(method)) {
				String sql = getSql(jxml, selectTemplateTalbe, req);
				if (sql != null) {

					StringBuffer csvBuffer = expExcel(sql, req);
					resp.setContentType("application/x-download;charset=gbk");
					resp.setHeader("Content-Disposition",
							"attachment;filename=" + System.currentTimeMillis()
									+ ".csv");
					resp.getWriter().write(csvBuffer.toString());
					// System.out.println(csvBuffer.toString());
				}
			} else {
				Map dataMap = new HashMap();

				String sql = getSql(jxml, selectTemplateTalbe, req);
				if (sql != null) {

					System.out.println("sql=" + sql);
					// System.out.println("page=" + getParameter(req,"page"));
					// System.out.println("rows=" + getParameter(req,"rows"));
					if (getParameter(req, "page") != null) {
						page = Integer.parseInt(getParameter(req, "page"));
					}
					if (getParameter(req, "rows") != null) {
						pageSize = Integer.parseInt(getParameter(req, "rows"));
					}
					dataMap = getData2(sql, page, pageSize, req);
					String obj = JSON.toJSONString(dataMap);

					// req.getSession().setAttribute("dataMap", dataMap);
					resp.getWriter().write(obj);
					// System.out.println("json="+obj.toString());
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getParameter(ServletRequest req, String arg0) {
		String par = "";
		String stType = req.getParameter("SelectTemplateType");
		HashMap<String, String> stSessionMap = ss.getstSessionMap();
		par = req.getParameter(arg0);
		System.out.println(arg0 + ".1=" + par);
		try {
			if (par != null)
				par = java.net.URLDecoder.decode(par, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(arg0 + ".2=" + par);
		if (ss.isNew()) {
			stSessionMap.put(arg0, par);
		} else {
			if ("init".equalsIgnoreCase(stType)) {
				if ("order".equals(arg0) || "sort".equals(arg0)) {
					stSessionMap.put(arg0, par);
				} else {
					if (!"page".equals(arg0) && !"rows".equals(arg0)) {
						par = stSessionMap.get(arg0);
					} else {
						stSessionMap.put(arg0, par);
					}

				}
			} else {
				if ("order".equals(arg0) || "sort".equals(arg0)) {
					par = stSessionMap.get(arg0);
				} else {
					if (!"page".equals(arg0) && !"rows".equals(arg0)) {
						stSessionMap.put(arg0, par);
					} else {
						par = stSessionMap.get(arg0);
					}
				}
			}
		}

		// System.out.println("return par11="+par);
		return par;
	}

	public String getSql(JqueryXML jxml, String selectTemplateTalbe,
			ServletRequest req) throws EvalError {

		// 會話唯一標識
		String stType = req.getParameter("SelectTemplateType");
		// System.out.println("stType="+stType);
		HashMap<String, String> stSessionMap = ss.getstSessionMap();

		String sql = null;
		StringBuffer whereSB = null;
		if (jxml.setSelectTemplate(selectTemplateTalbe)) {
			sql = jxml.getSql();
			Map<String, String> whereMap = jxml.getWhere();
			Set<String> keySet = whereMap.keySet();
			for (String string : keySet) {
				String par = getParameter(req, string);
				if (ss.isNew()) {
					stSessionMap.put(string, par);
				} else {
					if ("init".equalsIgnoreCase(stType)) {
						par = stSessionMap.get(string);
					} else {
						stSessionMap.put(string, par);
					}
				}
				String bsh = whereMap.get(string);
				// System.out.println("parameter="+string);
				// System.out.println("par="+par);
				if (bsh != null && !bsh.isEmpty()) {
					whereBeanShell.set("p", par);
					Object eval = whereBeanShell.eval(bsh);
					// System.out.println("eval="+eval);
					if (eval != null) {
						if (whereSB == null) {
							whereSB = new StringBuffer(eval.toString());
						} else {
							whereSB.append(" and ").append(eval);
						}
					}
				}
			}
			if (whereSB != null) {
				sql += " where " + whereSB.toString();
			}
		}
		return sql;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// System.out.println("characterEncoding="+req.getCharacterEncoding());
		// req.setCharacterEncoding("UTF-8");
		// resp.setContentType("text/html;charset=UTF-8");
		// String a = new
		// String(req.getParameter("sql").getBytes("ISO8859_1"),"UTF-8");
		// System.out.println(a);
		// JSONObject obj = JSONObject.fromObject(req.getParameterMap());
		// System.out.println(obj.toString());
		doGet(req, resp);
	}

	/**
	 * 排序 ResultSet分页
	 * 
	 * @param sql
	 * @param page
	 * @param pageSize
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public Map getData2(String sql, int page, int pageSize,
			HttpServletRequest req) throws Exception {

		// JSONObject obj = JSONObject.fromObject(req.getParameterMap());
		// System.out.println(obj.toString());

		String orderBy = "";
		String sort = getParameter(req, "sort");
		String order = getParameter(req, "order");
		if (sort != null && order != null) {
			orderBy = " order by " + sort + " " + order;
		}
		// System.out.println("sort=" + sort);
		// System.out.println("order=" + order);

		List tasks = new ArrayList();
		String queySql = "";
		queySql = "select * from (" + sql + ")  " + orderBy;
		// System.out.println(queySql);

		Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = st.executeQuery(queySql);
		int row = (page - 1) * pageSize;
		int count = 0;
		rs.last();
		count = rs.getRow();
		// System.out.println("今ろう＝" + row + ",总ろう=" + count);
		if (row == 0) {
			rs.beforeFirst();
		} else {
			rs.absolute(row);
		}
		ResultSetMetaData rsmd = rs.getMetaData();
		int column = rsmd.getColumnCount();
		for (int j = 0; j < pageSize && rs.next(); j++) {
			Map rowMap = new HashMap();
			for (int i = 0; i < column; i++) {
				String columnName = rsmd.getColumnName(i + 1);
				// System.out.println("columnName:" + columnName.toLowerCase() +
				// "=" + rs.getString(columnName));
				rowMap.put(columnName.toLowerCase(), rs.getString(columnName));

			}
			tasks.add(rowMap);
		}
		Map dataMap = new HashMap();
		rs.close();
		st.close();

		dataMap.put("total", String.valueOf(count));
		dataMap.put("rows", tasks);
		return dataMap;
	}

	private StringBuffer expExcel(String sql, HttpServletRequest req)
			throws SQLException {
		// TODO Auto-generated method stub
		String orderBy = "";
		String sort = getParameter(req, "sort");
		String order = getParameter(req, "order");
		if (sort != null && order != null) {
			orderBy = " order by " + sort + " " + order;
		}
		String queySql = "";
		queySql = "select * from (" + sql + ")  " + orderBy;

		Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = st.executeQuery(queySql);
		int count = 0;
		rs.last();
		count = rs.getRow();
		rs.beforeFirst();
		StringBuffer csvBuffer = new StringBuffer();
		ResultSetMetaData rsmd = rs.getMetaData();
		int column = rsmd.getColumnCount();
		for (int i = 0; i < column; i++) {
			String douhao = i + 1 == column ? "\n\n" : ",";
			csvBuffer.append(rsmd.getColumnName(i + 1)).append(douhao);
		}
		for (int j = 0; rs.next(); j++) {
			for (int i = 0; i < column; i++) {
				String douhao = i + 1 == column ? "\n" : ",";
				String columnName = rsmd.getColumnName(i + 1);
				String data = rs.getString(columnName) == null ? "" : rs
						.getString(columnName);
				csvBuffer.append(data).append(douhao);

			}
		}
		rs.close();
		st.close();
		return csvBuffer;
	}

	public static Connection getOraclConn() {
		try {
			DBManager dbManager = null;
			try {
				if (conn == null || conn.isClosed()) {
					dbManager = psdi.server.MXServer.getMXServer()
							.getDBManager();
					if (dbManager != null) {
						// System.out.println("dbManager="+dbManager);
						conn = dbManager.getSequenceConnection();
					} else {
						getOraclConnJdbc();
					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				dbManager = psdi.server.MXServer.getMXServer().getDBManager();
				// System.out.println("dbManager="+dbManager);
				if (dbManager != null) {
					conn = dbManager.getSequenceConnection();
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	public static Connection getOraclConnJdbc() {
		try {
			if (conn == null || conn.isClosed()) {
				DataSource ds = null;
				try {
					InitialContext ctx = new InitialContext();
					ds = (DataSource) ctx.lookup("java:comp/env/jdbc/druid");
					conn = ds.getConnection();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			DataSource ds = null;
			try {
				InitialContext ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/druid");
				conn = ds.getConnection();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return conn;
	}
}