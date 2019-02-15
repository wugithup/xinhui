/**
 * 接口参数
 */
package com.shuto.mam.webservice.common.bean;

/**
 * @author ITROBOT
 *
 */
public class Parameter {
	/**
	 * 参数名
	 */
	private String name;
	/**
	 * 参数类型
	 */
	private String type;
	/**
	 * 序号
	 */
	private int snum;
	/**
	 * 接口映射参数名
	 */
	private String faceName;
	/**
	 * 数据来源类型(属性、固定值、序列)
	 */
	private String sourceType;
	/**
	 * 参数值
	 */
	private String value;
	/**
	 * 参数是否必填
	 */
	private boolean isRequired;
	/**
	 * 是否序列
	 */
	private boolean isSequence;
	/**
	 * 序列名
	 */
	private String sequenceName;
	/**
	 * 是否自动编码
	 */
	private boolean isAutoNumber;
	/**
	 * 自动编码名
	 */
	private String AutoNumberName;
	/**
	 * 是否从父级取值
	 */
	private boolean isFromParent;
	/**
	 * 父级取值参数名
	 */
	private String parentParamet;
	/**
	 * 是否作为更新的条件属性
	 */
	private boolean isUpdateCondition;
	/**
	 * 参数类
	 */
	private String parametClass;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 比较运算符(=,<>,<,>,<=,>=)
	 */
	private String comparemark;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSnum() {
		return snum;
	}
	public void setSnum(int snum) {
		this.snum = snum;
	}
	public String getFaceName() {
		return faceName;
	}
	public void setFaceName(String faceName) {
		this.faceName = faceName;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isRequired() {
		return isRequired;
	}
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	public boolean isSequence() {
		return isSequence;
	}
	public void setSequence(boolean isSequence) {
		this.isSequence = isSequence;
	}
	public String getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	public boolean isAutoNumber() {
		return isAutoNumber;
	}
	public void setAutoNumber(boolean isAutoNumber) {
		this.isAutoNumber = isAutoNumber;
	}
	public String getAutoNumberName() {
		return AutoNumberName;
	}
	public void setAutoNumberName(String autoNumberName) {
		AutoNumberName = autoNumberName;
	}
	public boolean isFromParent() {
		return isFromParent;
	}
	public void setFromParent(boolean isFromParent) {
		this.isFromParent = isFromParent;
	}
	public String getParentParamet() {
		return parentParamet;
	}
	public void setParentParamet(String parentParamet) {
		this.parentParamet = parentParamet;
	}
	public boolean isUpdateCondition() {
		return isUpdateCondition;
	}
	public void setUpdateCondition(boolean isUpdateCondition) {
		this.isUpdateCondition = isUpdateCondition;
	}
	public String getParametClass() {
		return parametClass;
	}
	public void setParametClass(String parametClass) {
		this.parametClass = parametClass;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getComparemark() {
		return comparemark;
	}
	public void setComparemark(String comparemark) {
		this.comparemark = comparemark;
	}
}
