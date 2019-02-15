package com.shuto.mam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import psdi.server.MXServer;

public class PiLineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Connection conn = getOraclConn();
		Statement statement = null;
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter pw = resp.getWriter();
		String sql = "";
		ResultSet rs;
		int len = Integer.parseInt(req.getParameter("pointLen"));
		String pointids = req.getParameter("pointids");
		if (pointids.length() > 0) {
			pointids = pointids.substring(0, pointids.lastIndexOf(','));
		}
		String begin_date = req.getParameter("begin_date");
		String end_date = req.getParameter("end_date");
		System.out.println(pointids + "|" + begin_date + "|" + end_date);
		String siteid = req.getParameter("siteid").trim();

		System.out.println(siteid);
		sql = "select num,device_part_name,PI_ITEM_DESCRIPTION,point_norm,pi_task_item_result,DOUBLERET ,pi_task_time ,higher_limit,lower_limit  from  pi_task_item"
				+ " where  "
				+ ((siteid != null && !"".equals(siteid) && !"null"
						.equals(siteid)) ? ("siteid = '"
						+ siteid
						+ "' and orgid = (select orgid from site where siteid = '"
						+ siteid + "') and ")
						: "")
				+ ""
				+ " pi_task_item.num in ("
				+ pointids
				+ ")"
				+ " and pi_task_item.pi_task_time >= to_date('"
				+ begin_date
				+ "','yyyy-MM-dd') and pi_task_item.pi_task_time <= to_date('"
				+ end_date
				+ " 23:59:59','yyyy-MM-dd HH24:MI:SS')"
				+ " order by pi_task_time";
		System.out.println(sql);
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			int i = 0;
			String str1 = "['name'," + pointids + "]", str2 = str1;
			Date time = null;
			Vector<String> data = new Vector<String>();
			boolean ok = true, f_ok = false;
			String msg = "", fields = str1, other = "";
			while (rs.next()) {

				if (rs.getDate("pi_task_time").equals(time) || time == null) {
					time = rs.getDate("pi_task_time");
					if (!f_ok) {
						fields = fields.replace(
								rs.getString("num"),
								"'" + rs.getString("num") + ":"
										+ rs.getString("device_part_name")
										+ "'");
						String pn = "";
						if (rs.getString("point_norm") != null
								&& !"null".equals(rs.getString("point_norm")))
							pn = rs.getString("point_norm");
						other += "[{itemname:'" + rs.getString("num") + ":"
								+ rs.getString("PI_ITEM_DESCRIPTION")
								+ "',higher_limit:'"
								+ rs.getString("higher_limit")
								+ "',lower_limit:'"
								+ rs.getString("lower_limit")
								+ "',point_norm:'" + pn + "'}],";
					}
					SimpleDateFormat d = new SimpleDateFormat("MM-dd");
					if (i == 0) {
						str2 = str2.replace("name",
								d.format(rs.getDate("pi_task_time")));
					}
					// if(rs.getString("DOUBLERET")!=null)
					str2 = str2.replace(rs.getString("num"),
							rs.getString("DOUBLERET"));
					i++;

					System.out.println("str2:" + str2 + "-------i" + i);
				} else {
					System.out.println("i: " + i + " len: " + len + " time: "
							+ time + " rstime: " + rs.getDate("pi_task_time"));
					msg = "Ext.MessageBox.alert('错误','在加载时间点:" + time
							+ " 数据时发生错误,请与管理员联系!')";
					ok = false;
					break;
				}
				if (i == len) {
					f_ok = true;
					data.add(str2);
					time = null;
					str2 = str1;
					i = 0;
				}
				System.out.println("rstime:" + rs.getString("pi_task_time")
						+ "time:" + time);
			}
			statement.close();
			rs.close();
			conn.close();
			if (ok) {
				if (!"".equals(other)) {
					other = other.substring(0, other.length() - 1);
				}

				msg = "[" + fields + ",[";
				for (int j = 0; j < data.size(); j++) {
					msg += data.get(j) + ",";
				}
				msg = msg.substring(0, msg.lastIndexOf(','));
				msg += "],[" + other + "]]";
			}
			System.out.println(msg);
			pw.print(msg);
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
		return conn;
	}

}
