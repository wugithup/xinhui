package com.shuto.mam.webclient.beans.workorder;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.workorder.SelectKksDataBean 工作票设备编码选择
 * 
 * @author quanhw quanhw@shuoto.cn
 * @date 2018年4月25日
 */

public class SelectKksDataBean extends DataBean {

	@Override
	protected MboSetRemote getMboSetRemote() throws MXException,
			RemoteException {
		// TODO Auto-generated method stub
		super.getMboSetRemote();
		MboRemote Mbo = this.app.getAppBean().getMbo();
		MboSetRemote msr = Mbo.getMboSet("WOT_LOCATION");
		return msr;
	}

	@Override
	public synchronized int execute() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		MboRemote mbo = app.getAppBean().getMbo();
		String kksId = mbo.getString("LOCATION");
		String description = mbo.getString("OPLOG_DELAYCAUSE");
		Vector selecteLines = getMboSet().getSelection();
		for (int i = 0; i < selecteLines.size(); i++) {
			MboRemote persongroupteam = (MboRemote) selecteLines.elementAt(i);
			kksId = kksId.concat(persongroupteam.getString("LOCATION") + "、");
			description = description.concat(persongroupteam
					.getString("DESCRIPTION") + "、");
		}
		mbo.setValue("LOCATION", kksId.substring(0, kksId.length() - 1), 11L);
		mbo.setValue("OPLOG_DELAYCAUSE",
				description.substring(0, description.length() - 1), 11L);
		app.getAppBean().reloadTable();
		app.getAppBean().refreshTable();
		return 1;
	}
}
