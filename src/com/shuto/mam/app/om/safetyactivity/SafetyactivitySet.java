package com.shuto.mam.app.om.safetyactivity;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class SafetyactivitySet extends MboSet implements SafetyactivitySetRemote{

	public SafetyactivitySet(MboServerInterface ms) throws RemoteException {
		super(ms);
	}
	
	protected Mbo getMboInstance(MboSet mboset) throws MXException, RemoteException
	{
	    return new Safetyactivity(mboset);
	}

}