package com.shuto.mam.app.stpi.st_pi_taskcfg;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class FldCycle extends MboValueAdapter {

	public FldCycle() {
		super();
	}

	public FldCycle(MboValue mbv) {
		super(mbv);
	}
	@Override
	public void init() throws MXException, RemoteException {
		super.init();
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mainMbo = this.getMboValue().getMbo();
		String type = mainMbo.getString("type");
		String cycle_unit = mainMbo.getString("CYCLE_UNIT");
		if(("天".equals(cycle_unit)&&"点检".equals(type))||"巡检".equals(type)){
			int cycle = this.getMboValue().getInt();
			if (cycle <= 0) {
				throw new MXApplicationException("PI_TCFG", "CYCLEERROR");
			}
		}
	}
}
