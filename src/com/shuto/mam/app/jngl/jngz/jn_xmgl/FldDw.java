package com.shuto.mam.app.jngl.jngz.jn_xmgl;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.jngl.jngz.jn_xmgl.FldDw 华东大区 阜阳项目
 * 
 * @author lzq liuzq@shuoto.cn
 * @date 2017-4-14 上午9:31:38
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldDw extends MAXTableDomain {
	public FldDw(MboValue mbovalue) throws MXException {
		super(mbovalue);
		setRelationship("DEPARTMENT", "");
		String[] strFrom = { "DEPNUM" };
		String[] strTo = { "DW" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("siteid=:siteid and  status='活动'");
		return super.getList();
	}
}