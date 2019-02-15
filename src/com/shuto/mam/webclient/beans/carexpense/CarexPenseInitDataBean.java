package com.shuto.mam.webclient.beans.carexpense;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class CarexPenseInitDataBean extends DataBean {
	public int toggledeleterow() throws MXException {
		super.toggledeleterow();
		try {
			MboRemote mainMbo = this.app.getAppBean().getMbo();
			MboSetRemote mboSet = mainMbo.getMboSet("CAREXPENSELINE2");
			int count = mboSet.count();
			float price = 0.0F;
			for (int i = 0; i < count; i++) {
				MboRemote mbo = mboSet.getMbo(i);
				price += mbo.getFloat("WXMONEY");
			}
			mainMbo.setValue("SQMONEY", price);

			

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return 1;
	}
}
