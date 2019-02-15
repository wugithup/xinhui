package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldC_Ghsj extends MboValueAdapter {

	public FldC_Ghsj(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.action();
		MboRemote mainMbo = getMboValue().getMbo();
		String xksj = mainMbo.getString("S_ZZPZDATE");
		if (xksj.isEmpty()) {
			mainMbo.setValue("STATUS", "已签发", 11L);
		}
	}

}
