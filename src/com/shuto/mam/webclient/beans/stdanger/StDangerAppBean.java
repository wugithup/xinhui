package com.shuto.mam.webclient.beans.stdanger;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月6日
 * @since:控股台账
 */

public class StDangerAppBean extends AppBean {
	public int INSERT() throws MXException, RemoteException {
		int i = super.INSERT();
		MboRemote localMboRemote = this.app.getAppBean().getMbo();
		localMboRemote.setValue("status", "新建", 2L);
		refreshTable();
		return i;
	}

	public int SAVE() throws MXException, RemoteException {
		if (!hasAuth()) {
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");
		}

		return super.SAVE();
	}

	public boolean hasAuth() throws MXException, RemoteException {
		System.out.println();
		String app = this.appName;
		long l;
		if ("ST_DANGER".equalsIgnoreCase(app)||"NOSADANGER".equalsIgnoreCase(app)) {
			 l = getMbo().getLong("ST_DANGERID");
		} else {
			 l = getMbo().getLong("TZ_ST_DANGERID");
		}
		String str1 = getMbo().getString("STATUS");
		String str2 = getMbo().getUserInfo().getPersonId();
		if (str2.equalsIgnoreCase("maxadmin"))
			return true;
		if ("新建".equals(str1))
			return true;
		MboSetRemote localMboSetRemote1 = getMbo().getMboSet(
				"$instance",
				"WFINSTANCE",
				"ownertable='ST_DANGER' and ownerid='" + l
						+ "' and active = 1");

		if (!localMboSetRemote1.isEmpty()) {
			String str3 = "ownerid='"
					+ l
					+ "' and ownertable='ST_DANGER' and assignstatus='活动' and assigncode='"
					+ str2 + "'";

			MboSetRemote localMboSetRemote2 = getMbo().getMboSet("$assigncode",
					"WFASSIGNMENT", str3);

			return !localMboSetRemote2.isEmpty();
		}
		return false;
	}

	public int ROUTEWF() throws MXException, RemoteException {
		if (!hasAuth()) {
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");
		}
		SAVE();
		return super.ROUTEWF();
	}
}