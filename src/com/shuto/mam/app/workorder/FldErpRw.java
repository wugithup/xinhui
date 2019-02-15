package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/** 选择任务**/
public class FldErpRw extends MAXTableDomain{

	public FldErpRw(MboValue mbv) {
		super(mbv);
		setRelationship("WOTACK", "1=1"); 
		setListCriteria("1=1");
		String[] target = { "task_id" };
		String[] source = { "task_id" };
		setLookupKeyMapInOrder(target, source);
	}
	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = this.getMboValue().getMbo();
		//所属组织
		String orgid = mainMbo.getString("orgid");
		String siteid = mainMbo.getString("siteid");
		String interfaceorgtype = mainMbo.getString("interfaceorgtype");
		
	    String porject = mainMbo.getString("PROJECT_CODE");
		String where = "orgid='"+orgid+"' and siteid='"+siteid+"' and interfaceorgtype='"+interfaceorgtype+"'";
		if(porject!= null && !porject.equals("")){
			where = where + " and project_code = '"+porject+"'" ;
		} else {
			where = where + " and project_code = ''" ;
		}
		setListCriteria(where);
		return super.getList();
	}
}
