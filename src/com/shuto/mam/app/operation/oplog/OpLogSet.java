package com.shuto.mam.app.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**   
 * @Title: OpLogSet.java 
 * @Package com.shuto.mam.app.operation.oplog 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author wuqi   
 * @date 2017-5-8 下午03:46:58 
 * @version V1.0   
 */
public class OpLogSet extends MboSet implements OpLogSetRemote {
	public OpLogSet(MboServerInterface ms) throws RemoteException {
		super(ms);
	}

	protected Mbo getMboInstance(MboSet arg0) throws MXException, RemoteException {
		return new OpLog(arg0);
	}
}