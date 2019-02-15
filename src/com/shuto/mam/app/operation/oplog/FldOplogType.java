package com.shuto.mam.app.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @Title: FldOplogType.java
 * @Package com.shuto.mam.app.operation.oplog
 * @Description: TODO(日志类别)
 * @author wuqi
 * @date 2017-5-10 上午10:22:47
 * @version V1.0
 */

public class FldOplogType extends MAXTableDomain {
	public FldOplogType(MboValue paramMboValue) {
		super(paramMboValue);

		String str = getMboValue().getName();

		setRelationship("oplogcfg", "1=1");

		String[] arrayOfString1 = { str };

		String[] arrayOfString2 = { "oplogtype" };

		setLookupKeyMapInOrder(arrayOfString1, arrayOfString2);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		String siteid = getMboValue().getMbo().getString("siteid");
		if ("".equals(siteid)) {
			siteid = getMboValue().getMbo().getInsertSite();
		}
		setListCriteria("siteid='" + siteid + "'");
		return super.getList();
	}
}