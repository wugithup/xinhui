/**
 * 
 */
package com.shuto.mam.webservice.ebs.bean;

/**
 * @author ITROBOT
 *
 */
public class OrgMap {
	/**
	 * maximo组织ID
	 */
	private String maxorg;
	/**
	 * maximo站点ID
	 */
	private String maxsite;
	/**
	 * 接口映射组织ID
	 */
	private String interorg;
	/**
	 * 接口映射站点ID
	 */
	private String intersite;
	/**
	 * 接口对接系统标识ID
	 */
	private String partnerid;
	/**
	 * 财务核算组织
	 */
	private String interfaceorgtype;
	
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
	public String getInterorg() {
		return interorg;
	}
	public void setInterorg(String interorg) {
		this.interorg = interorg;
	}
	public String getIntersite() {
		return intersite;
	}
	public void setIntersite(String intersite) {
		this.intersite = intersite;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getInterfaceorgtype() {
		return interfaceorgtype;
	}
	public void setInterfaceorgtype(String interfaceorgtype) {
		this.interfaceorgtype = interfaceorgtype;
	}
}
