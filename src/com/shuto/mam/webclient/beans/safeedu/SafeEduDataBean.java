package com.shuto.mam.webclient.beans.safeedu;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * 
    *  文件名： com.shuto.mam.webclient.beans.safeedu.SafeEduDataBean.java
    *  说明：TODO
    *  创建日期： 2017年8月21日
    *  修改历史 :   
    *     1. [2017年8月21日下午2:22:46] 创建文件 by lull lull@shuto.cn
 */
public class SafeEduDataBean extends DataBean {
	private int count = 0;

	public int addrow() throws MXException {
		String str = "";
		try {
			MboRemote localMboRemote1 = this.app.getAppBean().getMbo();
			str = localMboRemote1.getString("NAME");
		} catch (RemoteException localRemoteException1) {
			localRemoteException1.printStackTrace();
		}
		if (!"".equals(str))
			try {
				this.count = getMboSet().count();
				super.addrow();
				MboRemote localMboRemote2 = getMbo();
				localMboRemote2.setValue("personid", str);
				this.count += 1;
				localMboRemote2.setValue("num", String.valueOf(this.count), 11L);
			} catch (RemoteException localRemoteException2) {
				localRemoteException2.printStackTrace();
			}
		else {
			throw new MXApplicationException("isname", "namenull");
		}

		return 1;
	}

	public int toggledeleterow() throws MXException {
		try {
			int i = getMboSet().count();
			if (i > 0)
				i -= 1;
		} catch (RemoteException localRemoteException) {
			localRemoteException.printStackTrace();
		}
		return super.toggledeleterow();
	}
}