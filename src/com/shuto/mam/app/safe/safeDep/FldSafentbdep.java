package com.shuto.mam.app.safe.safeDep;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.safe.safeDep.FldSafentbdep 华东大区阜阳项目 安全培训 培训管理部门字段 上级安全通报
 * 不安全事件通报
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月29日 下午8:59:44
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldSafentbdep extends MAXTableDomain {
	public FldSafentbdep(MboValue mbovalue) throws MXException {
		super(mbovalue);
		String thisname = mbovalue.getName();
		setRelationship("DEPARTMENT", "");
		String[] strFrom = { "DEPNUM" };
		String[] strTo = { thisname };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mbo = getMboValue().getMbo();
		String orgid = mbo.getString("orgid");
		String siteid = mbo.getString("siteid");
		setListCriteria("status = '活动'  and  orgid ='" + orgid
				+ "'  and  siteid='" + siteid + "'");
		return super.getList();
	}

}
