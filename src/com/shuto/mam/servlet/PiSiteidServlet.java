package com.shuto.mam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import psdi.server.MXServer;
import psdi.util.MXSession;

public class PiSiteidServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
		//获得Maximo当前用户的siteid及orgid
			MXSession session = (MXSession)req.getSession().getAttribute("MXSession");
			String siteid = session.getUserInfo().getInsertSite();
			PrintWriter pw   = resp.getWriter() ;
			Connection conn = getOraclConn();
			String sql = "select siteid,description from pi_site CONNECT BY PRIOR pi_siteid = parent  START WITH "+(siteid!=null&&!"".equals(siteid)?("siteid = '"+siteid+"'"):(" parent is null "))+" order by   pi_siteid";
			String data = "{'curSiteid':'"+siteid+"','data':[" ;
			try {
				Statement statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()){
					data = data+"{'siteid':'"+rs.getString("siteid")+"','name':'"+rs.getString("description")+"','cursiteid':'"+(siteid!=null&&!"".equals(siteid)?siteid:"")+"'},";
				}
				rs.close();
				statement.close();
				if (data!=null&&!"".equals(data)) {
					data = data.substring(0, data.length() - 1);
				}
				data = data+"]}";
				System.out.println(data);
				pw.print(data);
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
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
