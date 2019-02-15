package com.shuto.mam.webclient.beans.tzgl;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class GqjAppBean extends AppBean {

	@Override
	public int SAVE() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		return super.SAVE();
	}

	@Override
	protected void setCurrentRecordData(MboRemote mbo) throws MXException,
			RemoteException {
		String ysr = mbo.getString("YSR");
		if (!ysr.isEmpty()) {
			mbo.setFlag(MboConstants.READONLY, true);
		} else {
			mbo.setFlag(MboConstants.READONLY, false);
		}
		super.setCurrentRecordData(mbo);
	}

	// 一键借出
	public void YJJC() throws RemoteException, MXException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String userName = mainMbo.getUserInfo().getDisplayName();
		mainMbo.setValue("JCR", userName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mainMbo.setValue("JCRQ", df.format(new Date()));
		super.SAVE();
	}

	// 一键归还
	public void YJGH() throws RemoteException, MXException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String userName = mainMbo.getUserInfo().getDisplayName();
		mainMbo.setValue("YSR", userName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mainMbo.setValue("GHRQ", df.format(new Date()));
		super.SAVE();
	}
}
