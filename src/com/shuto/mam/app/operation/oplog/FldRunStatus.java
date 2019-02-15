package com.shuto.mam.app.operation.oplog;

/**  
com.shuto.mam.app.operation.oplog.FldRunStatus 河南
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月22日 上午10:54:14
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldRunStatus extends MboValueAdapter {
	public FldRunStatus() {
	}

	public FldRunStatus(MboValue mbv) {
		super(mbv);
	}

	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mbo = getMboValue().getMbo();

		if (getMboValue("RUN").getBoolean()) {
			mbo.setValue("OVERHAUL", "0");
			mbo.setValue("BACKUP", "0");
		}
	}
}