package com.shuto.mam.app.tzgl;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldKeyName extends MAXTableDomain {

	public FldKeyName(MboValue mbv) {
		super(mbv);
		setRelationship("YXCFG", "");
		String[] target = { "PDS" };
		String[] source = { "DESCRIPTION" };
		setLookupKeyMapInOrder(target, source);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("type='钥匙'");
		return super.getList();
	}

}
