package com.shuto.mam.webservice.common.bean;

import java.util.List;
import java.util.Map;

public class Node {
	/**
	 * 节点编号
	 */
	private String nodeNum;
	/**
	 * 节点名称
	 */
	private String nodeName;
	/**
	 * 父节点编号
	 */
	private String parentNum;
	/**
	 * 节点层级
	 */
	private int layer;
	/**
	 * 节点对应的表
	 */
	private String tableName;
	/**
	 * 节点sql
	 */
	private String sql;
	/**
	 * 节点sql参数
	 */
	private Map<String,Parameter> parameters;
	/**
	 * 节点属性
	 */
	private List<Attribute> attributes;
	/**
	 * 子节点
	 */
	private List<Node> subNodes;
	/**
	 * 数据更新标识字段
	 */
	private Map<String,String> updateMark;
	/**
	 * 数据更新条件属性
	 */
	private Map<String,Parameter> updateConditions;
	/**
	 * 备注
	 */
	private String remark;
	
	public String getNodeNum() {
		return nodeNum;
	}
	public void setNodeNum(String nodeNum) {
		this.nodeNum = nodeNum;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getParentNum() {
		return parentNum;
	}
	public void setParentNum(String parentNum) {
		this.parentNum = parentNum;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Map<String, Parameter> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, Parameter> parameters) {
		this.parameters = parameters;
	}
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	public List<Node> getSubNodes() {
		return subNodes;
	}
	public void setSubNodes(List<Node> subNodes) {
		this.subNodes = subNodes;
	}
	public Map<String, String> getUpdateMark() {
		return updateMark;
	}
	public void setUpdateMark(Map<String, String> updateMark) {
		this.updateMark = updateMark;
	}
	public Map<String, Parameter> getUpdateConditions() {
		return updateConditions;
	}
	public void setUpdateConditions(Map<String, Parameter> updateConditions) {
		this.updateConditions = updateConditions;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
