package com.shuto.mam.webclient.beans.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import com.shuto.mam.webclient.beans.base.CAppBean;

/**
 * 危险新建 com.shuto.mam.webclient.beans.workorder.HazardAppBean 华东大区
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年8月28日 下午8:53:08
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class HazardAppBean extends AppBean {

	public int INSERT() throws MXException, RemoteException {
		int i = super.INSERT();
		MboRemote mbo = this.app.getAppBean().getMbo();
		mbo.setValue("PRECAUTIONENABLED", 1, 11L);
		mbo.setValue("HA_L_INO", 0, 11L);
		mbo.setValue("HA_E_INO", 0, 11L);
		mbo.setValue("HA_C_INO", 0, 11L);
		mbo.setValue("HAZ01", mbo.getUserInfo().getInsertSite(), 11L);

		this.app.getAppBean().refreshTable();
		this.app.getAppBean().reloadTable();
		return i;
	}

	public int SAVE() throws MXException, RemoteException {
		return super.SAVE();
	}
}
