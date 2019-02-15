package com.shuto.mam.app.fhlzlrb;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.server.MXServer;
import psdi.util.MXException;

/**
 *
 * com.shuto.mam.app.fhlzlrb.FldFhlzlrbValue.java
 * 
 * app:煤粉细度分析
 * 
 * Email:xiamy@shuto.cn 2017年8月31日 上午10:44:50
 *
 */
public class FldFhlzlrbValue extends MboValueAdapter {
	public FldFhlzlrbValue(MboValue mbv) {
		super(mbv);
	}

	public void action() throws MXException, RemoteException {
		super.action();
		Mbo mbo = getMboValue().getMbo();
		mbo.setValue("entry", mbo.getUserInfo().getPersonId(), 2L);
		mbo.setValue("entrytime", MXServer.getMXServer().getDate(), 2L);
	}
}
