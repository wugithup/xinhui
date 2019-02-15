
package com.shuto.mam.app.stpi.st_pi_task_item;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class St_pi_task_itemSet extends MboSet implements St_pi_task_itemSetRemote {
	private static final long serialVersionUID = 1L;

	public St_pi_task_itemSet(MboServerInterface mboserverinterface)
			throws MXException, RemoteException {
		super(mboserverinterface);
	}

	protected Mbo getMboInstance(MboSet mboset) throws MXException,
			RemoteException {
		return new St_pi_task_item(mboset);
	}
}
