package com.shuto.mam.app.tzgl;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldGjmc extends MAXTableDomain {

	public FldGjmc(MboValue mbv) {
		super(mbv);
		setRelationship("YXCFG", "");
		String[] target = { "GJMCSL" };
		String[] source = { "DESCRIPTION" };
		setLookupKeyMapInOrder(target, source);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("type='工器具'");
		return super.getList();
	}
}
