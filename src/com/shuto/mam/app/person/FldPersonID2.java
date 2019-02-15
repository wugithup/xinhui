package com.shuto.mam.app.person;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPersonID2 extends MAXTableDomain {
	public FldPersonID2(MboValue mbv) throws MXException {
		super(mbv);
		String thisAttr = getMboValue().getAttributeName();
		setRelationship("PERSON", "personid=:" + thisAttr);
		setErrorMessage("person", "InvalidPerson");
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainmbo = this.getMboValue().getMbo().getOwner().getOwner();
		String siteid = mainmbo.getString("siteid");
		setListCriteria("status='活动' and locationsite='" + siteid + "'");
		return super.getList();
	}
}
