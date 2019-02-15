package com.shuto.mam.app.tzgl;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldYxcfgType extends MAXTableDomain {

	public FldYxcfgType(MboValue mbv) {
		super(mbv);
		setRelationship("ALNDOMAIN", "1=1");
		String[] target = { "TYPE" };
		String[] source = { "DESCRIPTION" };
		setLookupKeyMapInOrder(target, source);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("DOMAINID = 'YXCFG' ");
		return super.getList();
	}
}
