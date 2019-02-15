package com.shuto.mam.workflow.wffieldctl;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;

/**
 * @author lzq
 * @date 创建时间：2017-4-7 上午11:53:57
 * @version 1.0
 * @parameter
 * @since 华南项目相关
 * @return
 */
public class WFFieldCtl extends Mbo implements WFFieldCtlRemote {
	public WFFieldCtl(MboSet arg0) throws RemoteException {
		super(arg0);
	}
}