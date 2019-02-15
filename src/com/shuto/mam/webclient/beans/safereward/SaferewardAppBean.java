package com.shuto.mam.webclient.beans.safereward;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月18日
 * @since: 菏泽 安全奖励 appbean
 */
public class SaferewardAppBean extends CAppBean {
	public int INSERT() throws MXException, RemoteException {
		int i = super.INSERT();
		MboRemote localMboRemote = this.app.getAppBean().getMbo();
		localMboRemote.setValue("status", "编制", 11L);
		localMboRemote.setValue("APPTYPE", "奖励", 11L);
		return i;
	}

}