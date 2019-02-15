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
import com.shuto.mam.portal.model.Workorder;

/**
 * 工单具体实现类
 * 
 * @author liyc
 *
 */
public class WorkorderAction {
	private Connection con;
	private Statement st;
	private String siteid;

	public WorkorderAction(String siteid) throws RemoteException, SQLException {
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
		 * 查询状态
		 */

		String sqlStatus = "select value from SYNONYMDOMAIN where domainid='WOSTATUS' and "
				+ " value in (select  STATUS  from workorder  where   worktype in ('检修工单','缺陷工单','预维护工单') and istask = 0   and siteid ='XZHR200' )";
		ResultSet rsStatus = st.executeQuery(sqlStatus);
		List<Option> options = new ArrayList<Option>();
		int id = 0;
		Option opall = new Option();
		opall.setId("wr_0");
		opall.setSelected(true);
		opall.setText("全部");
		options.add(opall);
		while (rsStatus.next()) {
			String value = rsStatus.getString("value");
			Option op = new Option();
			id = id + 1;
			op.setId("wr_" + id);
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
				+ siteid + "'    and  status='活动'  order by PROFESSIONNUM";
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
		 * 工单类型
		 */
		List<Option> optionsLX = new ArrayList<Option>();
		Option opAllJB = new Option();
		opAllJB.setId("lx_0");
		opAllJB.setSelected(true);
		opAllJB.setText("全部");
		optionsLX.add(opAllJB);
		String sqlLX = "select worktype from worktype where   worktype in ('检修工单','缺陷工单','预维护工单') and  orgid='CRPH500JS'";
		ResultSet rsLX = st.executeQuery(sqlLX);
		id = 0;
		while (rsLX.next()) {
			id = id + 1;
			String value = rsLX.getString("worktype");
			Option op = new Option();
			op.setId("LX_" + id);
			op.setText(value);
			optionsLX.add(op);
		}
		rsLX.close();
		String selectLX = JSON.toJSONString(optionsLX);
		resultStr = resultStr + ",\"lx\":" + selectLX + "}";

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
	 * @param wr_status
	 *            状态
	 * @param wr_zydw
	 *            检修单位
	 * @param wr_zy
	 *            专业
	 * @param wr_lx
	 *            类型
	 * @param wr_jz
	 *            机组
	 * @param wr_description
	 *            描述
	 * @param pageSize
	 *            翻页 每页条数
	 * @param pageNum
	 *            翻页 页码
	 * @return 要加载的数据
	 * @throws SQLException
	 */
	public String loadData(String startdate, String enddate, String wr_status,
			String wr_zydw, String wr_zy, String wr_lx, String wr_description,
			int pageSize, int pageNum) throws SQLException {
		String sqlDate = "  select wr.wonum,wr.status, wr.createdate,wr.worktype,( select  DESCRIPTION from  PROFESSION "
				+ "where wr.PROFESSION =PROFESSION.PROFESSIONNUM  and  wr.siteid=PROFESSION.Siteid) as  S_PROFESSION,wr.schedstart,"
				+ "wr.schedfinish,( select  DESCRIPTION from  TEAM where  wr.TEAMNAME =TEAM.TEAMNUM  and  wr.siteid=TEAM.Siteid) as teamname ,"
				+ "wr.description,(select displayname from person where person.personid=wr.lead) as lead from workorder wr  ";

		String sqlCount = "select count(wonum) as count from workorder wr ";
		String sqlWhere = "    where worktype in ('检修工单','缺陷工单','预维护工单') and istask = 0 and wr.siteid='"
				+ siteid + "'  and";
		// 处理参数拼接Sql
		if (startdate != null && !"".equals(startdate)) {
			sqlWhere = sqlWhere + " wr.REPORTDATE >=to_date('" + startdate
					+ "','YYYY-MM-DD') and";
		}
		if (enddate != null && !"".equals(enddate)) {
			sqlWhere = sqlWhere + " wr.REPORTDATE <=to_date('" + enddate
					+ "','YYYY-MM-DD') and";
		}
		if (!"全部".equals(wr_status) && !"".equals(wr_status)) {
			sqlWhere = sqlWhere + " wr.status='" + wr_status + "' and";
		}
		if (!"全部".equals(wr_zydw) && !"".equals(wr_zydw)) {
			sqlWhere = sqlWhere + " wr.teamname='" + wr_zydw + "' and";
		}
		if (!"全部".equals(wr_zy) && !"".equals(wr_zy)) {
			sqlWhere = sqlWhere + " wr.s_profession='" + wr_zy + "' and";
		}
		if (!"全部".equals(wr_lx) && !"".equals(wr_lx)) {
			sqlWhere = sqlWhere + " wr.WORKTYPE='" + wr_lx + "' and";
		}
		if (wr_description != null && !"".equals(wr_description)) {
			sqlWhere = sqlWhere + " wr.description like '%" + wr_description
					+ "%' and";
		}
		// if (!"全部".equals(wr_jz) && !"".equals(wr_jz)) {
		// sqlWhere = sqlWhere + " wr.S_JIZU='" + wr_jz + "' and";
		// }

		sqlWhere = sqlWhere.substring(0, sqlWhere.length() - 3);
		List<Workorder> srs = new ArrayList<Workorder>();

		String sqlFy = "select * from (" + sqlDate + sqlWhere + "   order by createdate desc ) where rownum>"
				+ (pageNum - 1) * pageSize + " and rownum<=" + pageSize * pageNum
				+ "  ";
		ResultSet rs = st.executeQuery(sqlFy);

		while (rs.next()) {
			Workorder wr = new Workorder();
			wr.setWonum(rs.getString("wonum"));
			wr.setStatus(rs.getString("status"));
			wr.setWorktype(rs.getString("worktype"));
			wr.setS_profession(rs.getString("s_profession"));
			wr.setSchedstart(rs.getString("schedstart"));
			wr.setSchedfinish(rs.getString("schedfinish"));
			wr.setTeamname(rs.getString("teamname"));
			wr.setDescription(rs.getString("description"));
			wr.setLead(rs.getString("lead"));
			srs.add(wr);
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
