package com.shuto.mam.app.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldZhibie extends MAXTableDomain {
	public FldZhibie(MboValue mbv) {
		super(mbv);

		String thisAtt = getMboValue().getName();

		setRelationship("shift", "1=1");

		String[] strTo = { thisAtt };

		String[] strFrom = { "shiftnum" };

		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		String siteid = getMboValue().getMbo().getString("siteid");
		if ("".equals(siteid)) {
			siteid = getMboValue().getMbo().getInsertSite();
		}
		setListCriteria("shifttype = '班值' and siteid='" + siteid + "'");
		return super.getList();
	}
}