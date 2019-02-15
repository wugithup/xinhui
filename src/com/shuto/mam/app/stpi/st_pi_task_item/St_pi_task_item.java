package com.shuto.mam.app.stpi.st_pi_task_item;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class St_pi_task_item extends Mbo implements St_pi_task_itemRemote {
	private static final long serialVersionUID = 1L;

	public St_pi_task_item(MboSet mboset) throws MXException, RemoteException {
		super(mboset);
	}

	public void add() throws MXException, RemoteException {
		super.add();
	}

	protected void save() throws MXException, RemoteException {
		super.save();
	}

	public void init() throws MXException {
		super.init();
		try {
			if(!isNew()){
				String appName = this.getThisMboSet().getApp();
				if("PI_CBDATAX".equals(appName)){
					boolean isnormal = getBoolean("ISNORMAL");
					 if (isnormal) {
					      setFieldFlag("NOTE", 128L, true);
					    }
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void delete(long accessModifier) throws RemoteException, MXException {
		super.delete(accessModifier);
	}
}
