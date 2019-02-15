package com.shuto.mam.webclient.beans.safeedu;
/**  
com.shuto.mam.webclient.beans.safeedu.SafeEduAppBean 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月27日 下午5:36:59
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.util.MXException;

public class SafeEduAppBean extends CAppBean {
	public void FINISH() throws RemoteException, MXException {
		getMbo().setValue("status", "完成编制", 11L);
		super.SAVE();
	}

	public int INSERT() throws MXException, RemoteException {
		int i = super.INSERT();
		getMbo().setValue("status", "新建", 11L);
		refreshTable();
		return i;
	}
}