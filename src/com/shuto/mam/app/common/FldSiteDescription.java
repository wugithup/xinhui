package com.shuto.mam.app.common;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**   
 * @Title: FldSiteDescription.java 
 * @Package com.shuto.mam.app.common 
 * @Description: TODO(对SITE.DESCRIPTION进行过滤) 
 * @author wuqi   
 * @date 2017-7-4 下午05:22:12 
 * @version V1.0   
 */
public class FldSiteDescription  extends MAXTableDomain {

	public FldSiteDescription(MboValue mbv) {
		super(mbv);
		String thisAtt = getMboValue().getName();
		setRelationship("SITE", "1=1");

		String[] strFrom = { thisAtt };

		String[] strTo = { "DESCRIPTION" };

		setLookupKeyMapInOrder(strFrom, strTo);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("ACTIVE = 1 AND YETAI  NOT IN ('风电')");
		return super.getList();
	}
}