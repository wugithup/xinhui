package com.shuto.mam.app.stpi.st_pi_post;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldDescription extends MAXTableDomain {
	public FldDescription(MboValue mbovalue) throws MXException {
		super(mbovalue);
		setRelationship("ST_PI_POST", "");
		String[] strFrom = { "ST_PI_POSTID","NO" }; // 设置数据库中对象的列
		String[] strTo = { "ST_PI_POSTID","POSTNO" }; // 设置要填充的界面对象上的列,该列要与数据库表列一一对应;
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("1=2");
		return super.getList();
	}

	@Override
	public void setListCriteria(String listWhere) {
		String sql = listWhere;
		String tableName = this.getMboValue().getMbo().getName();
		if("ST_PI_POST".equals(tableName)){
			sql = "1=1";
		}
		super.setListCriteria(sql);
	}
}
