package com.shuto.mam.app.jsjd;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**  
com.shuto.mam.app.jsjd.FldKks 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-13 下午5:02:25
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class FldKks extends MAXTableDomain {
	public FldKks(MboValue mbovalue) throws MXException, RemoteException {
		super(mbovalue);
		String thisname = mbovalue.getName();
		setRelationship("LOCATIONS", "");
		String[] strFrom = { "LOCATION" };
		String[] strTo = { thisname };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("");

		return super.getList();
	}
}
