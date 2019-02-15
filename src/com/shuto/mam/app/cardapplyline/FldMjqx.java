package com.shuto.mam.app.cardapplyline;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldMjqx extends MAXTableDomain {

	public FldMjqx(MboValue mbv) {
		super(mbv);
		setRelationship("ASSET", "1=1");
		setLookupKeyMapInOrder(new String[] { "MJQX" },
				new String[] { "DESCRIPTION" });
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("parent = '0601' and bm='ZHB'");
		return super.getList();
	}

}
