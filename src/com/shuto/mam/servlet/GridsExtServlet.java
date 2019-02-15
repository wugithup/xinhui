package com.shuto.mam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.util.MXSession;

public class GridsExtServlet extends HttpServlet {
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		////////
		Connection conn = getOraclConn();
		Statement statement = null;
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter pw = resp.getWriter();
		String areaname = "";
		String sql = "";
		ResultSet rs;
		String id = req.getParameter("sid");
		String parentid = req.getParameter("parentid").replaceAll("_", "") ;
		id = id.substring(id.indexOf("_")+1);
		String siteid = req.getParameter("siteid").trim();
		sql = "select num,device_part_name , point_type , higher_limit, lower_limit ,check_method from pi_area_item  where "+((siteid!=null&&!"".equals(siteid)&&!"null".equals(siteid))?("siteid = '"+siteid+"' and orgid = (select orgid from site where siteid = '"+siteid+"') and "):"")+"  pi_area_device_no ='"
				+ id + "'  and point_type <> 'GC' and pi_area_no in (select num from pi_area where PROFESSIONAL = '"+parentid+"')";
		System.out.println(sql);
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				areaname += "[{itemid:'" + rs.getString("num") + "',itemname:'"
				+rs.getString("num") +":"+ rs.getString("device_part_name") + "',pointtype:'"
						+ rs.getString("point_type") + "'}],";
			}
			statement.close();
			rs.close();
			conn.close();
			if (!"".equals(areaname)) {
				areaname = areaname.substring(0, areaname.length() - 1);
			}

			String treeStr = "[" + areaname + "]";
			System.out.println(treeStr);
			pw.print(treeStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getOraclConn() {
		Connection conn = null;
		
		try {
			conn = MXServer.getMXServer().getDBManager()
					.getSequenceConnection();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
/*		
		try {
			System.out.println("asdas");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.254:1521:orcl","pi","pi");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}*/
		return conn;
	}

}
