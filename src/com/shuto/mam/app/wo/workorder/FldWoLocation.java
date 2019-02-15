package com.shuto.mam.app.wo.workorder;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author xiamy@shuto.cn
 * @create 2018-01-15 15:28
 * @Copyright 2018 Shuto版权所有
 * @desc 工作票位置字段类
 **/

public class FldWoLocation extends MAXTableDomain {

	public FldWoLocation(MboValue mbv) {
		super(mbv);
		setRelationship("locations", "");
		String[] target = { getMboValue().getName() };
		String[] source = { "location" };
		setLookupKeyMapInOrder(target, source);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote thismbo = this.getMboValue().getMbo();
		String siteid = thismbo.getString("siteid");
		String wheresql = "siteid = '" + siteid + "' and type ='操作'";
		setListCriteria(wheresql);
		return super.getList();
	}

	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote thismbo = this.getMboValue().getMbo();
		String locdescription = thismbo.getString("LOCATION.DESCRIPTION");
		thismbo.setValue("OPLOG_DELAYCAUSE", locdescription, 11L);

	}
}
