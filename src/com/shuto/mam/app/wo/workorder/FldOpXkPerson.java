package com.shuto.mam.app.wo.workorder;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author xiamy@shuto.cn
 * @create 2018-01-15 10:52
 * @Copyright 2018 Shuto版权所有
 * @desc 工作票运行许可人
 **/

public class FldOpXkPerson extends MAXTableDomain {
	public FldOpXkPerson(MboValue mbv) {
		super(mbv);
		setRelationship("WOTICKLINE", "1=1");
		String[] target = { getMboValue().getName() };
		String[] source = { "PERSON.DISPLAYNAME" };
		setLookupKeyMapInOrder(target, source);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		String app = getMboValue().getMbo().getThisMboSet().getApp();
		MboRemote mainMbo = getMboValue().getMbo();
		String siteid = mainMbo.getString("SITEID");
		String s_ordertype = mainMbo.getString("S_ORDERTYPE");
		String wheresql = "type='许可人' and  description='" + s_ordertype + "' and  siteid='" + siteid
				+ "' and s_departmentsid in (select depnum from department where type = '生产部门')";
		setListCriteria(wheresql);
		return super.getList();
	}
}
