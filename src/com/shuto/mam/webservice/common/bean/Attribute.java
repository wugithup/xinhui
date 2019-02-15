/**
 * 接口属性
 */
package com.shuto.mam.webservice.common.bean;

public class Attribute {
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 字段名
	 */
	private String columnName;
	/**
	 * 字段别名
	 */
	private String columAlias;
	/**
	 * 字段类型
	 */
	private String columnType;
	/**
	 * 字段长度
	 */
	private String columnSize;
	/**
	 * 取值类型
	 */
	private String valueType;
	/**
	 * 接口对外字段名
	 */
	private String faceName;
	/**
	 * 数据来源类型(属性、固定值)
	 */
	private String sourceType;
	/**
	 * 固定值
	 */
	private String value;
	/**
	 * 是否必填
	 */
	private boolean isRequired;
	/**
	 * 备注
	 */
	private String remark;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumAlias() {
		return columAlias;
	}
	public void setColumAlias(String columAlias) {
		this.columAlias = columAlias;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(String columnSize) {
		this.columnSize = columnSize;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
