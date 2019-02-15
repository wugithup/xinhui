package com.shuto.mam.app.safe.safeDep;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.safe.safeDep.Fldapplydep 安全处罚部门
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月5日 下午8:22:29
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class Fldapplydep extends MAXTableDomain {
	public Fldapplydep(MboValue mbovalue) throws MXException {
		super(mbovalue);
		setRelationship("DEPARTMENT", "");
		String[] strFrom = { "DEPNUM" };
		String[] strTo = { "APPLYDEPT" };
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

	public void action() throws MXException, RemoteException {
		MboRemote thismbo = getMboValue().getMbo();
		String depnum = thismbo.getString("APPLYDEPT");
		MboSetRemote DepMboSet = thismbo.getMboSet("$department", "department",
				"depnum='" + depnum + "'");
		thismbo.setValue("description",
				DepMboSet.getMbo(0).getString("description"));
	}
}