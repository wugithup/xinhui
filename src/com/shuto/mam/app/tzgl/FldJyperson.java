package com.shuto.mam.app.tzgl;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldJyperson extends MAXTableDomain {

	public FldJyperson(MboValue mbv) {
		super(mbv);
		setRelationship("PERSON", "1=1");
		String[] strTo = { "JYPERSON", "JYRDH" };
		String[] strFrom = { "DISPLAYNAME", "MOBILEPHONE" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		String jydw = getMboValue().getMbo().getString("JYDW");
		if ("粤电新会".equals(jydw)) {
			setListCriteria("department in(select depnum from department where type='生产部门') and personid not in('MAXADMIN','JL001')");
		} else if ("广东火电".equals(jydw)) {
			setListCriteria("personid like 'GH%'");
		} else {
			setListCriteria("department not in(select depnum from department where type='生产部门') and personid not like('GH%')");
		}

		return super.getList();
	}

}
