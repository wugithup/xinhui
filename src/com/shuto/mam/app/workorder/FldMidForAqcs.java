package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldMidForAqcs extends MAXTableDomain {

	public FldMidForAqcs(MboValue mbv) {
		super(mbv);
		setRelationship("tagoutmethod", "");
		setLookupKeyMapInOrder(new String[] { "TAGOUTMETHOD" },
				new String[] { "TAGOUTMETHOD" });
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria(" optype = '工作票'");
		return super.getList();
	}
}
