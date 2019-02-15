package com.shuto.mam.webclient.beans.fmh;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * com.shuto.mam.webclient.beans.fmh.FcpgyszrAppBean 华东大区 阜阳项目 副产品供应商准入页面类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月7日 下午2:29:21
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FcpgyszrAppBean extends CAppBean {

	@Override
	public int INSERT() throws MXException, RemoteException {
		super.INSERT();
		MboSetRemote AppMboSet = this.app.getAppBean().getMboSet();
		MboRemote AppMbo = AppMboSet.getMbo();
		String personid = this.sessionContext.getUserInfo().getPersonId();
		MboSetRemote msr_person = AppMbo.getMboSet("$prperson", "person", "personid ='" + personid + "'");
		String department = msr_person.getMbo(0).getString("DEPARTMENT");
		AppMbo.setValue("DEPNUM", department, 11L);
		super.SAVE();
		return 1;
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		super.SAVE();
		return 1;
	}
}