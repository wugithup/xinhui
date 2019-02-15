package com.shuto.mam.webclient.beans.om.pm;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.util.MXException;

/**
 * com.shuto.mam.webclient.beans.om.pm.OppmWoAppBean华东大区 阜阳项目 安全定期工作页面类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月6日 下午9:13:56
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class OppmWoAppBean extends CAppBean {

	public int INSERT() throws MXException, RemoteException {
		super.INSERT();
		return 1;
	}

	public int SAVE() throws MXException, RemoteException {
		return super.SAVE();
	}

	public void EXE() throws RemoteException, MXException {
		String user = getMbo().getUserInfo().getPersonId();
		getMbo().setValue("ZXPERSON", user, 11L);
		getMbo().setValue("STATUS", "已关闭", 11L);
		this.SAVE();
	}
}