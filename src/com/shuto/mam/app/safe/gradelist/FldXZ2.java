package com.shuto.mam.app.safe.gradelist;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.safe.gradelist.FldXZ1 华东大区阜阳项目 安健环体系 二级目录字段查找类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月29日 下午8:03:06
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldXZ2 extends MAXTableDomain {

	public FldXZ2(MboValue mboValue) {
		super(mboValue);
		String s = getMboValue().getAttributeName();
		setRelationship("gradelist", "gradelistnum= :" + s);
		setLookupKeyMapInOrder(new String[] { s },
				new String[] { "gradelistnum" });
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		MboValue thisValue = getMboValue();
		Mbo thisMbo = thisValue.getMbo();
		String gradelist = thisMbo.getString("GRADELIST");
		String orgid = thisMbo.getString("orgid");
		String siteid = thisMbo.getString("siteid");
		setListCriteria(" parent ='" + gradelist
				+ "' and GRADESTATUS = '启用' and type='EHS体系'  and  orgid ='"
				+ orgid + "'  and  siteid='" + siteid + "'");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		MboValue thisValue = getMboValue();
		Mbo thisMbo = thisValue.getMbo();
		thisMbo.setValue("GRADELIST3", "", 11L);
	}

}
