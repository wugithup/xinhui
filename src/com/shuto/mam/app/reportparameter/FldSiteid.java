package com.shuto.mam.app.reportparameter;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldSiteid extends MAXTableDomain {

	public FldSiteid(MboValue mbv) {
		super(mbv);

		setRelationship("site", "");
		String[] target = { this.getMboValue().getName() };
		String[] source = { "siteid" };
		setLookupKeyMapInOrder(target, source);

	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mbo = getMboValue().getMbo();
		String orgid = mbo.getString("orgid");
		setListCriteria("orgid = '" + orgid+ "' and ACTIVE = 1 and yetai<>'风电'");
		return super.getList();
	}

}
