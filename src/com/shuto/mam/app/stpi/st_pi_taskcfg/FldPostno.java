package com.shuto.mam.app.stpi.st_pi_taskcfg;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPostno extends MAXTableDomain {
	public FldPostno(MboValue mbovalue) throws MXException {
		super(mbovalue);
		setRelationship("ST_PI_POST", "");
		String[] strFrom = { "ST_PI_POSTID","NO" }; // 设置数据库中对象的列
		String[] strTo = { "ST_PI_POSTID","POSTNO" }; // 设置要填充的界面对象上的列,该列要与数据库表列一一对应;
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mbo = this.getMboValue().getMbo();
		String type = mbo.getString("type");
		String professional = mbo.getString("PROFESSIONAL");
		setListCriteria("professional = '"+professional+"' and type = '"+type+"'");
		return super.getList();
	}
}
