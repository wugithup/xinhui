package com.shuto.mam.app.sr;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class SRMboSet extends psdi.app.ticket.SRSet implements SRMboSetRemote {

	public SRMboSet(MboServerInterface ms) throws MXException, RemoteException {
		super(ms);
		// TODO Auto-generated constructor stub
	}

	protected Mbo getMboInstance(MboSet ms) throws MXException, RemoteException {
		return new SRMbo(ms);
	}

}