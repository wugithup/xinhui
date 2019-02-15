package com.shuto.mam.app.stpi.impdata.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PITaskCfgEntity implements Serializable {
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	private static final long serialVersionUID = 6744400705945449503L;
	private String professional;
	private String type;
	private String description;
	private String siteid;
	private String orgid;
	private Date beginTime;
	private Date endTime;
	private String cycleUnit;
	private int cycle;
	private String postNo;
	private String dataformatNo;
	private String dutyInfoNo;
	private String dayCycle;
	private int deviceseq;

	private List<PIAreaEntity> piAreaEntities = new ArrayList<PIAreaEntity>();

	public PITaskCfgEntity(String professional, String type, String description, String siteid, String orgid,
			Date beginTime, Date endTime, String cycleUnit, int cycle, String postNo, String dataformatNo,
			String dutyInfoNo,String dayCycle,int deviceseq) {
		super();
		this.professional = professional;
		this.type = type;
		this.description = description;
		this.siteid = siteid;
		this.orgid = orgid;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.cycleUnit = cycleUnit;
		this.cycle = cycle;
		this.postNo = postNo;
		this.dataformatNo = dataformatNo;
		this.dutyInfoNo = dutyInfoNo;
		this.dayCycle = dayCycle;
		this.deviceseq=deviceseq;
	}
	
	

	public int getDeviceseq() {
		return deviceseq;
	}



	public void setDeviceseq(int deviceseq) {
		this.deviceseq = deviceseq;
	}



	/**
	 * @return the dayCycle
	 */
	public String getDayCycle() {
		return dayCycle;
	}

	/**
	 * @param dayCycle
	 *            the dayCycle to set
	 */
	public void setDayCycle(String dayCycle) {
		this.dayCycle = dayCycle;
	}

	/**
	 * @return the professional
	 */
	public String getProfessional() {
		return professional;
	}

	/**
	 * @param professional
	 *            the professional to set
	 */
	public void setProfessional(String professional) {
		this.professional = professional;
	}

	/**
	 * @return the type
	 */
	public String piTaskCfgEntity() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the siteid
	 */
	public String getSiteid() {
		return siteid;
	}

	/**
	 * @param siteid
	 *            the siteid to set
	 */
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	/**
	 * @return the orgid
	 */
	public String getOrgid() {
		return orgid;
	}

	/**
	 * @param orgid
	 *            the orgid to set
	 */
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	/**
	 * @return the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            the beginTime to set
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the cycleUnit
	 */
	public String getCycleUnit() {
		return cycleUnit;
	}

	/**
	 * @param cycleUnit
	 *            the cycleUnit to set
	 */
	public void setCycleUnit(String cycleUnit) {
		this.cycleUnit = cycleUnit;
	}

	/**
	 * @return the cycle
	 */
	public int getCycle() {
		return cycle;
	}

	/**
	 * @param cycle
	 *            the cycle to set
	 */
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	/**
	 * @return the postNo
	 */
	public String getPostNo() {
		return postNo;
	}

	/**
	 * @param postNo
	 *            the postNo to set
	 */
	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	/**
	 * @return the dataformatNo
	 */
	public String getDataformatNo() {
		return dataformatNo;
	}

	/**
	 * @param dataformatNo
	 *            the dataformatNo to set
	 */
	public void setDataformatNo(String dataformatNo) {
		this.dataformatNo = dataformatNo;
	}

	/**
	 * 向集合中添加 区域信息 如果区域信息已存在则不添加
	 * 
	 * @param piAreaEntity
	 */
	public PIAreaEntity addPiArea(PIAreaEntity piAreaEntity) {
		// 获取数据所在的位置
		int index = this.piAreaEntities.lastIndexOf(piAreaEntity);
		if (index != -1) {// 如果存在则获取已存在的区域
			piAreaEntity = this.piAreaEntities.get(index);
		} else {
			this.piAreaEntities.add(piAreaEntity);
		}
		return piAreaEntity;
	}

	/**
	 * @return the dutyInfoNo
	 */
	public String getDutyInfoNo() {
		return dutyInfoNo;
	}

	/**
	 * @param dutyInfoNo
	 *            the dutyInfoNo to set
	 */
	public void setDutyInfoNo(String dutyInfoNo) {
		this.dutyInfoNo = dutyInfoNo;
	}

	/**
	 * @return the piAreaEntities
	 */
	public List<PIAreaEntity> getPiAreaEntities() {
		return piAreaEntities;
	}

	/**
	 * @param piAreaEntities
	 *            the piAreaEntities to set
	 */
	public void setPiAreaEntities(List<PIAreaEntity> piAreaEntities) {
		this.piAreaEntities = piAreaEntities;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PITaskCfgEntity other = (PITaskCfgEntity) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

}
