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
public class PiSeachTreeServlet  extends HttpServlet{
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException { 
		Connection conn1 = getOraclConn();
		Statement statement1 = null;
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter pw   = resp.getWriter() ;
		ResultSet rs1 ; 
		String searchName  = req.getParameter("searchName");
		String siteid = req.getParameter("siteid").trim();
		System.out.println(siteid);
		
		String sql = "select PI_AREA.Professional,Pi_Area_Device.Device_Name,Pi_Area_Device.Num from PI_AREA,Pi_Area_Device where  "+((siteid!=null&&!"".equals(siteid)&&!"null".equals(siteid))?("PI_AREA.siteid = '"+siteid+"' and PI_AREA.orgid = (select orgid from site where siteid = '"+siteid+"') and "):"")+"  PI_AREA.type = '点检' and  PI_AREA.Num = Pi_Area_Device.Pi_Area_No and Pi_Area_Device.Device_Name like '%"+searchName+"%'  and (select count(pi_area_itemid) from pi_area_item where pi_area_item.pi_area_device_no = PI_AREA_DEVICE.Num and pi_area_item.pi_area_no = PI_AREA_DEVICE.pi_area_no and pi_area_item.point_type <> 'GC' )>0";
		String resText = "";
		String parents = "";
		try {
			statement1 = conn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE) ;
			rs1  = statement1.executeQuery(sql);
			while(rs1.next()){
				if(rs1.isLast()){
					parents += "{id:'_"+rs1.getString("Professional")+"',child:'"+rs1.getString("Professional")+"_"+rs1.getString("NUM")+"'}";
				}else{
					parents += "{id:'_"+rs1.getString("Professional")+"',child:'"+rs1.getString("Professional")+"_"+rs1.getString("NUM")+"'},";
				}
			}	
			resText += "{parents:["+parents+"]}";
			rs1.close() ;
			statement1.close() ;
			conn1.close() ;
			System.out.println(resText);
			pw.print(resText) ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getOraclConn(){
		Connection conn = null;
			try {
				conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		return conn ;
	}
	
	
	
	
}
 