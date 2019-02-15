package com.shuto.mam.webclient.beans.sczh;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;
import java.util.Vector;

/**
 * @Auther: NieMingKun
 * @Date: 2018/9/17 11:01
 * @Description: 多选人员信息
 */

public class personsDateBean extends DataBean {

	@Override
	protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {

		super.getMboSetRemote();
		MboRemote Mbo = this.app.getAppBean().getMbo();
		MboSetRemote msr = Mbo.getMboSet("PERSON");
		return msr;
	}

	@Override
	public synchronized int execute() throws MXException, RemoteException {

		MboRemote mbo = app.getAppBean().getMbo();
		String disPlayName = mbo.getString("JOINPEOPLE");
		Vector selecteLines = getMboSet().getSelection();
		for (int i = 0; i < selecteLines.size(); i++) {
			MboRemote persongroupteam = (MboRemote) selecteLines.elementAt(i);
			disPlayName = disPlayName.concat(persongroupteam.getString("DISPLAYNAME") + "、");
		}
		mbo.setValue("JOINPEOPLE", disPlayName.substring(0, disPlayName.length() - 1), 11L);

		app.getAppBean().reloadTable();
		app.getAppBean().refreshTable();
		return 1;
	}

}
