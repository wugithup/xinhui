package com.shuto.mam.portal.model;

public class Sr {
	private String ticketid;//缺陷编号
	private String s_profession;//专业
	private String teamname;//检修班组
	private String description;//描述
	private String reportdate;//填报时间
	private String reportedpriority;//缺陷级别
	private String status;//状态
	private String reportedby;//填报人
	private String s_xqperson;//消缺人

	
	public String getS_xqperson() {
		return s_xqperson;
	}
	public void setS_xqperson(String s_xqperson) {
		this.s_xqperson = s_xqperson;
	}
	public String getTicketid() {
		return ticketid;
	}
	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}
	public String getS_profession() {
		return s_profession;
	}
	public void setS_profession(String s_profession) {
		this.s_profession = s_profession;
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
	public String getReportdate() {
		return reportdate;
	}
	public void setReportdate(String reportdate) {
		this.reportdate = reportdate;
	}
	public String getReportedpriority() {
		return reportedpriority;
	}
	public void setReportedpriority(String reportedpriority) {
		this.reportedpriority = reportedpriority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReportedby() {
		return reportedby;
	}
	public void setReportedby(String reportedby) {
		this.reportedby = reportedby;
	}
	
	
	
}
