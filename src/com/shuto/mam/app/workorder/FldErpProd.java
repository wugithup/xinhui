package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/** 选择产品段**/
public class FldErpProd extends MAXTableDomain{

	public FldErpProd(MboValue mbv) {
		super(mbv);
		setRelationship("erpprod", "1=1"); 
		setListCriteria("1=1");
		String[] target = { this.getMboValue().getName() };
		String[] source = { "prod_code" };
		setLookupKeyMapInOrder(target, source);
	}
	public MboSetRemote getList() throws MXException, RemoteException {
		String where = "enabled_flag='Y' and trunc(sysdate) between nvl(start_date_active,trunc(sysdate)-1) and nvl(end_date_active,trunc(sysdate)+1)" ;
		setListCriteria(where);
		return super.getList();
	}
}
