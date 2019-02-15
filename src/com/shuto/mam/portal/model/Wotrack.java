package com.shuto.mam.portal.model;

import java.text.SimpleDateFormat;

public class Wotrack {

	private String wonum;// 编号
	private String status;// 状态
	private String worktype;// 类型
	private String s_profession;// 专业
	private String schedstart;// 计划开工时间
	private String schedfinish;// 计划完成时间
	private String teamname;// 作业单位
	private String description;// 描述
	private String lead;// 工作负责人
	private String phone;// 工作负责人

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	private String createdate;// 工作负责人

	public String getWonum() {
		return wonum;
	}

	public void setWonum(String wonum) {
		this.wonum = wonum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWorktype() {
		return worktype;
	}

	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}

	public String getS_profession() {
		return s_profession;
	}

	public void setS_profession(String s_profession) {
		this.s_profession = s_profession;
	}

	public String getSchedstart() {
		return schedstart;
	}

	public void setSchedstart(String schedstart) {
		this.schedstart = schedstart;
	}

	public String getSchedfinish() {
		return schedfinish;
	}

	public void setSchedfinish(String schedfinish) {
		this.schedfinish = schedfinish;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

}
