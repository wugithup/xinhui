package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/** 选择机组**/
public class FldErpjz extends MAXTableDomain{

	public FldErpjz(MboValue mbv) {
		super(mbv);
		setRelationship("erpjz", "1=1"); 
		setListCriteria("1=1");
		String[] target = { this.getMboValue().getName() };
		String[] source = { "erpjznum" };
		setLookupKeyMapInOrder(target, source);
	}
	
	public MboSetRemote getList() throws MXException, RemoteException {
		String where = "orgid = :orgid and siteid=:siteid" ;
		MboRemote mainMbo = this.getMboValue().getMbo();
		MboSetRemote objSet = mainMbo.getMboSet("PROJCODE");
		String jz = "" ;
		if(objSet != null && objSet.count()>0){
			jz = objSet.getMbo(0).getString("GROUP_NUM");
			if(jz != null && !"".equals(jz)){
				where = where + " and rl = '"+objSet.getMbo(0).getString("GROUP_NUM")+"'" ;
			}
		}
		setListCriteria(where);
		return super.getList();
	}
	
	public void action() throws MXException, RemoteException {
		super.action();
	}
}
