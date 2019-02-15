package com.shuto.mam.portal.action;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import psdi.server.MXServer;

import com.alibaba.fastjson.JSON;
import com.shuto.mam.portal.model.Option;
import com.shuto.mam.portal.model.Sr;

/**
 * 缺陷具体实现类
 * 
 * @author Liuyc
 *
 */
public class SrAction {
	private Connection con;
	private Statement st;
	private String siteid;

	public SrAction(String siteid) throws RemoteException, SQLException {
		con = MXServer.getMXServer().getDBManager().getSequenceConnection();
		st = con.createStatement();
		this.siteid = siteid;
	}

	/**
	 * 初始化页面控件
	 * 
	 * @throws SQLException
	 */
	public String getInitControl() throws SQLException {
		String resultStr = "{";

		/*
		 * 查询缺陷状态
		 */
		String sqlStatus = "select value from SYNONYMDOMAIN where domainid='SRSTATUS' ";// and
																						// siteid='"+siteid+"'
		ResultSet rsStatus = st.executeQuery(sqlStatus);
		List<Option> options = new ArrayList<Option>();
		int id = 0;
		Option opall = new Option();
		opall.setId("sr_0");
		opall.setSelected(true);
		opall.setText("全部");
		options.add(opall);
		while (rsStatus.next()) {
			String value = rsStatus.getString("value");
			Option op = new Option();
			id = id + 1;
			op.setId("sr_" + id);
			op.setText(value);
			options.add(op);
		}
		rsStatus.close();
		String selectStart = JSON.toJSONString(options);
		resultStr = resultStr + "\"status\":" + selectStart;

		/*
		 * 查询作业单位
		 */
		List<Option> optionsZydw = new ArrayList<Option>();
		Option opZyAll = new Option();
		opZyAll.setId("zydw_0");
		opZyAll.setSelected(true);
		opZyAll.setText("全部");
		optionsZydw.add(opZyAll);
		String sqlzydw = "select DESCRIPTION from  TEAM  where siteid='"
				+ siteid + "' order by TEAMNUM";
		ResultSet rszydw = st.executeQuery(sqlzydw);
		id = 0;
		while (rszydw.next()) {
			id = id + 1;
			String value = rszydw.getString("DESCRIPTION");
			Option op = new Option();
			op.setId("zydw+" + id);
			op.setText(value);
			optionsZydw.add(op);
		}
		rszydw.close();
		String selectZydw = JSON.toJSONString(optionsZydw);
		resultStr = resultStr + ",\"zydw\":" + selectZydw;

		/*
		 * 查询专业类别
		 */
		List<Option> optionsZy = new ArrayList<Option>();
		Option opAllZy = new Option();
		opAllZy.setId("zy_0");
		opAllZy.setSelected(true);
		opAllZy.setText("全部");
		optionsZy.add(opZyAll);
		String sqlZy = "select DESCRIPTION from PROFESSION where siteid='"
				+ siteid + "'  and  status='活动' order by PROFESSIONNUM";
		ResultSet rsZy = st.executeQuery(sqlZy);
		id = 0;
		while (rsZy.next()) {
			String value = rsZy.getString("DESCRIPTION");
			id = id + 1;
			Option op = new Option();
			op.setId("zy_" + id);
			op.setText(value);
			optionsZy.add(op);
		}
		rsZy.close();
		String selectZy = JSON.toJSONString(optionsZy);
		resultStr = resultStr + ",\"zy\":" + selectZy;

		/*
		 * 缺陷级别
		 */
		List<Option> optionsJB = new ArrayList<Option>();
		Option opAllJB = new Option();
		opAllJB.setId("jb_0");
		opAllJB.setSelected(true);
		opAllJB.setText("全部");
		optionsJB.add(opAllJB);
		String sqlJB = "select type from srtype ";
		ResultSet rsJB = st.executeQuery(sqlJB);
		id = 0;
		while (rsJB.next()) {
			id = id + 1;
			String value = rsJB.getString("type");
			Option op = new Option();
			op.setId("JB_" + id);
			op.setText(value);
			optionsJB.add(op);
		}
		rsJB.close();
		String selectJB = JSON.toJSONString(optionsJB);
		resultStr = resultStr + ",\"jb\":" + selectJB + "}";
		/*
		 * 查询机组
		 */
		// List<Option> optionsJZ = new ArrayList<Option>();
		// Option opAllJZ = new Option();
		// opAllJZ.setId("jz_0");
		// opAllJZ.setSelected(true);
		// opAllJZ.setText("全部");
		// optionsJZ.add(opAllJZ);
		// String sqlJZ =
		// "select value from alndomain where domainid='JZ' and siteid='"
		// + siteid + "'";
		// ResultSet rsJZ = st.executeQuery(sqlJZ);
		// id = 0;
		// while (rsJZ.next()) {
		// id = id + 1;
		// String value = rsJZ.getString("value");
		// Option op = new Option();
		// op.setId("jz_" + id);
		// op.setText(value);
		// optionsJZ.add(op);
		// }
		// rsJZ.close();
		// String selectJZ = JSON.toJSONString(optionsJZ);
		// resultStr = resultStr + ",\"jz\":" + selectJZ + "}";

		return resultStr;
	}

	/**
	 * 加载数据
	 * 
	 * @param startdate
	 *            开始时间
	 * @param enddate
	 *            结束时间
	 * @param sr_status
	 *            状态
	 * @param sr_zydw
	 *            检修单位
	 * @param sr_zy
	 *            专业
	 * @param sr_jb
	 *            级别
	 * @param sr_jz
	 *            机组
	 * @param sr_description
	 *            描述
	 * @param pageSize
	 *            翻页 每页条数
	 * @param pageNum
	 *            翻页 页码
	 * @return 要加载的数据
	 * @throws SQLException
	 */
	public String loadData(String startdate, String enddate, String sr_status,
			String sr_zydw, String sr_zy, String sr_jb, String sr_description,
			int pageSize, int pageNum)// String sr_jz,
			throws SQLException {
		String sqlDate = "   select   s.TICKETID, ( select  DESCRIPTION from  PROFESSION where "
				+ " s.PROFESSION =PROFESSION.PROFESSIONNUM  and  s.siteid=PROFESSION.Siteid) as  S_PROFESSION,"
				+ "( select  DESCRIPTION from  TEAM where  s.TEAMNAME =TEAM.TEAMNUM  and  s.siteid=TEAM.Siteid) as  TEAMNAME,"
				+ "s.DESCRIPTION,  TO_CHAR(s.REPORTDATE, 'yyyy-mm-dd hh24:mi')  REPORTDATE,s.S_QXLB REPORTEDPRIORITY, s.STATUS,"
				+ "(select displayname from person where person.personid=s.REPORTEDBY ) as REPORTEDBY,  "
				+ "(select displayname from person where person.personid = s.S_XQPERSON) as S_XQPERSON from "
				+ "sr s left join locations l on s.location=l.location    ";

		String sqlCount = "select count(s.TICKETID) as count from sr  s left join locations l on s.location=l.location ";
		String sqlWhere = " where s.siteid=l.siteid and s.siteid='" + siteid
				+ "'  and";
		// 处理参数拼接Sql
		if (startdate != null && !"".equals(startdate)) {
			sqlWhere = sqlWhere + " s.REPORTDATE>=to_date('" + startdate
					+ "','YYYY-MM-DD') and";
		}
		if (enddate != null && !"".equals(enddate)) {
			sqlWhere = sqlWhere + " s.REPORTDATE<=to_date('" + enddate
					+ "','YYYY-MM-DD') and";
		}
		if (!"全部".equals(sr_status) && !"".equals(sr_status)) {
			sqlWhere = sqlWhere + " s.status='" + sr_status + "' and";
		}
		if (!"全部".equals(sr_zydw) && !"".equals(sr_zydw)) {
			sqlWhere = sqlWhere + " s.teamname='" + sr_zydw + "' and";
		}
		if (!"全部".equals(sr_zy) && !"".equals(sr_zy)) {
			sqlWhere = sqlWhere + " s.s_profession='" + sr_zy + "' and";
		}
		if (!"全部".equals(sr_jb) && !"".equals(sr_jb)) {
			sqlWhere = sqlWhere + " s.reportedpriority='" + sr_jb + "' and";
		}
		if (sr_description != null && !"".equals(sr_description)) {
			sqlWhere = sqlWhere + " s.description like '%" + sr_description
					+ "%' and";
		}
		// if (!"全部".equals(sr_jz) && !"".equals(sr_jz)) {
		// sqlWhere = sqlWhere + " l.jizu='" + sr_jz + "' and";
		// }

		sqlWhere = sqlWhere.substring(0, sqlWhere.length() - 3);
		List<Sr> srs = new ArrayList<Sr>();

		String sqlFy = "select * from (" + sqlDate + sqlWhere
				+ "  order by  REPORTDATE desc ) where rownum>" + (pageNum - 1)
				* pageSize + " and rownum<=" + pageSize * pageNum + " ";
		ResultSet rs = st.executeQuery(sqlFy);

		while (rs.next()) {
			Sr sr = new Sr();
			sr.setTicketid(rs.getString("ticketid"));
			sr.setTeamname(rs.getString("teamname"));
			sr.setDescription(rs.getString("description"));
			sr.setReportdate(rs.getString("reportdate"));
			sr.setReportedby(rs.getString("reportedby"));
			sr.setReportedpriority(rs.getString("reportedpriority"));
			sr.setS_profession(rs.getString("s_profession"));
			sr.setStatus(rs.getString("status"));
			sr.setS_xqperson(rs.getString("S_XQPERSON"));
			srs.add(sr);
		}
		rs.close();

		ResultSet rsCount = st.executeQuery(sqlCount + sqlWhere);
		String count = "";
		while (rsCount.next()) {
			count = rsCount.getString("count");
		}
		rsCount.close();

		Map dataGrid = new HashMap();
		dataGrid.put("total", count);
		dataGrid.put("rows", srs);
		String datas = JSON.toJSON(dataGrid).toString();
		return datas;
	}

}
