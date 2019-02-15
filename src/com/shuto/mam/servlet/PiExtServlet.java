package com.shuto.mam.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.util.MXSession;
/*import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboRemote ;
import psdi.server.MXServer;*/
public class PiExtServlet  extends HttpServlet{
	private static final long serialVersionUID = -2894391397271192539L;
	private Map<String, String> treeJson =  new HashMap<String, String>();
	private Date updateDate = new Date();
	private int updateDay = 2;
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException { 
		////////
		Connection conn = getOraclConn();
		Statement statement = null;
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter pw   = resp.getWriter() ;
		String areaname = "" ;
		String sql  = "" ;
		Date curdate = new Date();
		String siteid = req.getParameter("siteid").trim();
		if(siteid==null||"".equals(siteid)||"null".equals(siteid)){
			MXSession session = (MXSession)req.getSession().getAttribute("MXSession");
			siteid = session.getUserInfo().getInsertSite();
		}
		System.out.println("siteid--"+siteid);
		try {
			if("root".equals(req.getParameter("node"))){
				if(treeJson.get("root"+siteid)==null||"".equals(treeJson.get("root"+siteid))||"[]".equals(treeJson.get("root"+siteid))||((curdate.getTime()-updateDate.getTime())/ (24 * 60 * 60 * 1000))>updateDay){
					statement = conn.createStatement() ;
					sql = "select pi_a.PROFESSIONAL from PI_AREA pi_a where "+((siteid!=null&&!"".equals(siteid)&&!"null".equals(siteid))?("siteid = '"+siteid+"' and orgid = (select orgid from site where siteid = '"+siteid+"') and "):"")+"  type = '点检' and  (select count(PI_AREA_DEVICE.NUM) from PI_AREA_DEVICE where PI_AREA_DEVICE.Pi_Area_No in (select PI_AREA.NUM from PI_AREA where PI_AREA.PROFESSIONAL = pi_a.PROFESSIONAL) and (select count(pi_area_itemid) from pi_area_item where pi_area_item.pi_area_device_no = PI_AREA_DEVICE.Num and pi_area_item.point_type <> 'GC' )>0 )>0 group by pi_a.PROFESSIONAL";
					ResultSet  rs  = statement.executeQuery(sql);
					System.out.println(sql);
					while (rs.next()){
						areaname  +="{text:'"+ rs.getString("PROFESSIONAL")+"',id:'_"+rs.getString("PROFESSIONAL")+"'}," ;
					}  
					rs.close() ;
					if(areaname!=null&&!"".equals(areaname)){
						areaname = areaname.substring(0,areaname.length()-1) ;
						String treeStr  = "["+  areaname  +"]" ;
						System.out.println(treeStr);
						treeJson.remove("root"+siteid);
						treeJson.put("root"+siteid, treeStr);
					}
					statement.close() ;
				}
				pw.print(treeJson.get("root"+siteid)) ;
			}else{
				 String text = req.getParameter("id") ;  
				 text = text.replaceAll("_", "") ;
				 if(treeJson.get(text+siteid)==null||"".equals(treeJson.get(text+siteid))||"[]".equals(treeJson.get(text+siteid))||((curdate.getTime()-updateDate.getTime())/ (24 * 60 * 60 * 1000)+1)>updateDay){
					 statement = conn.createStatement() ;
					 sql = "select PI_AREA_DEVICE.NUM,PI_AREA_DEVICE.DEVICE_NAME from  PI_AREA_DEVICE where "+((siteid!=null&&!"".equals(siteid))?("siteid = '"+siteid+"' and orgid = (select orgid from site where siteid = '"+siteid+"') and "):"")+" NUM IN (select num from pi_task_device ) and  type = '点检' and  PI_AREA_DEVICE.Pi_Area_No in (select PI_AREA.NUM from PI_AREA where PI_AREA.PROFESSIONAL = '"+text+"') and (select count(pi_area_itemid) from pi_area_item where pi_area_item.pi_area_device_no = PI_AREA_DEVICE.Num and pi_area_item.pi_area_no = PI_AREA_DEVICE.pi_area_no and pi_area_item.point_type <> 'GC' )>0 group by PI_AREA_DEVICE.NUM,PI_AREA_DEVICE.DEVICE_NAME";
					 System.out.println(sql);
					 ResultSet rs  = statement.executeQuery(sql);
						while (rs.next()){
						 areaname  +="{text:'"+ rs.getString("device_name")+"',leaf:true,id:'"+text+"_"+rs.getString("NUM")+"',idd:'"+rs.getString("NUM")+"'}," ;
						}
						rs.close() ;
						if(!"".equals(areaname)){
							areaname = areaname.substring(0,areaname.length()-1) ;
						}
						String treeStr  = "["+  areaname  +"]" ;
						System.out.println(treeStr);
						treeJson.remove(text+siteid);
						treeJson.put(text+siteid, treeStr);
					 statement.close() ;
				 }
				pw.print(treeJson.get(text+siteid)) ;
			}
			conn.close() ; 
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public static Connection getOraclConn(){
		Connection conn = null;
/*		try {
			System.out.println("asdas");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.254:1521:orcl","pi","pi");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}*/
			try {
				conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		return conn ;
	}
	
	
	
	
}
 