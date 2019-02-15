package com.shuto.mam.webclient.beans.she;

/**  
 com.shuto.mam.webclient.beans.she.SheAppBean 华南
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月5日 上午11:28:43
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class SheAppBean extends AppBean {
	protected void setCurrentRecordData(MboRemote mbo) throws MXException, RemoteException {
		mbo.setFlag(7L, false);
		String status = mbo.getString("status");
		
		long id = mbo.getUniqueIDValue();
		String tableName = mbo.getName().toUpperCase();
		String personid = mbo.getUserInfo().getPersonId();
		String ENTRY = mbo.getString("ENTRY");

		MboSetRemote mbosetremote = mbo.getMboSet("$instance", "WFINSTANCE",
				"ownertable='" + tableName + "' and ownerid='" + id + "' and active=1");
		if (!mbosetremote.isEmpty()) {
			String sql = "ownerid='" + id + "' and ownertable='" + tableName
					+ "' and assignstatus='活动' and assigncode='" + personid + "'";
			MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode", "WFASSIGNMENT", sql);
			if (!mbosetremote1.isEmpty()) {
				StringBuffer sb = new StringBuffer();
				sb.append(mbosetremote1.getMbo(0).getString("processname") + "_");
				sb.append(mbosetremote1.getMbo(0).getString("nodeid") + "_");

				mbo.setFieldFlag(WfUtil(mbo, sb.toString() + "READONLY"), 7L, true);
				mbo.setFieldFlag(WfUtil(mbo, sb.toString() + "REQUIRED"), 128L, true);
			}
			mbosetremote1.close();
		} else if (personid.equals(ENTRY)) {
			mbo.setFieldFlag(WfUtil(mbo, tableName + "_0_READONLY"), 7L, true);
			mbo.setFieldFlag(WfUtil(mbo, tableName + "_0_REQUIRED"), 128L, true);
		} else {
			mbo.setFlag(7L, true);
		}
		mbosetremote.close();
		
		if (status.equals("已关闭")) {
			mbo.setFlag(7L, true);
		}
		super.setCurrentRecordData(mbo);
	}

	private String[] WfUtil(MboRemote mbo, String value) throws RemoteException, MXException {
		MboSetRemote mboSet = mbo.getMboSet("$ALNDOMAIN", "ALNDOMAIN",
				" DOMAINID='WFNODEFIELDCTL' and value ='" + value + "'");
		if (!mboSet.isEmpty()) {
			String[] rdStr = mboSet.getMbo(0).getString("description").split(",");
			mboSet.close();
			return rdStr;
		}
		mboSet.close();
		return new String[0];
	}

	public int INSERT() throws MXException, RemoteException {
		super.INSERT();

		String orgid = getMbo().getInsertOrganization();
		String siteid = getMbo().getInsertSite();
		setValue("SITEID", siteid, 11L);
		setValue("orgid", orgid, 11L);

		return 1;
	}
}
