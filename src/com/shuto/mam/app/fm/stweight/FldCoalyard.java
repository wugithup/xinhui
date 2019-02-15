package com.shuto.mam.app.fm.stweight;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 库存煤质维护 com.shuto.mam.app.fm.stweight.FldCoalyard 河北分公司  @author       liwc  
 * liwc@shuoto.cn  @date         2017年5月4日 下午2:27:14
 *  @Copyright:   2017 Shuto版权所有  @version      V1.0 
 */
public class FldCoalyard extends MAXTableDomain {
	public FldCoalyard(MboValue mbovalue) throws MXException {
		super(mbovalue);
		setRelationship("ST_CSTORAGE", "");
		String thisAtt = getMboValue().getName();
		String[] strFrom = { "COALYARDNUM" };
		String[] strTo = { thisAtt };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria(
				"STYPE = 'CSTORAGE' AND COALYARDNUM IN (SELECT COALYARDNUM FROM ST_COALYEARD WHERE STATUS = '有效')");
		return super.getList();
	}
}