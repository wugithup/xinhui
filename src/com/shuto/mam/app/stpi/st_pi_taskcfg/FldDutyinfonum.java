package com.shuto.mam.app.stpi.st_pi_taskcfg;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldDutyinfonum extends MAXTableDomain {
	public FldDutyinfonum(MboValue mbovalue) throws MXException {
		super(mbovalue);
		setRelationship("ST_PI_DUTYINFO", "");
		String[] strFrom = { "ST_PI_DUTYINFONUM" }; // 设置数据库中对象的列
		String[] strTo = { "ST_PI_DUTYINFONUM" }; // 设置要填充的界面对象上的列,该列要与数据库表列一一对应;
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mbo = this.getMboValue().getMbo();
		String professional = mbo.getString("PROFESSIONAL");
		setListCriteria("(professional is null or professional = '"+professional+"') and IS_ACTIVITY = 1");
		return super.getList();
	}
}
