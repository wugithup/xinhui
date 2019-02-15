package com.shuto.mam.app.wo.workorder;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author xiamy@shuto.cn
 * @create 2018-01-15 11:17
 * @Copyright 2018 Shuto版权所有
 * @desc 工作票调试签发人
 **/

public class FldTsQfPerson extends MAXTableDomain {
	public FldTsQfPerson(MboValue mbv) {
		super(mbv);
		setRelationship("WOTICKLINE", "1=1");
		String[] target = { getMboValue().getName() };
		String[] source = { "PERSON.DISPLAYNAME" };
		setLookupKeyMapInOrder(target, source);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = getMboValue().getMbo();
		String siteid = mainMbo.getString("SITEID");
		String s_ordertype = mainMbo.getString("S_ORDERTYPE");
		String wheresql = "type='签发人' and  description='" + s_ordertype + "' and  siteid='" + siteid
				+ "' and s_departmentsid in (select depnum from department where type = '外委')";
		setListCriteria(wheresql);
		return super.getList();
	}
}

