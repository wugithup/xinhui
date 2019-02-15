/**
 * 
 */
package com.shuto.mam.webservice.ebs.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dom4j.Element;

import com.shuto.mam.webservice.common.bean.Parameter;
import com.shuto.mam.webservice.common.service.BaseParameter;
import com.shuto.mam.webservice.ebs.bean.OrgMap;
import com.shuto.mam.webservice.ebs.util.JcacheUtil;

/**
 * @author ITROBOT
 *
 */
public class ParameterSite implements BaseParameter {

	/* (non-Javadoc)
	 * @see com.shuto.mam.webservice.common.service.BaseParameter#initValue(com.shuto.mam.webservice.common.bean.Parameter, org.dom4j.Element, java.sql.Connection)
	 */
	@Override
	public String initValue(Parameter p, Element elm, Connection conn) {
		// TODO Auto-generated method stub
		String value = null;
		String interorg = elm.elementText("maximo_org_code");
		String intersite = elm.elementText("maximo_location");
		OrgMap orgmap = JcacheUtil.getOrgFromCache(interorg, intersite, conn);
		if (orgmap != null) {
			value = orgmap.getMaxsite();
		}
		return value;
	}

}
