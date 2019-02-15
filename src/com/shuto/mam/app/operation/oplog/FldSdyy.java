package com.shuto.mam.app.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldSdyy extends MAXTableDomain {
	public FldSdyy(MboValue paramMboValue) throws MXException {
		super(paramMboValue);
		String str = getMboValue().getName();
		setRelationship("person", "personid=:" + str);
		String[] arrayOfString1 = { str };
		String[] arrayOfString2 = { "personid" };
		setLookupKeyMapInOrder(arrayOfString1, arrayOfString2);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo localMbo = getMboValue().getMbo();
		String str = localMbo.getString("siteid");
		if (localMbo.isZombie()) {
			str = localMbo.getThisMboSet().getProfile().getDefaultSite();
			setListCriteria(" status  = '活动' and S_DEPARTMENTSID = '10010' and locationsite='" + str + "'");
		} else {
			setListCriteria(" status  = '活动' and S_DEPARTMENTSID = '10010' and locationsite='" + str + "'");
		}
		return super.getList();
	}
}