package com.shuto.mam.app.pm;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPerson extends MAXTableDomain {
	public FldPerson(MboValue mbv) {
		super(mbv);
		setRelationship("TEAMPERSON", "1=1");
		String[] target = { getMboValue().getName() };
		String[] source = { "personid" };
		setLookupKeyMapInOrder(target, source);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = getMboValue().getMbo();
		String siteid = mainMbo.getString("SITEID");
		String TEAMNAME = mainMbo.getString("TEAMNAME");
		setListCriteria(" TEAMNUM = '" + TEAMNAME + "'  and SITEID = '" + siteid + "'");
		return super.getList();
	}
}