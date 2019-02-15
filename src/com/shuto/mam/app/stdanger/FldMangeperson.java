package com.shuto.mam.app.stdanger;

/**  
com.shuto.mam.app.stdanger.FldMangeperson 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月3日 下午8:47:34
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldMangeperson extends MAXTableDomain {
	public FldMangeperson(MboValue paramMboValue) {
		super(paramMboValue);
		String str = getMboValue().getName();
		setRelationship("person", "1=1");
		String[] arrayOfString1 = { "personid" };
		String[] arrayOfString2 = { str };
		setLookupKeyMapInOrder(arrayOfString2, arrayOfString1);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo localMbo = getMboValue().getMbo();
		String str1 = localMbo.getString("siteid");
		String str2 = " locationsite = '" + str1 + "' and status = '活动' and department is not null";
		setListCriteria(str2);
		return super.getList();
	}
}