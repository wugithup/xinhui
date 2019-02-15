package com.shuto.mam.app.stpi.st_pi_post;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class St_pi_post extends Mbo implements St_pi_postRemote {
	private static final long serialVersionUID = 1L;

	public St_pi_post(MboSet mboset) throws MXException, RemoteException {
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
	}

	public void delete(long accessModifier) throws RemoteException, MXException {
		getMboSet("ST_PI_USER").deleteAll(accessModifier);
		super.delete(accessModifier);
	}
}
