package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldJssj extends MboValueAdapter {

	public FldJssj(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.action();
		MboRemote mainMbo = getMboValue().getMbo();
		String zjsj = mainMbo.getString("OPLOG_ZXDATE");
		if (zjsj.isEmpty()) {
			mainMbo.setValue("STATUS", "已结束", 11L);
		}
	}

}
