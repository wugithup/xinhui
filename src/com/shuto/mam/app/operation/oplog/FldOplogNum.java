package com.shuto.mam.app.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.operation.oplog.FldOplogNum 中西 运行台帐选择操作票
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年9月20日 下午4:25:41
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldOplogNum extends MAXTableDomain {
	public FldOplogNum(MboValue paramMboValue) {
		super(paramMboValue);

		String str = getMboValue().getName();

		setRelationship("oplog", "1=1");

		String[] arrayOfString1 = { str };

		String[] arrayOfString2 = { "oplognum" };

		setLookupKeyMapInOrder(arrayOfString1, arrayOfString2);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		String siteid = getMboValue().getMbo().getString("siteid");
		if ("".equals(siteid)) {
			siteid = getMboValue().getMbo().getInsertSite();
		}
		setListCriteria("   siteid='" + siteid + "'");
		setListOrderBy("zqdate,zhibie  desc");
		return super.getList();
	}
}