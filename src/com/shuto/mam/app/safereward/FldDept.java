package com.shuto.mam.app.safereward;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldDept extends MAXTableDomain {
	public FldDept(MboValue paramMboValue) {
		super(paramMboValue);
		String str = getMboValue().getName();
		setRelationship("DEPARTMENT", "1=1");
		String[] arrayOfString1 = { "depnum" };
		String[] arrayOfString2 = { str };
		setLookupKeyMapInOrder(arrayOfString2, arrayOfString1);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo localMbo = getMboValue().getMbo();
		String str1 = localMbo.getString("siteid");
		if ("".equals(str1)) {
			str1 = localMbo.getInsertSite();
		}
		String str2 = "siteid = '" + str1 + "' and status ='活动' ";
		setListCriteria(str2);
		return super.getList();
	}
}