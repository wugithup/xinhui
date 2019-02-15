package com.shuto.mam.app.fhlzlrb.fhlzlrbzb.mbo;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 *
 * com.shuto.mam.app.fhlzlrb.fhlzlrbzb.FhlzlrbzbMboSet.java
 * 
 * Email:xiamy@shuto.cn 2017年8月31日 上午10:46:24
 *
 */
public class FhlzlrbzbMboSet extends MboSet implements FhlzlrbzbSetRemote {
	public FhlzlrbzbMboSet(MboServerInterface ms) throws RemoteException {
		super(ms);
	}

	protected Mbo getMboInstance(MboSet mboset) throws MXException, RemoteException {
		return new FhlzlrbzbMbo(mboset);
	}
}