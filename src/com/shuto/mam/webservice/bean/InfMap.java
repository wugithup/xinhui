/**
 * 接口数据映射对象
 */
package com.shuto.mam.webservice.bean;

/**
 * @author ITROBOT
 *
 */
public class InfMap {
	/**
	 * maximo组织
	 */
	private String maxorg;
	/**
	 * maximo地点
	 */
	private String maxsite;
	/**
	 * maximo值
	 */
	private String maxval;
	/**
	 * 接口值
	 */
	private String infval;
	/**
	 * 接口业务标识
	 */
	private String business;
	
	public String getMaxorg() {
		return maxorg;
	}
	public void setMaxorg(String maxorg) {
		this.maxorg = maxorg;
	}
	public String getMaxsite() {
		return maxsite;
	}
	public void setMaxsite(String maxsite) {
		this.maxsite = maxsite;
	}
	public String getMaxval() {
		return maxval;
	}
	public void setMaxval(String maxval) {
		this.maxval = maxval;
	}
	public String getInfval() {
		return infval;
	}
	public void setInfval(String infval) {
		this.infval = infval;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
}
