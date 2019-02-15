package com.shuto.mam.app.om.safetyactivity;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.om.safetyactivity.FldPersonzc 华东大区阜阳项目 安健环安全活动 主持人字段
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月29日 下午9:06:14
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldPersonzc extends MAXTableDomain {
	public FldPersonzc(MboValue mbovalue) throws MXException {
		super(mbovalue);
		setRelationship("PERSON", "");
		String[] strFrom = { "PERSONID" };
		String[] strTo = { "PERSONZC" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mbo = getMboValue().getMbo();
		String orgid = mbo.getString("orgid");
		String siteid = mbo.getString("siteid");
		setListCriteria("status = '活动' and department is not null and  locationorg ='"
				+ orgid + "'  and  locationsite='" + siteid + "'");
		return super.getList();
	}

}