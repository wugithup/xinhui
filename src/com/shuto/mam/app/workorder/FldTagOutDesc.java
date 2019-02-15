package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldTagOutDesc extends MAXTableDomain {

	public FldTagOutDesc(MboValue mbv) {
		super(mbv);
		setRelationship("LOCATIONS", "");
		setLookupKeyMapInOrder(
				new String[] { "TAGOUTID", "TAGOUTDESCRIPTION2" },
				new String[] { "location", "description" });
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("ISJDX=1");
		return super.getList();
	}

}
