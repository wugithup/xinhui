package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/** 选择预算科目 **/
public class FldErpYskm extends MAXTableDomain {

	public FldErpYskm(MboValue mbv) {
		super(mbv);
		setRelationship("FACCOUNTS", "1=1");
		setListCriteria("1=1");
		String[] target = { "dept_line_id" };
		String[] source = { "line_id" };
		setLookupKeyMapInOrder(target, source);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = this.getMboValue().getMbo();
		// 所属组织
		String orgid = mainMbo.getString("orgid");
		String siteid = mainMbo.getString("siteid");
		String interfaceorgtype = mainMbo.getString("interfaceorgtype");
		
		String where = "orgid='" + orgid + "' and siteid='"+siteid+"' and interfaceorgtype='"+interfaceorgtype+"' and enabled_flag='Y'";

		MboSetRemote depSet = mainMbo.getMboSet("GSDEPARTMENT");
		if (depSet != null && depSet.count() > 0) {
			where = where + " and DEPT_CODE = '" + depSet.getMbo(0).getString("ERPDEPNUM") + "'";
		} else {
			where = "1!=1";
		}
		setListCriteria(where);
		this.setListOrderBy("acct_number,subaccount_name");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mainMbo = this.getMboValue().getMbo();
		mainMbo.setValue("project_code", "", 2l);
	}
}
