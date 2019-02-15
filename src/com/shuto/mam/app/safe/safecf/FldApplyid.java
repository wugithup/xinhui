package com.shuto.mam.app.safe.safecf;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.safe.safecf.FldApplyid 华东大区 阜阳项目 安全处罚 处罚编号
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月31日 上午11:41:10
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldApplyid extends MAXTableDomain {
	public FldApplyid(MboValue mbovalue) {
		super(mbovalue);
		setRelationship("SMAWARDAPPLY", "");
		String[] strFrom = { "applyid" };
		String[] strTo = { "applyid" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		return super.getList();
	}
}