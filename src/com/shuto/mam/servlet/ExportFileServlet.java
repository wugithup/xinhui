package com.shuto.mam.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import psdi.server.MXServer;
import psdi.util.MXSession;

public class ExportFileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/x-msdownload");
		resp.setHeader("Content-Disposition", "attachment; filename=ExportFileExcel.xls");
		System.out.println("ExportFile Start");
		String type = getParam(req, "xdjtype");
		String professionid = getParam(req, "professionid");
		String begin = getParam(req, "startdate");
		String end = getParam(req, "enddate");
		String dbz = getParam(req, "dbz");
		String siteid = getParam(req, "siteid");
		if(siteid==null||"".equals(siteid)||"null".equals(siteid)){
			MXSession session = (MXSession)req.getSession().getAttribute("MXSession");
			siteid = session.getUserInfo().getInsertSite();
		}
		System.out.println(type+"--"+professionid+"--"+begin+"--"+end+"--"+dbz);
		if (type != null && professionid != null && begin != null && end != null) {
			String pro = "";
			if (!"全部".equals(professionid)) {
				pro = " PROFESSIONAL='" + professionid + "' and ";
			}
			String shift = "";
			if("点检".equals(type)){
				shift = " ";
			}else if (!"全部".equals(dbz)) {
				shift = " SHIFT='" + dbz + "' and ";
			}
			String sql = "select   num as 任务编号,  device_part_name as 部位部件名称,  point_type as 测点类型,  check_method as 检测方法,  higher_limit as 上限值,  lower_limit as 下限值,  pi_task_item_result as 检测结果  "+
					" from VIEW_PI_TASK_ITEM   where  "+
					" task_up_time between to_date('" + begin
							+ "','yyyy-MM-dd') and to_date('" + end+" 23:59:59"
							+ "','yyyy-MM-dd HH24:mi:ss') and " 
							+((siteid!=null&&!"".equals(siteid)&&!"null".equals(siteid))?("siteid = '"+siteid+"' and orgid = (select orgid from site where siteid = '"+siteid+"') and "):"")
							+ pro + shift
							+ " task_type='" + type + "' ";
			System.out.println(sql);	
			Connection conn  = getOraclConn();
			try {
				ServletOutputStream localServletOutputStream = resp.getOutputStream();
				WritableWorkbook wwb = Workbook.createWorkbook(localServletOutputStream);
				WritableSheet ws = wwb.createSheet("ExportFile Sheet 1", 0);
				Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stat.executeQuery(sql);
				int row ;
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					String name = rs.getMetaData().getColumnName(i);
					System.out.println(name);
					ws.addCell(new Label(i-1,0 ,name));
					row = 1;
					while(rs.next()){
						System.out.println(rs.getString(name));
						ws.addCell(new Label(i-1,row ,rs.getString(name)));
						row ++;
					}
					rs.beforeFirst();
				}
				rs.close();
				stat.close();
				conn.close();
				wwb.write();
				wwb.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		System.out.println("ExportFileEnd");
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

}
