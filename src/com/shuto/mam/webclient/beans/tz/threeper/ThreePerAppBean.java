package com.shuto.mam.webclient.beans.tz.threeper;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since: 菏泽 - 安全管理 - 三种人审批 appbean  
 */
public class ThreePerAppBean extends CAppBean {
	//com.shuto.mam.webclient.beans.tz.threeper.ThreePerAppBean
	public int INSERT() throws MXException, RemoteException {
		int i = super.INSERT();
		MboRemote localMboRemote = this.app.getAppBean().getMbo();
		localMboRemote.setValue("status", "编制", 11L);
		refreshTable();
		return i;
	}

}