package com.shuto.mam.portal.action;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import psdi.server.MXServer;

import com.alibaba.fastjson.JSON;
import com.shuto.mam.portal.model.Option;
import com.shuto.mam.portal.model.Wotrack;

/**
 * 工作票具体实现类
 * 
 * @author Liuyc
 *
 */
public class WotrackAction {
	private Connection con;
	private Statement st;
	private String siteid;

	public WotrackAction(String siteid) throws RemoteException, SQLException {
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
				+ " value in (select  STATUS  from workorder  where  worktype='工作票'  and siteid ='XZHR200' )";
		ResultSet tkStatus = st.executeQuery(sqlStatus);
		List<Option> options = new ArrayList<Option>();
		int id = 0;
		Option opall = new Option();
		opall.setId("tk_0");
		opall.setSelected(true);
		opall.setText("全部");
		options.add(opall);
		while (tkStatus.next()) {
			String value = tkStatus.getString("value");
			Option op = new Option();
			id = id + 1;
			op.setId("tk_" + id);
			op.setText(value);
			options.add(op);
		}
		tkStatus.close();
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
		 * 工作票类型
		 */
		List<Option> optionsLX = new ArrayList<Option>();
		Option opAllLX = new Option();
		opAllLX.setId("lx_0");
		opAllLX.setSelected(true);
		opAllLX.setText("全部");
		optionsLX.add(opAllLX);
		String sqlLX = "select value from ALNDOMAIN where domainid ='S_ORDERTYPE' ";
		ResultSet rsLX = st.executeQuery(sqlLX);
		id = 0;
		while (rsLX.next()) {
			id = id + 1;
			String value = rsLX.getString("value");
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
	 * @param tk_status
	 *            状态
	 * @param tk_zydw
	 *            检修单位
	 * @param tk_zy
	 *            专业
	 * @param tk_lx
	 *            类型
	 * @param tk_jz
	 *            机组
	 * @param tk_description
	 *            描述
	 * @param pageSize
	 *            翻页 每页条数
	 * @param pageNum
	 *            翻页 页码
	 * @return 要加载的数据
	 * @throws SQLException
	 */
	public String loadData(String startdate, String enddate, String tk_status,
			String tk_zydw, String tk_zy, String tk_lx, String tk_description,
			int pageSize, int pageNum) throws SQLException {
		String sqlDate = " select tk.wonum,tk.status, tk.phone,  TO_CHAR(tk.createdate, 'yyyy-mm-dd hh24:mi') createdate ,tk.worktype,( select  DESCRIPTION from  PROFESSION where  "
				+ "tk.PROFESSION =PROFESSION.PROFESSIONNUM  and  tk.siteid=PROFESSION.Siteid) as  S_PROFESSION, TO_CHAR(tk.schedstart, 'yyyy-mm-dd hh24:mi') schedstart,"
				+ " TO_CHAR(tk.schedfinish, 'yyyy-mm-dd hh24:mi') schedfinish,( select  DESCRIPTION from  TEAM where  tk.TEAMNAME =TEAM.TEAMNUM  and  tk.siteid=TEAM.Siteid) "
				+ "as  TEAMNAME,tk.description, (select displayname from person where person.personid=tk.lead) as lead  from workorder tk  ";// String
																																				// tk_jz,
		String sqlCount = "select count(wonum) as count from workorder tk ";
		String sqlWhere = " where worktype in ('工作票') and  tk.siteid='"
				+ siteid + "'  and";
		// 处理参数拼接Sql
		if (startdate != null && !"".equals(startdate)) {
			sqlWhere = sqlWhere + " tk.createdate >=to_date('" + startdate
					+ "','YYYY-MM-DD') and";
		}
		if (enddate != null && !"".equals(enddate)) {
			sqlWhere = sqlWhere + " tk.createdate <=to_date('" + enddate
					+ "','YYYY-MM-DD') and";
		}
		if (!"全部".equals(tk_status) && !"".equals(tk_status)) {
			sqlWhere = sqlWhere + " tk.status='" + tk_status + "' and";
		}
		if (!"全部".equals(tk_zydw) && !"".equals(tk_zydw)) {
			sqlWhere = sqlWhere + " tk.teamname='" + tk_zydw + "' and";
		}
		if (!"全部".equals(tk_zy) && !"".equals(tk_zy)) {
			sqlWhere = sqlWhere + " tk.s_profession='" + tk_zy + "' and";
		}
		if (!"全部".equals(tk_lx) && !"".equals(tk_lx)) {
			sqlWhere = sqlWhere + " tk.WORKTYPE='" + tk_lx + "' and";
		}
		if (tk_description != null && !"".equals(tk_description)) {
			sqlWhere = sqlWhere + " tk.description like '%" + tk_description
					+ "%' and";
		}
		// if (!"全部".equals(tk_jz) && !"".equals(tk_jz)) {
		// sqlWhere = sqlWhere + " tk.S_JIZU='" + tk_jz + "' and";
		// }

		sqlWhere = sqlWhere.substring(0, sqlWhere.length() - 3);
		List<Wotrack> srs = new ArrayList<Wotrack>();

		String sqlFy = "select * from (" + sqlDate + sqlWhere
				+ "  order by createdate desc ) where rownum>" + (pageNum - 1)
				* pageSize + " and rownum<=" + pageSize * pageNum + " ";//
		ResultSet rs = st.executeQuery(sqlFy);
		while (rs.next()) {
			Wotrack tk = new Wotrack();
			tk.setWonum(rs.getString("wonum"));
			tk.setStatus(rs.getString("status"));
			tk.setWorktype(rs.getString("worktype"));
			tk.setS_profession(rs.getString("s_profession"));
			tk.setSchedstart(rs.getString("schedstart"));
			tk.setSchedfinish(rs.getString("schedfinish"));
			tk.setTeamname(rs.getString("teamname"));
			tk.setDescription(rs.getString("description"));
			tk.setLead(rs.getString("lead"));
			tk.setPhone(rs.getString("phone"));
			tk.setCreatedate(rs.getString("createdate"));
			srs.add(tk);
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
