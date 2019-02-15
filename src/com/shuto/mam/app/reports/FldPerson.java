package com.shuto.mam.app.reports;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPerson extends MAXTableDomain {

	public FldPerson(MboValue mbv) {
		super(mbv);
		String thisAtt = getMboValue().getName();
		setRelationship("PERSON", "");
		String[] strFrom = { "PERSONID" };
		String[] strTo = { thisAtt };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mbo = this.getMboValue().getMbo();
		String siteid = mbo.getString("SITEID");
		setListCriteria("status = '活动' and locationsite='" + siteid + "' and personid<>'MAXADMIN'");
		return super.getList();
	}
}
