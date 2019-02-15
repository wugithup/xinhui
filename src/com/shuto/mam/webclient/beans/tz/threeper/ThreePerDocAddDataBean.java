package com.shuto.mam.webclient.beans.tz.threeper;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class ThreePerDocAddDataBean extends DataBean {
	//com.shuto.mam.webclient.beans.tz.threeper.ThreePerDocAddDataBean
	public int instantdelete() throws MXException {
		MboRemote localMboRemote = null;
		String str = "";
		try {
			localMboRemote = this.app.getAppBean().getMbo();
			str = localMboRemote.getString("STATUS");
		} catch (RemoteException localRemoteException) {
			localRemoteException.printStackTrace();
		}
		if ("编制".equals(str)) {
			return super.instantdelete();
		}
		throw new MXApplicationException("deletefileerror", "deletefileerror");
	}

	public void SHOWDOC() throws RemoteException, MXException {
		MboRemote localMboRemote = this.app.getAppBean().getMbo();
		String str = localMboRemote.getString("status");
		if ("编制".equals(str))
			this.clientSession.loadDialog("addnewattachmentfile2");
		else
			throw new MXApplicationException("adddocerror", "adddocerror");
	}
}