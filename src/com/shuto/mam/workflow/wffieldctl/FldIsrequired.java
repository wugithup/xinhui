package com.shuto.mam.workflow.wffieldctl;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author lzq
 * @date 创建时间：2017-4-7 上午11:53:57
 * @version 1.0
 * @parameter
 * @since 华南项目相关
 * @return
 */
public class FldIsrequired extends MboValueAdapter {
	public FldIsrequired(MboValue arg0) {
		super(arg0);
	}

	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mboremote = getMboValue().getMbo();
		if (getMboValue().getBoolean())
			mboremote.setValue("isreadonly", false);
	}
}