package com.shuto.mam.app.jngl.jngz.jn_jzsy;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.jngl.jngz.jn_jzsy.FldSydw 华东大区 阜阳项目
 * 
 * @author lzq liuzq@shuoto.cn
 * @date 2017-4-14 上午9:32:24
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldSydw extends MAXTableDomain {
	public FldSydw(MboValue mbovalue) throws MXException {
		super(mbovalue);
		setRelationship("DEPARTMENT", "");
		String[] strFrom = { "DEPNUM" };
		String[] strTo = { "SYDW" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("siteid=:siteid and  status='活动'");
		return super.getList();
	}
}