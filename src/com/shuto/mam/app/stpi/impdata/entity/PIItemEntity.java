package com.shuto.mam.app.stpi.impdata.entity;

import java.io.Serializable;

public class PIItemEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String description;
	private String deviceName;
	private String devicePartName;
	private String pointType;
	private String pointUnit;
	private String higherLimit;
	private String lowerLimit;
	private String checkMethod;
	private String sharkType;
	private String onOffPoint;
	private String pointNorm;
	private String isPCheck;
	private String type;
	private String location;
	private String deviceNo;
	private int itemseq;
	private int deviceseq;

	public int getItemseq() {
		return itemseq;
	}

	public void setItemseq(int itemseq) {
		this.itemseq = itemseq;
	}

	public int getDeviceseq() {
		return deviceseq;
	}

	public void setDeviceseq(int deviceseq) {
		this.deviceseq = deviceseq;
	}

	
	

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * @param deviceNo
	 *            the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
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
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName
	 *            the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the devicePartName
	 */
	public String getDevicePartName() {
		return devicePartName;
	}

	/**
	 * @param devicePartName
	 *            the devicePartName to set
	 */
	public void setDevicePartName(String devicePartName) {
		this.devicePartName = devicePartName;
	}

	/**
	 * @return the pointType
	 */
	public String getPointType() {
		return pointType;
	}

	/**
	 * @param pointType
	 *            the pointType to set
	 */
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	/**
	 * @return the pointUnit
	 */
	public String getPointUnit() {
		return pointUnit;
	}

	/**
	 * @param pointUnit
	 *            the pointUnit to set
	 */
	public void setPointUnit(String pointUnit) {
		this.pointUnit = pointUnit;
	}

	/**
	 * @return the higherLimit
	 */
	public String getHigherLimit() {
		return higherLimit;
	}

	/**
	 * @param higherLimit
	 *            the higherLimit to set
	 */
	public void setHigherLimit(String higherLimit) {
		this.higherLimit = higherLimit;
	}

	/**
	 * @return the lowerLimit
	 */
	public String getLowerLimit() {
		return lowerLimit;
	}

	/**
	 * @param lowerLimit
	 *            the lowerLimit to set
	 */
	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	/**
	 * @return the checkMethod
	 */
	public String getCheckMethod() {
		return checkMethod;
	}

	/**
	 * @param checkMethod
	 *            the checkMethod to set
	 */
	public void setCheckMethod(String checkMethod) {
		this.checkMethod = checkMethod;
	}

	/**
	 * @return the sharkType
	 */
	public String getSharkType() {
		return sharkType;
	}

	/**
	 * @param sharkType
	 *            the sharkType to set
	 */
	public void setSharkType(String sharkType) {
		this.sharkType = sharkType;
	}

	/**
	 * @return the onOffPoint
	 */
	public String getOnOffPoint() {
		return onOffPoint;
	}

	/**
	 * @param onOffPoint
	 *            the onOffPoint to set
	 */
	public void setOnOffPoint(String onOffPoint) {
		this.onOffPoint = onOffPoint;
	}

	/**
	 * @return the pointNorm
	 */
	public String getPointNorm() {
		return pointNorm;
	}

	/**
	 * @param pointNorm
	 *            the pointNorm to set
	 */
	public void setPointNorm(String pointNorm) {
		this.pointNorm = pointNorm;
	}

	/**
	 * @return the isPCheck
	 */
	public int getIsPCheck() {
		return isPCheck.equals("是") ? 1 : 0;
	}

	/**
	 * @param isPCheck
	 *            the isPCheck to set
	 */
	public void setIsPCheck(String isPCheck) {
		this.isPCheck = isPCheck;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public PIItemEntity(String description, String deviceName, String devicePartName, String pointType,
			String pointUnit, String higherLimit, String lowerLimit, String checkMethod, String sharkType,
			String onOffPoint, String pointNorm, String isPCheck, String type, String location, String deviceNo,int areaseq,int itemseq,int deviceseq) {
		super();
		this.description = description;
		this.deviceName = deviceName;
		this.devicePartName = devicePartName;
		this.pointType = pointType;
		this.pointUnit = pointUnit;
		this.higherLimit = higherLimit;
		this.lowerLimit = lowerLimit;
		this.checkMethod = checkMethod;
		this.sharkType = sharkType;
		this.onOffPoint = onOffPoint;
		this.pointNorm = pointNorm;
		this.isPCheck = isPCheck;
		this.type = type;
		this.location = location;
		this.deviceNo = deviceNo;
		this.deviceseq=deviceseq;
		this.itemseq=itemseq;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
		result = prime * result + ((devicePartName == null) ? 0 : devicePartName.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PIItemEntity other = (PIItemEntity) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (deviceName == null) {
			if (other.deviceName != null)
				return false;
		} else if (!deviceName.equals(other.deviceName))
			return false;
		if (devicePartName == null) {
			if (other.devicePartName != null)
				return false;
		} else if (!devicePartName.equals(other.devicePartName))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
