package com.shuto.mam.webclient.beans.stpi.pi_importdata;

import java.sql.Timestamp;

import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.CheckMethodCheckRule;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.CycleCheckRule;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.DataFormatNoCheckRule;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.DayCycleCheckRule;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.DutyinfoNumCheckRule;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.EmptyAndSizeCheckRule;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.IsPcheckCheckRule;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.OnOffPotinCheckRule;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.PostNoCheckRule;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.ProfessionalCheckRule;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.ShakeTypeCheckRule;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.SizeCheckRule;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.validator.TypeCheckRule2;

import cc.aicode.e2e.annotation.ExcelEntity;
import cc.aicode.e2e.annotation.ExcelProperty;

@ExcelEntity
public class PiXjData {
	@ExcelProperty(value = "专业", rule = ProfessionalCheckRule.class,required = true)
	private String professional;
	
	@ExcelProperty(value = "任务类型", rule = TypeCheckRule2.class,required = true)
	private String type;
	
	@ExcelProperty(value = "巡点检任务配置名称", rule = EmptyAndSizeCheckRule.class,required = true)
	private String taskcfgName;
	
	@ExcelProperty(value = "巡点检区域名称", rule = EmptyAndSizeCheckRule.class,required = true)
	private String areaName;
	
	@ExcelProperty(value = "射频卡", rule = EmptyAndSizeCheckRule.class,required = true)
	private String rfidCode;
	
	@ExcelProperty(value = "设备编号", required = false)
	private String deviceNo;
	
	@ExcelProperty(value = "设备名称", rule = EmptyAndSizeCheckRule.class,required = true)
	private String deviceName;
	
	@ExcelProperty(value = "KKS编码", required = false)
	private String location;
	
	@ExcelProperty(value = "巡点检部位部件", rule = EmptyAndSizeCheckRule.class,required = true)
	private String devicePartName;
	
	@ExcelProperty(value = "巡点检项目", rule = EmptyAndSizeCheckRule.class,required = true)
	private String checkProject;
	@ExcelProperty("测点类型")
	private String pointType;
	
	/**
	 * @return the point_type
	 */
	public String getPointType() {
		return pointType;
	}

	/**
	 * @param point_type the point_type to set
	 */
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	@ExcelProperty(value = "点检方法", rule = CheckMethodCheckRule.class,required = true)
	private String checkMethod;
	
	@ExcelProperty(value = "设备状态", rule = OnOffPotinCheckRule.class,required = true)
	private String onOffPotin;
	
	@ExcelProperty(value = "数据单位", rule = SizeCheckRule.class,required = true)
	private String pointUnit;
	
	@ExcelProperty("上限值")
	private String higherLimit;
	
	@ExcelProperty("下限值")
	private String lowerLimit;
	
	@ExcelProperty(value = "振动类型", rule = ShakeTypeCheckRule.class,required = true)
	private String shakeType;
	
	@ExcelProperty(value = "点检标准", rule = EmptyAndSizeCheckRule.class,required = true)
	private String pointNorm;
	
	@ExcelProperty(value = "备用时是否检查", rule = IsPcheckCheckRule.class,required = true)
	private String isPcheck;
	
	@ExcelProperty("siteid")
	private String siteid;
	
	@ExcelProperty("orgid")
	private String orgid;

	@ExcelProperty(value = "岗位编号", rule = PostNoCheckRule.class,required = true)
	private String postNo;
	
	@ExcelProperty(value = "设备类型", rule = DataFormatNoCheckRule.class,required = true)
	private String dataFormatNo;
	
	@ExcelProperty(value = "倒班编号", rule = DutyinfoNumCheckRule.class,required = true)
	private String dutyinfoNum;
	
	@ExcelProperty(value = "周期", rule = CycleCheckRule.class,required = true)
    private String cycle;
	
	@ExcelProperty(value = "开始时间")
    private Timestamp startDate;
	
	@ExcelProperty(value = "结束时间")
    private Timestamp endDate;
	
	@ExcelProperty(value = "每日巡检周期", rule = DayCycleCheckRule.class,required = true)
    private String dayCycle;
	
	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTaskcfgName() {
		return taskcfgName;
	}

	public void setTaskcfgName(String taskcfgName) {
		this.taskcfgName = taskcfgName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getRfidCode() {
		return rfidCode;
	}

	public void setRfidCode(String rfidCode) {
		this.rfidCode = rfidCode;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDevicePartName() {
		return devicePartName;
	}

	public void setDevicePartName(String devicePartName) {
		this.devicePartName = devicePartName;
	}

	public String getCheckProject() {
		return checkProject;
	}

	public void setCheckProject(String checkProject) {
		this.checkProject = checkProject;
	}

	public String getCheckMethod() {
		return checkMethod;
	}

	public void setCheckMethod(String checkMethod) {
		this.checkMethod = checkMethod;
	}

	public String getOnOffPotin() {
		return onOffPotin;
	}

	public void setOnOffPotin(String onOffPotin) {
		this.onOffPotin = onOffPotin;
	}

	public String getPointUnit() {
		return pointUnit;
	}

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

	public String getShakeType() {
		return shakeType;
	}

	public void setShakeType(String shakeType) {
		this.shakeType = shakeType;
	}

	public String getPointNorm() {
		return pointNorm;
	}

	public void setPointNorm(String pointNorm) {
		this.pointNorm = pointNorm;
	}

	public String getIsPcheck() {
		return isPcheck;
	}

	public void setIsPcheck(String isPcheck) {
		this.isPcheck = isPcheck;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	
	public String getPostNo() {
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getDataFormatNo() {
		return dataFormatNo;
	}

	public void setDataFormatNo(String dataFormatNo) {
		this.dataFormatNo = dataFormatNo;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	
	public String getDutyinfoNum() {
		return dutyinfoNum;
	}

	public void setDutyinfoNum(String dutyinfoNum) {
		this.dutyinfoNum = dutyinfoNum;
	}

	public String getDayCycle() {
		return dayCycle;
	}

	public void setDayCycle(String dayCycle) {
		this.dayCycle = dayCycle;
	}

	@Override
    public String toString() {
		String str = professional+"--"+type+"--"+taskcfgName+"--"+areaName+"--"+rfidCode+"--"+deviceNo+"--"+deviceName+"--"+location+"--"+devicePartName+"--"+checkProject+"--"+checkMethod+"--"+onOffPotin+"--"+pointUnit+"--"+higherLimit+"--"+lowerLimit+"--"+shakeType+"--"+pointNorm+"--"+isPcheck+"--"+siteid+"--"+orgid;

        return str;
    }
	
}
