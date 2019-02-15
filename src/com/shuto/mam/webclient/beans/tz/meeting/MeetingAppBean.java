package com.shuto.mam.webclient.beans.tz.meeting;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class MeetingAppBean extends AppBean {
	public int INSERT() throws MXException, RemoteException {
		super.INSERT();
		String appName = getMbo().getThisMboSet().getApp();
		getMbo().setValue("APP", appName, 11L);
		return 1;
	}

	public int SAVE() throws MXException, RemoteException {
		return super.SAVE();
	}

	public void EXE() throws RemoteException, MXException {
		super.SAVE();
		getMbo().setValue("STATUS", "关闭", 11L);
		super.SAVE();
	}

	public void CHANGE() throws RemoteException, MXException {
		super.SAVE();
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		mainMbo.setValue("STATUS", "已结束");
		super.SAVE();
	}

	@Override
	protected void setCurrentRecordData(MboRemote mbo) throws MXException,
			RemoteException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String status = mainMbo.getString("STATUS");
		if ("已结束".equals(status)) {
			mbo.setFlag(MboConstants.READONLY, true);
		}
		super.setCurrentRecordData(mbo);
	}

}