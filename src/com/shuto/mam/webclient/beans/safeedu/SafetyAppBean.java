package com.shuto.mam.webclient.beans.safeedu;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 * 
    *  文件名： com.shuto.mam.webclient.beans.safeedu.SafetyAppBean.java
    *  说明：TODO
    *  创建日期： 2017年8月21日
    *  修改历史 :   
    *     1. [2017年8月21日下午2:20:03] 创建文件 by lull lull@shuto.cn
 */
public class SafetyAppBean extends CAppBean {
	public int INSERT() throws MXException, RemoteException {
		int i = super.INSERT();
		MboRemote localMboRemote = this.app.getAppBean().getMbo();
		localMboRemote.setValue("TYPE", "培训", 2L);
		return i;
	}
}