package com.shuto.mam.app.common;

import java.rmi.RemoteException;

import psdi.app.person.FldPersonID;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 
 * com.shuto.mam.app.common.SelectPersonSiteid 东北分公司 沈阳项目
 * 
 * @author songdd songdd@shuoto.cn
 * @date 2017年5月4日 上午10:39:38
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SelectPersonSiteid extends FldPersonID {
	public SelectPersonSiteid(MboValue mbv) throws MXException, RemoteException {
		super(mbv);
		String thisAtt = getMboValue().getName();
		setRelationship("PERSON", "1=1");

		String[] strFrom = { thisAtt };

		String[] strTo = { "PERSONID" };

		setLookupKeyMapInOrder(strFrom, strTo);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		String SITEID = getMboValue().getMbo().getUserInfo().getInsertSite();
		String where = "STATUS = '活动' AND LOCATIONSITE='" + SITEID + "'";

		setListCriteria(where);

		return super.getList();
	}
}