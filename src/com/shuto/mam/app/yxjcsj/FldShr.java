package com.shuto.mam.app.yxjcsj;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 *
 * com.shuto.mam.app.om.mfxdfx.mbo.MfxdfxRemote.java
 * 
 * app:MFXDFX(煤粉细度分析)
 * 
 * Email:xiamy@shuto.cn 2017年8月31日 上午9:41:33
 *
 */
public class FldShr extends MAXTableDomain {
	public FldShr(MboValue mbovalue) throws MXException {
		super(mbovalue);
		setRelationship("PERSON", "");
		String[] strFrom = { "PERSONID" };
		String[] strTo = { "SHR" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = this.getMboValue().getMbo();
		String siteid = mainMbo.getString("SITEID");
		if (siteid.isEmpty()) {
			siteid = mainMbo.getInsertSite();
		}
		setListCriteria("locationsite = '" + siteid + "' and status = '活动' and department is not null");
		return super.getList();
	}
}