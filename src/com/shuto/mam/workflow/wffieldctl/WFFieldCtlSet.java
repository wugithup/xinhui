package com.shuto.mam.workflow.wffieldctl;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * @author lzq
 * @date 创建时间：2017-4-7 上午11:53:57
 * @version 1.0
 * @parameter
 * @since 华南项目相关
 * @return
 */
public class WFFieldCtlSet extends MboSet implements WFFieldCtlSetRemote {
	public WFFieldCtlSet(MboServerInterface arg0) throws RemoteException {
		super(arg0);
	}

	@Override
	protected Mbo getMboInstance(MboSet arg0) throws MXException, RemoteException {
		return new WFFieldCtl(arg0);
	}
}