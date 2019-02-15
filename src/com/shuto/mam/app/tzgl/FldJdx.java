package com.shuto.mam.app.tzgl;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldJdx extends MAXTableDomain{

	public FldJdx(MboValue mbv) {
		super(mbv);
		setRelationship("LOCATIONS", "1=1");
		String[] strTo = { "JDZDBH","ZSDD" };
		String[] strFrom = { "LOCATION","DESCRIPTION" };
		setLookupKeyMapInOrder(strTo, strFrom);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		setListCriteria("ISJDX=1");
		return super.getList();
	}
}
