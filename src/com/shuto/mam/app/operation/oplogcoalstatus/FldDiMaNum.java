package com.shuto.mam.app.operation.oplogcoalstatus;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class FldDiMaNum extends MboValueAdapter {
	public FldDiMaNum() {
	}

	public FldDiMaNum(MboValue mbv) {
		super(mbv);
	}

	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mbo = getMboValue().getMbo();

		Double startdima = Double.valueOf(getMboValue("startdima").getDouble());

		Double enddima = Double.valueOf(getMboValue("enddima").getDouble());
		if (enddima.doubleValue() > startdima.doubleValue()) {
			throw new MXApplicationException("oplog", "oplogsmbj");
		}

		Double rlmnum = Double.valueOf(Math.abs(enddima.doubleValue() - startdima.doubleValue()));

		mbo.setValue("rlmnum", rlmnum.doubleValue(), 11L);
	}
}