package com.shuto.mam.webservice.common.bean;

/**
 * @Title: ServicePartner.java 
 * @Description: 接口服务对接方   
 * @author itrobot 
 * @date 2017-6-12 下午05:34:03 
 * @version V1.0 
 */
public class ServicePartner {
	/**
	 * 对接方编号
	 */
	private String partnerNum;
	/**
	 * 对接方标识ID
	 */
	private String partnerId;
	/**
	 * 对接方密钥
	 */
	private String secretKey;
	/**
	 * 排序字段
	 */
	private Integer orderNum;
	
	public String getPartnerNum() {
		return partnerNum;
	}
	public void setPartnerNum(String partnerNum) {
		this.partnerNum = partnerNum;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
