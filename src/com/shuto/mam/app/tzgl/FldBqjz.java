package com.shuto.mam.app.tzgl;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldBqjz extends MboValueAdapter {

	public FldBqjz(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void action() throws MXException, RemoteException {
		// 上一条记录补氢后氢气压力
		String bcbqhyl = null;
		MboRemote mbo = this.getMboValue().getMbo();
		// 机组编号
		String jzbh = mbo.getString("JZBH");

		MboSetRemote msr = mbo.getMboSet("$TZ_BQJL", "TZ_BQJL",
				"lqlv is not null and jzbh='" + jzbh
						+ "' order by tz_bqjlid desc");
		MboRemote prevMbo = msr.getMbo(0);
		bcbqhyl = prevMbo.getString("bcbqhyl");
		System.out.println("---------------------" + bcbqhyl);
		mbo.setValue("BQHYL", bcbqhyl, 11L);
		super.action();
	}

}
