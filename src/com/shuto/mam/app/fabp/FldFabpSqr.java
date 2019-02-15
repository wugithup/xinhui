package com.shuto.mam.app.fabp;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.fabp.FldFabpSqr 阜阳台账
 * 
 * @author zhaowei zhaowei@shuoto.cn
 * @date 2017年8月16日 下午3:57:59
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldFabpSqr extends MAXTableDomain {

	public FldFabpSqr(MboValue mbv) {
		super(mbv);
		String thisAtt = getMboValue().getName();
		setRelationship("person", "personid=:" + thisAtt);
		String[] strTo = { thisAtt };
		String[] strFrom = { "personid" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo mbo = getMboValue().getMbo();
		String siteid = mbo.getString("siteid");
		setListCriteria("status  = '活动' and locationsite='" + siteid + "'");
		return super.getList();
	}

	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		Mbo mbo = getMboValue().getMbo();
		String personId = getMboValue().getString();
		String department = mbo
				.getMboSet("$person", "person", "personid='" + personId + "'")
				.getMbo(0).getString("department");
		getMboValue().getMbo().setValue("depnum", department, 2L);
	}
}
