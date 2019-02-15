package com.shuto.mam.app.person;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 
 * @Title: FldPost.java
 * @Description: TODO
 * @author: lull lull@shuto.cn
 * @date: 2017年4月20日 下午11:14:38
 * @version: V1.0.0
 */

public class FldPost extends MAXTableDomain {
	// com.shuto.mam.app.person.FldPost
	public FldPost(MboValue mbv) {
		super(mbv);
		setRelationship("post", "1=1");
		String[] strFrom = new String[] { "postnum" };
		String thisAttr = getMboValue().getAttributeName();
		String strTo[] = { thisAttr };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		String depnum = getMboValue("DEPARTMENT").getString();
		String siteid = getMboValue("locationsite").getString();
		setListCriteria("siteid = '" + siteid + "' and  status  = '活动' and depnum= '" + depnum + "'");
		return super.getList();
	}
}