package com.shuto.mam.app.stpi.impdata.entity;

import java.io.Serializable;
import java.util.Date;


import cc.aicode.e2e.annotation.ExcelEntity;
import cc.aicode.e2e.annotation.ExcelProperty;

/**
 * 
 * @author shixw
 * @date 2017-05-15
 * @use 数据导入Excel解析对应的实体类
 */
@ExcelEntity
public class ImportDataEntity implements Serializable{
	private static final long serialVersionUID = 311050161418598817L;

	@ExcelProperty(value = "专业", required = true)
	private String professional;

	@ExcelProperty(value = "任务类型", required = true)
	private String type;

	@ExcelProperty(value = "巡点检任务配置名称", required = true)
	private String taskcfgName;

	@ExcelProperty(value = "巡点检区域名称", required = true)
	private String areaName;

	@ExcelProperty(value = "射频卡", required = true)
	private String rfidCode;
	
	//新加的
	@ExcelProperty(value = "区域排序字段", required = true)
	private int areaseq;

	@ExcelProperty(value = "设备编号", required = false)
	private String deviceNo;

	@ExcelProperty(value = "设备名称", required = true)
	private String deviceName;

	@ExcelProperty(value = "KKS编码", required = false)
	private String location;

	@ExcelProperty(value = "巡点检部位部件", required = true)
	private String devicePartName;
	
	//新加的
	@ExcelProperty(value = "设备排序字段", required = true)
	private int deviceseq;

	@ExcelProperty(value = "巡点检项目", required = true)
	private String checkProject;
	
	//新加的
	@ExcelProperty(value = "项目排序字段", required = true)
	private int itemseq;

	@ExcelProperty(value = "测点类型")
	private String pointType;

	@ExcelProperty(value = "点检方法", required = true)
	private String checkMethod;

	@ExcelProperty(value = "设备状态", required = true)
	private String onOffPotin;

	@ExcelProperty(value = "数据单位", required = true)
	private String pointUnit;

	@ExcelProperty(value = "上限值")
	private String higherLimit;

	@ExcelProperty(value = "下限值")
	private String lowerLimit;

	@ExcelProperty(value = "振动类型", required = true)
	private String shakeType;

	@ExcelProperty(value = "点检标准", required = true)
	private String pointNorm;

	@ExcelProperty(value = "备用时是否检查", required = true)
	private String isPcheck;

	@ExcelProperty("siteid")
	private String siteid;

	@ExcelProperty("orgid")
	private String orgid;

	@ExcelProperty(value = "岗位编号", required = true)
	private String postNo;

	@ExcelProperty(value = "设备类型", required = true)
	private String dataFormatNo;

	@ExcelProperty(value = "倒班编号")
	private String dutyinfoNum;
	
	@ExcelProperty(value = "周期标识")
	private String cycleUnit;

	@ExcelProperty(value = "周期", required = true)
	private int cycle;

	
	@ExcelProperty(value = "开始时间")
	private Date startDate;

	@ExcelProperty(value = "结束时间")
	private Date endDate;
	
	@ExcelProperty(value = "每日巡检周期")
    private String dayCycle;
	/**
	 * @return the professional
	 */
	public String getProfessional() {
		return professional;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the taskcfgName
	 */
	public String getTaskcfgName() {
		return taskcfgName;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @return the rfidCode
	 */
	public String getRfidCode() {
		return rfidCode;
	}
	
	
	/**
	 * @return the areaseq   新加的
	 */
	public int getAreaseq(){
		return areaseq;
	}

	/**
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the devicePartName
	 */
	public String getDevicePartName() {
		return devicePartName;
	}
	
	/**
	 * @return the deviceseq 新加的
	 */
	public int getDeviceseq(){
		return deviceseq;
	}

	/**
	 * @return the checkProject
	 */
	public String getCheckProject() {
		return checkProject;
	}
	
	/**
	 * @return the itemseq 新加的
	 */
	public int getItemseq(){
		return itemseq;
	}

	/**
	 * @return the pointType
	 */
	public String getPointType() {
		return pointType;
	}

	/**
	 * @return the checkMethod
	 */
	public String getCheckMethod() {
		return checkMethod;
	}

	/**
	 * @return the onOffPotin
	 */
	public String getOnOffPotin() {
		return onOffPotin;
	}

	/**
	 * @return the pointUnit
	 */
	public String getPointUnit() {
		return pointUnit;
	}

	/**
	 * @return the shakeType
	 */
	public String getShakeType() {
		return shakeType;
	}

	/**
	 * @return the pointNorm
	 */
	public String getPointNorm() {
		return pointNorm;
	}

	/**
	 * @return the isPcheck
	 */
	public String getIsPcheck() {
		return isPcheck;
	}

	/**
	 * @return the siteid
	 */
	public String getSiteid() {
		return siteid;
	}

	/**
	 * @return the orgid
	 */
	public String getOrgid() {
		return orgid;
	}

	/**
	 * @return the postNo
	 */
	public String getPostNo() {
		return postNo;
	}

	/**
	 * @return the dataFormatNo
	 */
	public String getDataFormatNo() {
		return dataFormatNo;
	}

	/**
	 * @return the dutyinfoNum
	 */
	public String getDutyinfoNum() {
		return dutyinfoNum;
	}

	/**
	 * @return the cycleUnit
	 */
	public String getCycleUnit() {
		return cycleUnit;
	}

	/**
	 * @return the cycle
	 */
	public int getCycle() {
		return cycle;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param professional the professional to set
	 */
	public void setProfessional(String professional) {
		this.professional = professional;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param taskcfgName the taskcfgName to set
	 */
	public void setTaskcfgName(String taskcfgName) {
		this.taskcfgName = taskcfgName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @param rfidCode the rfidCode to set
	 */
	public void setRfidCode(String rfidCode) {
		this.rfidCode = rfidCode;
	}
	
	
	/**
	 * @param areaseq the areaseq to set
	 */
	public void setAreaseq(int areaseq){
		this.areaseq=areaseq;
	}

	/**
	 * @param deviceNo the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param devicePartName the devicePartName to set
	 */
	public void setDevicePartName(String devicePartName) {
		this.devicePartName = devicePartName;
	}
	
	/**
	 * @param deviceseq the deviceseq to set新加的
	 */
	public void setDeviceseq(int deviceseq){
		this.deviceseq=deviceseq;
	}

	/**
	 * @param checkProject the checkProject to set
	 */
	public void setCheckProject(String checkProject) {
		this.checkProject = checkProject;
	}
	
	/**
	 * @param itemseq the itemseq to set新加的
	 */
	public void setitemseq(int itemseq){
		this.itemseq=itemseq;
	}

	/**
	 * @param pointType the pointType to set
	 */
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	/**
	 * @param checkMethod the checkMethod to set
	 */
	public void setCheckMethod(String checkMethod) {
		this.checkMethod = checkMethod;
	}

	/**
	 * @param onOffPotin the onOffPotin to set
	 */
	public void setOnOffPotin(String onOffPotin) {
		this.onOffPotin = onOffPotin;
	}

	/**
	 * @param pointUnit the pointUnit to set
	 */
	public void setPointUnit(String pointUnit) {
		this.pointUnit = pointUnit;
	}

	public String getHigherLimit() {
		return higherLimit;
	}

	public void setHigherLimit(String higherLimit) {
		this.higherLimit = higherLimit;
	}

	public String getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	/**
	 * @param shakeType the shakeType to set
	 */
	public void setShakeType(String shakeType) {
		this.shakeType = shakeType;
	}

	/**
	 * @param pointNorm the pointNorm to set
	 */
	public void setPointNorm(String pointNorm) {
		this.pointNorm = pointNorm;
	}

	/**
	 * @param isPcheck the isPcheck to set
	 */
	public void setIsPcheck(String isPcheck) {
		this.isPcheck = isPcheck;
	}

	/**
	 * @param siteid the siteid to set
	 */
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	/**
	 * @param orgid the orgid to set
	 */
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	/**
	 * @param postNo the postNo to set
	 */
	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	/**
	 * @param dataFormatNo the dataFormatNo to set
	 */
	public void setDataFormatNo(String dataFormatNo) {
		this.dataFormatNo = dataFormatNo;
	}

	/**
	 * @param dutyinfoNum the dutyinfoNum to set
	 */
	public void setDutyinfoNum(String dutyinfoNum) {
		this.dutyinfoNum = dutyinfoNum;
	}

	/**
	 * @param cycleUnit the cycleUnit to set
	 */
	public void setCycleUnit(String cycleUnit) {
		this.cycleUnit = cycleUnit;
	}

	/**
	 * @param cycle the cycle to set
	 */
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the dayCycle
	 */
	public String getDayCycle() {
		return dayCycle;
	}

	/**
	 * @param dayCycle the dayCycle to set
	 */
	public void setDayCycle(String dayCycle) {
		this.dayCycle = dayCycle;
	}

	public void setItemseq(int itemseq) {
		this.itemseq = itemseq;
	}
	
	


}
