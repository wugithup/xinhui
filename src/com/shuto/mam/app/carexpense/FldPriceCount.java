/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.carexpense;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

import java.rmi.RemoteException;

public class FldPriceCount extends MboValueAdapter {
	public FldPriceCount(MboValue paramMboValue) {
		super(paramMboValue);
	}

    @Override
    public void action() throws MXException, RemoteException {
		super.action();
		Mbo localMbo = getMboValue().getMbo();
		MboRemote localMboRemote1 = localMbo.getOwner();
		MboSetRemote localMboSetRemote = localMboRemote1.getMboSet("CAREXPENSELINE2");
		float num = 0.0F;
		int number = localMboSetRemote.count();
		for (int i = 0; i < number; i++) {
			MboRemote localMboRemote2 = localMboSetRemote.getMbo(number);
			float f4 = localMboRemote2.getFloat("WXMONEY");
			num += f4;
		}
		localMboRemote1.setValue("SQMONEY", num);
		localMboSetRemote.close();
	}
}
