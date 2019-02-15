package com.shuto.mam.webclient.beans.safeedu;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 *  
    *  文件名： com.shuto.mam.webclient.beans.safeedu.SelPersonDataBean.java
    *  说明：TODO
    *  创建日期： 2017年8月21日
    *  修改历史 :   
    *     1. [2017年8月21日下午2:24:01] 创建文件 by lull lull@shuto.cn
 */
public class SelPersonDataBean extends DataBean {
	protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		MboRemote localMboRemote = this.app.getAppBean().getMbo();
		String str1 = localMboRemote.getString("siteid");
		MboSetRemote localMboSetRemote1 = localMboRemote.getMboSet("ST_TRAINMID");
		MboSetRemote localMboSetRemote2 = localMboRemote.getMboSet("$ST_SAFEEDURECORD", "ST_SAFEEDURECORD");

		if (!localMboSetRemote1.isEmpty()) {
			String str2 = "";
			String str3 = "";
			for (int i = 0; i < localMboSetRemote1.count(); i++) {
				str3 = localMboSetRemote1.getMbo(i).getString("personid");
				if (i == 0)
					str2 = "'" + str3 + "'";
				else {
					str2 = str2 + "," + "'" + str3 + "'";
				}
			}
			localMboSetRemote2.setWhere("name not in( " + str2 + " ) and siteid = '" + str1 + "'");
		} else {
			localMboSetRemote2.setWhere("siteid = '" + str1 + "'");
		}

		return localMboSetRemote2;
	}

	public synchronized int execute() throws MXException, RemoteException {
		MboRemote localMboRemote1 = this.app.getAppBean().getMbo();
		String str1 = localMboRemote1.getString("SNNUM");
		String str2 = localMboRemote1.getString("TRAINTIME");
		String str3 = localMboRemote1.getString("TRAINDECS");
		DataBean localDataBean = this.app.getDataBean("selperson");
		MboSetRemote localMboSetRemote1 = localDataBean.getMboSet();
		Vector localVector = localMboSetRemote1.getSelection();
		MboRemote localMboRemote2 = null;
		MboSetRemote localMboSetRemote2 = localMboRemote1.getMboSet("ST_TRAINMID");

		if (localVector.size() > 0) {
			MboRemote localMboRemote3 = null;
			for (int i = 0; i < localVector.size(); i++) {
				localMboRemote2 = (MboRemote) localVector.get(i);
				localMboRemote3 = localMboSetRemote2.addAtEnd();
				localMboRemote3.setValue("trainnum", str1, 11L);
				localMboRemote3.setValue("TRAINTIME", str2, 11L);
				localMboRemote3.setValue("TRAINDECS", str3, 11L);
				localMboRemote3.setValue("personid", localMboRemote2.getString("name"), 11L);
			}
		}
		this.app.getAppBean().save();
		this.app.getAppBean().reloadTable();
		this.app.getAppBean().refreshTable();
		return super.execute();
	}
}