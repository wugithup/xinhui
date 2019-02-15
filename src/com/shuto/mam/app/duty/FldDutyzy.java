package com.shuto.mam.app.duty;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.duty.FldDutyzy 江苏
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月17日 上午9:22:14
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */

public class FldDutyzy extends MAXTableDomain{
	String tableName=null;
	String attName=null;

	public FldDutyzy(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
		attName = getMboValue().getName();
		tableName=getMboValue().getMbo().getName();
		setRelationship("ALNDOMAIN", "1=1");
		String[] strTo = { attName };
		
		String[] strFrom = { "value" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}
	
	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		Mbo mbo = getMboValue().getMbo();
		setListCriteria("domainid='DUTYNOTETYPE' and orgid='"+mbo.getString("orgid")+"'");
		setListOrderBy("ordernum");
		return super.getList();
	}
}