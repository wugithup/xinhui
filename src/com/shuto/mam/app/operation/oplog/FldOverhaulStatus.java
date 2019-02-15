package com.shuto.mam.app.operation.oplog;

/**  
com.shuto.mam.app.operation.oplog.FldOverhaulStatus 河南
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月22日 上午10:53:15
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldOverhaulStatus extends MboValueAdapter {
	public FldOverhaulStatus() {
	}

	public FldOverhaulStatus(MboValue mbv) {
		super(mbv);
	}

	public void action() throws MXException, RemoteException {
		super.action();

		MboRemote mbo = getMboValue().getMbo();

		if (getMboValue("OVERHAUL").getBoolean()) {
			mbo.setValue("RUN", "0");
			mbo.setValue("BACKUP", "0");
		}
	}
}
