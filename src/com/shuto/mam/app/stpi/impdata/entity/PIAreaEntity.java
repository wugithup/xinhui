package com.shuto.mam.app.stpi.impdata.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author shixw
 * @date 2017年5月15日
 * @use 任务配置区域实体类
 */
public class PIAreaEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String description;
	private String rfidCode;
	private int areaseq;
	
	private List<PIItemEntity> piItemEntities = new LinkedList<PIItemEntity>();
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the rfidCode
	 */
	public String getRfidCode() {
		return rfidCode;
	}
	/**
	 * @param rfidCode the rfidCode to set
	 */
	public void setRfidCode(String rfidCode) {
		this.rfidCode = rfidCode;
	}
	
	public int getAreaseq() {
		return areaseq;
	}
	public void setAreaseq(int areaseq) {
		this.areaseq = areaseq;
	}
	
	public PIAreaEntity(String description, String rfidCode,int areaseq) {
		super();
		this.description = description;
		this.rfidCode = rfidCode;
		this.areaseq = areaseq;
	}
	
	/**
	 * @return the piItemEntities
	 */
	public List<PIItemEntity> getPiItemEntities() {
		return piItemEntities;
	}
	/**
	 * @param piItemEntities the piItemEntities to set
	 */
	public void setPiItemEntities(List<PIItemEntity> piItemEntities) {
		this.piItemEntities = piItemEntities;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((rfidCode == null) ? 0 : rfidCode.hashCode());
		return result;
	}
	/* (non-Javadoc)
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
		PIAreaEntity other = (PIAreaEntity) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (rfidCode == null) {
			if (other.rfidCode != null)
				return false;
		} else if (!rfidCode.equals(other.rfidCode))
			return false;
		return true;
	}
	
	
}
