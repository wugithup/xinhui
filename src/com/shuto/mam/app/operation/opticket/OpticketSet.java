package com.shuto.mam.app.operation.opticket;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * 
 * @Title: OpticketSet.java
 * @Description: TODO
 * @author: lull lull@shuto.cn
 * @date: 2017年5月5日 下午3:52:19
 * @version: V1.0.0
 */
public class OpticketSet extends MboSet implements OpticketSetRemote {
	public OpticketSet(MboServerInterface ms) throws RemoteException {
		super(ms);
	}

	@Override
	protected Mbo getMboInstance(MboSet arg0) throws MXException, RemoteException {
		return new Opticket(arg0);
	}
}
