/**   
* @Title: PersonResultBean.java 
* @Package com.shuto.mam.webclient.beans.person 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lull lull@shuto.cn
* @date 2017年5月15日 下午4:49:20 
* @version V1.0.0
*/
package com.shuto.mam.webclient.beans.person;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.ResultsBean;

/**
 * @ClassName: PersonResultBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年5月15日 下午4:49:20
 * 
 */
public class PersonResultBean extends ResultsBean {
	// com.shuto.mam.webclient.beans.person.PersonResultBean

	/*
	 * (non-Javadoc)
	 * 
	 * @see psdi.webclient.system.beans.DataBean#getMboSetRemote()
	 */
	@Override
	protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		MboSetRemote resultSet = super.getMboSetRemote();
		String sql = "STATUS = '活动' AND PERSONID NOT IN ('DEFLT','DEFLTREG','MAXADMIN','SYSADM','WORKFLOW','MXINTADM','MAXREG','RPTADMIN')";
		if (!resultSet.isEmpty()) {
			MboRemote mainMbo = resultSet.getMbo(0);
			String siteid = mainMbo.getInsertSite();
			String orgid = mainMbo.getInsertOrganization();
			String personid = mainMbo.getUserInfo().getPersonId();
			MboSetRemote siteSet = mainMbo.getMboSet("$SITE", "SITE", "SITEID = '" + siteid + "'");
			String yetai = siteSet.getMbo(0).getString("YETAI");
			siteSet.close();
			if ("控股".equals(yetai)) {

			} else if ("MAXADMIN".equals(personid)) {

			} else if ("本部".equals(yetai)) {
				sql = sql + " AND LOCATIONORG = '" + orgid + "'";
			} else {
				sql = sql + " AND LOCATIONSITE = '" + siteid + "'";
			}
		}

		resultSet.setWhere(sql);
		resultSet.reset();
		return resultSet;
	}
}
