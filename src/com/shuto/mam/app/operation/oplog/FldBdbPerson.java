package com.shuto.mam.app.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldBdbPerson extends MAXTableDomain {

	public FldBdbPerson(MboValue mbv) {
		super(mbv);
		setRelationship("PERSON","");
		String[] strTo = { "BDBPERSON" };
		String[] strFrom = { "DISPLAYNAME" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}
	
	public MboSetRemote getList() throws MXException, RemoteException {
		String oplogtype = getMboValue().getMbo().getString("OPLOGTYPE");
		String siteid = getMboValue().getMbo().getString("SITEID");
		setListCriteria("personid in(select personid from oplogperson where siteid='" + siteid + "' and oplogtype='"
				+ oplogtype + "') and locationsite='" + siteid + "'");
		return super.getList();
	}

}
