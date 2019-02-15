package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldXksj extends MboValueAdapter {

	public FldXksj(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.action();
		MboRemote mainMbo = getMboValue().getMbo();
		String jssj = mainMbo.getString("C_REGAINSTART");
		if (jssj.isEmpty()) {
			mainMbo.setValue("STATUS", "已许可", 11L);
		}
	}

}
