package com.shuto.mam.webclient.beans.sjsq;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.ResultsBean;

/**
 * 
 * @Title: NewRysqResultsBean.java
 * @Description: TODO
 * @author: lull lull@shuto.cn
 * @date: 2017年4月21日 上午11:43:32
 * @version: V1.0.0
 */

public class NewRysqResultsBean extends ResultsBean {
	// com.shuto.mam.webclient.beans.sjsq.NewRysqResultsBean

	/**
	 * <p>
	 * Title: getMboSetRemote
	 * <p>
	 * Description:
	 * 
	 * @return
	 * @throws MXException
	 * @throws RemoteException
	 * @see psdi.webclient.system.beans.DataBean#getMboSetRemote()
	 */
	@Override
	protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		MboSetRemote resultSet = super.getMboSetRemote();
		String sql = "STATUS = '活动' AND PERSONID NOT IN ('DEFLT','DEFLTREG','MAXADMIN','SYSADM','WORKFLOW','MXINTADM','MAXREG','RPTADMIN') ";
		if (!resultSet.isEmpty()) {
			MboRemote mainMbo = resultSet.getMbo(0);
			String siteid = mainMbo.getInsertSite();
			String orgid = mainMbo.getInsertOrganization();
			MboSetRemote siteSet = mainMbo.getMboSet("$SITE", "SITE", "SITEID = '" + siteid + "'");
			String yetai = siteSet.getMbo(0).getString("YETAI");
			siteSet.close();
			if ("控股".equals(yetai)) {
				sql = sql + " AND  LOCATIONORG IS NULL ";//
			} else if ("本部".equals(yetai)) {
				sql = sql + " AND  LOCATIONORG IS NULL ";//
				// sql = sql + " AND LOCATIONORG = '" + orgid + "' AND
				// LOCATIONSITE IS NULL ";
			} else {
				sql = sql + " AND LOCATIONSITE = '" + siteid + "' AND （DEPARTMENT IS NULL OR POST IS NULL） ";
			}
		}

		resultSet.setWhere(sql);
		resultSet.reset();
		return resultSet;
	}
}