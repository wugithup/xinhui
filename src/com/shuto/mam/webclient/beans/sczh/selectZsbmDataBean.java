package com.shuto.mam.webclient.beans.sczh;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @Auther: Quan Hongwei
 * @Date: 2018/9/19
 * @Description: 主送部门多选
 */

public class selectZsbmDataBean extends DataBean {
	
	@Override
	protected MboSetRemote getMboSetRemote() throws MXException,
			RemoteException {
		super.getMboSetRemote();
		MboRemote Mbo = this.app.getAppBean().getMbo();
		MboSetRemote msr = Mbo.getMboSet("DEPARTMENT");
		return msr;
	}
	
	@Override
	public synchronized int execute() throws MXException, RemoteException {
		MboRemote mbo = app.getAppBean().getMbo();
		String zsDep = mbo.getString("ZS");
		Vector selecteLines = getMboSet().getSelection();
		for (int i = 0; i < selecteLines.size(); i++) {
			MboRemote persongroupteam = (MboRemote) selecteLines.elementAt(i);
			zsDep = zsDep.concat(persongroupteam
					.getString("DESCRIPTION") + "、");
		}
		mbo.setValue("ZS",
				zsDep.substring(0, zsDep.length() - 1), 11L);

		app.getAppBean().reloadTable();
		app.getAppBean().refreshTable();
		return 1;
	}

}
