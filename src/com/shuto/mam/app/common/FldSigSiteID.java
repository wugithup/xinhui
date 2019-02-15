package com.shuto.mam.app.common;

import java.rmi.RemoteException;

import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**   
 * @Title: FldSigSiteID.java 
 * @Package com.shuto.mam.app.common 
 * @Description: TODO(缺省信息过滤站点ACTIVE =1) 
 * @author wuqi   
 * @date 2017-6-20 下午05:03:51 
 * @version V1.0   
 */
public class FldSigSiteID  extends psdi.app.signature.FldSigSiteID{

	public FldSigSiteID(MboValue arg0) throws MXException {
		super(arg0);
	}
@Override
public MboSetRemote getList() throws MXException, RemoteException {
//	super.getList();
	setListCriteria(" ACTIVE =1 ");
//	System.out.println(" ACTIVE =1 ");
	return super.getList();
}
}
