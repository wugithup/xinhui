package com.shuto.mam.app.asset.assprol;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 
 * com.shuto.mam.app.asset.assprol.FldExecutive 河北分公司（曹妃甸）
 * 
 * @author songdd songdd@shuoto.cn
 * @date 2017年5月7日 下午7:25:32
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldExecutive extends MAXTableDomain {
	public FldExecutive(MboValue mbv) {
		super(mbv);
		setRelationship("person", "1=1");
		String[] strTo = { "EXECUTIVE" };
		String[] strFrom = { "personid" };

		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		String sql = "JOBCODE = '10477011' or JOBCODE = '10477173' or JOBCODE = '10477012' or JOBCODE = '10477166'";
		setListCriteria(sql);
		return super.getList();
	}
}