/**   
* @Title: LdapServices.java 
* @Package com.shuto.mam.webservice.ldap.bussiness 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lull lull@shuto.cn
* @date 2017年5月19日 上午11:31:16 
* @version V1.0.0
*/
package com.shuto.mam.webservice.ldap;

/**
 * @ClassName: LdapServices
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年5月19日 上午11:31:16
 * 
 */
public interface LdapServices {
	// com.shuto.mam.webservice.ldap.LdapServices
	public String synUser(String username, String password, String requestData);
}
