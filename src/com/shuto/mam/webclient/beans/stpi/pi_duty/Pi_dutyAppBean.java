package com.shuto.mam.webclient.beans.stpi.pi_duty;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class Pi_dutyAppBean extends AppBean {

	public Pi_dutyAppBean() {
		super();
	}

	@Override
	public int INSERT() throws MXException, RemoteException {
		int insert = super.INSERT();
		return insert;
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		/*if (!(hasAuth())) {
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");
		}*/
		MboRemote mainMbo = app.getAppBean().getMbo();
		//插入班次序号
		MboSetRemote dutyconfigSet = mainMbo.getMboSet("ST_PI_DUTYCONFIG");
		dutyconfigSet.setOrderBy("STARTTIME asc");
		for(int i=0;i<dutyconfigSet.count();i++){
			int seq=i+1;
			MboRemote dutyconfigMbo = dutyconfigSet.getMbo(i);
			dutyconfigMbo.setValue("seq", seq,11L);
		}
		
		return super.SAVE();
	}
	public boolean hasAuth() throws MXException, RemoteException {
		String createby = getMbo().getString("CREATEBY");
		String s1 = getMbo().getUserInfo().getPersonId();
		if (s1.equalsIgnoreCase("maxadmin"))
			return true;
		if (s1.equalsIgnoreCase(createby))
			return true;
		return false;
	}
}
