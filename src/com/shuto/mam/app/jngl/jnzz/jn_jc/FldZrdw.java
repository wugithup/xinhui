package com.shuto.mam.app.jngl.jnzz.jn_jc;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 华东大区阜阳项目 选择部门类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月29日 下午8:59:44
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldZrdw extends MAXTableDomain {
	public FldZrdw(MboValue mbovalue) throws MXException {
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
