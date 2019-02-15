package com.shuto.mam.app.om.mfxdfx.mbo;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
*
* com.shuto.mam.app.om.mfxdfx.mbo.MfxdfxRemote.java
* 
* app:MFXDFX(煤粉细度分析)
* 
* Email:xiamy@shuto.cn 2017年8月31日 上午9:41:33
*
*/
public class MfxdfxMboSet extends MboSet implements MfxdfxSetRemote {
	public MfxdfxMboSet(MboServerInterface ms) throws RemoteException {
		super(ms);
	}

	protected Mbo getMboInstance(MboSet mboset) throws MXException, RemoteException {
		return new MfxdfxMbo(mboset);
	}
}