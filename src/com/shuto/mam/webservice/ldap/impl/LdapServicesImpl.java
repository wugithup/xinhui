/**   
* @Title: LdapServicesImpl.java 
* @Package com.shuto.mam.webservice.ldap.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lull lull@shuto.cn
* @date 2017年5月19日 上午11:32:09 
* @version V1.0.0
*/
package com.shuto.mam.webservice.ldap.impl;

import com.shuto.mam.webservice.ldap.LdapServices;
import com.shuto.mam.webservice.ldap.bussiness.User;

/**
 * @ClassName: LdapServicesImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年5月19日 上午11:32:09
 * 
 */
public class LdapServicesImpl implements LdapServices {
	// com.shuto.mam.webservice.ldap.impl.LdapServicesImpl

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shuto.mam.webservice.ldap.LdapServices#synUser(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public String synUser(String username, String password, String requestData) {
		User user = new User();
		return user.synUser(username, password, requestData);
	}

}
