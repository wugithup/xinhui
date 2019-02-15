package com.shuto.mam.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.util.MXSession;

public class PItjServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		// 获取参数,由于测试有乱码进行了转码处理
		String type = getParam(req, "xdjtype");
		String id = getParam(req, "professionid");
		String begin = getParam(req, "beginDate");
		String end = getParam(req, "endDate");
		String ycd = getParam(req, "ycd");
		String dbz = getParam(req, "dbz");
		String siteid = getParam(req, "siteid");
		if(siteid==null||"".equals(siteid)||"null".equals(siteid)){
			MXSession session = (MXSession)req.getSession().getAttribute("MXSession");
			siteid = session.getUserInfo().getInsertSite();
		}
		System.out.println("siteid--"+siteid);
		int start = Integer.parseInt(getParam(req, "start"));
		int limit = Integer.parseInt(getParam(req, "limit"));
		System.out.println(type + "|" + id + "|" + begin + "|" + end + "|"
				+ start + "|" + limit + "|ycd:" + ycd);
		if (type != null && id != null && begin != null && end != null) {

			// 查询结果
			Connection conn = getOraclConn();

			try {
				String pro = "";
				if (!"全部".equals(id)) {
					pro = " PROFESSIONAL='" + id + "' and ";
				}
				String shift = "";
				if("点检".equals(type)){
					shift = " SHIFT is null and ";
				}else if (!"全部".equals(dbz)) {
					shift = " SHIFT='" + dbz + "' and ";
				}
				String sql_count = "select count(*) from";
				String sql_select = "select * from ";
				String sql_where = "  where  "+((siteid!=null&&!"".equals(siteid)&&!"null".equals(siteid))?("siteid = '"+siteid+"' and orgid = (select orgid from site where siteid = '"+siteid+"') and "):"")+"" + pro + shift
						+ " task_type='" + type + "' "
						+ "and task_up_time between to_date('" + begin
						+ "','yyyy-MM-dd') and to_date('" + end+" 23:59:59"
						+ "','yyyy-MM-dd HH24:mi:ss')";
				String sql_2 = "";
				if (ycd != null && !"".equals(ycd)
						&& !"null".equalsIgnoreCase(ycd)) {
					sql_2 = "(select rownum-1 as seq,aa.* from  (select * from VIEW_PI_TASK_ITEM " + sql_where + ")aa)";
				} else {
					sql_2 = "(select rownum-1 as seq,aa.* from  (select TASK_TYPE,SHIFT,PROFESSIONAL,round((SUM(REAL_AREA)/SUM(NORM_AREA)*100),2) as IN_PLACE_RATE ,"
							+ "SUM(NORM_AREA) AS NORM_AREA ,SUM(REAL_AREA) AS REAL_AREA,SUM(NORM_POINT) AS NORM_POINT,SUM(REAL_POINT) AS REAL_POINT,"
							+ "round(((SUM(REAL_POINT)+SUM(IGNORE_POINT))/SUM(NORM_POINT)*100),2) AS PI_RATE,SUM(MISSED_POINT) AS MISSED_POINT,"
							+ "round((SUM(MISSED_POINT)/SUM(NORM_POINT)*100),2) AS MISSED_RATE,SUM(YCD) AS YCD from VIEW_PI_TASK "
							+ sql_where
							+ " group by  TASK_TYPE,SHIFT,PROFESSIONAL) aa)";

				}

				String sql = sql_count + sql_2;
				Statement stat = conn.createStatement();
				System.out.println(sql);
				ResultSet rs = stat.executeQuery(sql);
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
					sql = sql_select + sql_2 + " where seq >= "
							+ (start  ) + " and seq <" +(start + limit);
					rs = stat.executeQuery(sql);
					System.out.println(sql);
					String retStr = "{'total':" + count + ",'topics':[";
					// NUM,TASK_UP_TIME,TASK_NAME,PROFESSIONAL,IN_PLACE_RATE,NORM_AREA,REAL_AREA,
					// NORM_POINT,REAL_POINT,PI_RATE,MISSED_POINT,MISSED_RATE,TASK_TYPE
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					
					while (rs.next()) {
						retStr += "{";
						for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
							String name = rs.getMetaData().getColumnName(i);
							if (name.toUpperCase().indexOf("AREA") > 0
									|| name.toUpperCase().indexOf("POINT") > 0) {
								retStr += name + ":" + rs.getInt(name) + ",";
							} else if (name.toUpperCase().indexOf(
									"TASK_UP_TIME") >= 0) {
								retStr += name + ":'"
										+ format.format(rs.getDate(name))
										+ "',";
							} else {
								retStr += name + ":'" + ((rs.getString(name)!=null&&!"".equals(rs.getString(name)))?rs.getString(name):"")
										+ "',";
							}
						}
						retStr = retStr.substring(0, retStr.length() - 1)
								+ "},";
					}
					if (!("{'total':" + count + ",'topics':[").equals(retStr)) {
						retStr = retStr.substring(0, retStr.length() - 1)
								+ "]}";
					} else {
						retStr = "{'total':0,'topics':[]}";
					}
					System.out.println(retStr);
					resp.getWriter().println(retStr);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resp.getWriter().println("");
		} else {
			resp.getWriter().println("");
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	public String getParam(HttpServletRequest req, String param)
			throws UnsupportedEncodingException {
		String ret = req.getParameter(param);
		if (ret == null) {
			return null;
		}
		return URLDecoder.decode(ret, "utf-8");
	}

	public static Connection getOraclConn() {
		Connection conn = null;
		try {
			conn = MXServer.getMXServer().getDBManager()
					.getSequenceConnection();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static Connection getOraclConn2() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.1.109:1521:orcl", "pi", "pi");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
