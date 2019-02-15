package com.shuto.mam.webclient.beans.tzgl;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class KeyAppBean extends AppBean {

	// 一键借出
	public void KEYJC() throws RemoteException, MXException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String userName = mainMbo.getUserInfo().getDisplayName();
		mainMbo.setValue("JCPERSON", userName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mainMbo.setValue("DATE1", df.format(new Date()));
		super.SAVE();
	}

	// 一键归还
	public void KEYGH() throws RemoteException, MXException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String userName = mainMbo.getUserInfo().getDisplayName();
		mainMbo.setValue("JSPERSON", userName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mainMbo.setValue("DATE2", df.format(new Date()));
		super.SAVE();
	}

	@Override
	protected void setCurrentRecordData(MboRemote mbo) throws MXException,
			RemoteException {
		String ysr = mbo.getString("JSPERSON");
		if (!ysr.isEmpty()) {
			mbo.setFlag(MboConstants.READONLY, true);
		} else {
			mbo.setFlag(MboConstants.READONLY, false);
		}
		super.setCurrentRecordData(mbo);
	}
}
