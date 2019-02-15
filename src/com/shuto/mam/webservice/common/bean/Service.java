/**
 * 
 */
package com.shuto.mam.webservice.common.bean;

import java.util.List;
import java.util.Map;

/**
 * @author ITROBOT
 *
 */
public class Service {
	/**
	 * 服务名
	 */
	private String serviceName;
	/**
	 * 服务类型
	 */
	private String serviceType;
	/**
	 * 服务类
	 */
	private String serviceClass;
	/**
	 * 数据输入/输出格式(xml/json)
	 */
	private String ioFormat;
	/**
	 * 数据库jdbc url
	 */
	private String dburl;
	/**
	 * 数据库用户名
	 */
	private String dbuser;
	/**
	 * 数据库密码
	 */
	private String dbpwd;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否分页输出
	 */
	private boolean ynpage;
	/**
	 * 每页最大记录数
	 */
	private Integer maxPageCount;
	/**
//	 * 接口数据加密类型(BASE64)
	 */
	private String encryptType;
	/**
	 * 查询服务根节点(一个查询服务只能对应一个查询服务根节点)
	 */
	private Node rootNode;
	/**
	 * 插入数据根节点(一个上传服务可以对应多个数据插入根节点)
	 */
	private List<Node> rootNodes;
	/**
	 * 接口服务对应的所有节点
	 */
	private List<Node> allNodes;
	/**
	 * 服务输入参数
	 */
	private Map<String,String> parameters;
	/**
	 * 接口服务对接方Map<对接方标识ID,对接方对象>
	 */
	private Map<String,ServicePartner> partners;
	/**
	 * 接口服务签名参数
	 */
	private Map<Integer,String> signparams;
	
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceClass() {
		return serviceClass;
	}
	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}
	public String getIoFormat() {
		return ioFormat;
	}
	public void setIoFormat(String ioFormat) {
		this.ioFormat = ioFormat;
	}
	public String getDburl() {
		return dburl;
	}
	public void setDburl(String dburl) {
		this.dburl = dburl;
	}
	public String getDbuser() {
		return dbuser;
	}
	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}
	public String getDbpwd() {
		return dbpwd;
	}
	public void setDbpwd(String dbpwd) {
		this.dbpwd = dbpwd;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isYnpage() {
		return ynpage;
	}
	public void setYnpage(boolean ynpage) {
		this.ynpage = ynpage;
	}
	public Integer getMaxPageCount() {
		return maxPageCount;
	}
	public void setMaxPageCount(Integer maxPageCount) {
		this.maxPageCount = maxPageCount;
	}
	public String getEncryptType() {
		return encryptType;
	}
	public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}
	public Node getRootNode() {
		return rootNode;
	}
	public void setRootNode(Node rootNode) {
		this.rootNode = rootNode;
	}
	public List<Node> getRootNodes() {
		return rootNodes;
	}
	public void setRootNodes(List<Node> rootNodes) {
		this.rootNodes = rootNodes;
	}
	public List<Node> getAllNodes() {
		return allNodes;
	}
	public void setAllNodes(List<Node> allNodes) {
		this.allNodes = allNodes;
	}
	public Map<String, String> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	public Map<String, ServicePartner> getPartners() {
		return partners;
	}
	public void setPartners(Map<String, ServicePartner> partners) {
		this.partners = partners;
	}
	public Map<Integer, String> getSignparams() {
		return signparams;
	}
	public void setSignparams(Map<Integer, String> signparams) {
		this.signparams = signparams;
	}
}
