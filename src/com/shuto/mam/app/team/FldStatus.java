package com.shuto.mam.app.team;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @ClassName: FldStatus
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年6月19日 下午10:45:26
 *
 */

public class FldStatus extends MboValueAdapter {
	// com.shuto.mam.app.team.FldStatus
	public FldStatus(MboValue mbv) throws RemoteException, MXException {
		super(mbv);
	}

	public void action() throws MXException, RemoteException {
		super.action();

		Date date = new Date();
		MboRemote thismbo = getMboValue().getMbo();

		thismbo.setValue("statusdate", date);
	}
}