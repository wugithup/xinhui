package com.shuto.mam.app.wo.hse;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.wo.hse.FldAqsid 华东大区 查找安全锁
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年8月22日 下午5:50:50
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldAqsid extends MAXTableDomain {
	String tableName = null;
	String attName = null;

	public FldAqsid(MboValue mbv) {
		super(mbv);
		attName = getMboValue().getName();
		tableName = getMboValue().getMbo().getName();
		setRelationship("HSE_LOCKANDKEY", "siteid=:siteid");
		String[] strTo = { attName };
		String[] strFrom = { "LOCKID" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mbo = getMboValue().getMbo();
		String Siteid = mbo.getString("Siteid");
		setListCriteria(" Siteid= '" + Siteid + "'  and  LOCKTYPE='安全锁'");
		setListOrderBy("LOCKID");
		return super.getList();
	}

}
