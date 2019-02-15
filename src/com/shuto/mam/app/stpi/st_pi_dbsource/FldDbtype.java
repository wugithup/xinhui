package com.shuto.mam.app.stpi.st_pi_dbsource;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 选择数据库类型
 * @author mabin
 * 2016年10月28日
 */
public class FldDbtype extends MAXTableDomain {
	public FldDbtype(MboValue mbovalue) throws MXException {
		super(mbovalue);
		String thisname = this.getMboValue().getName();
		setRelationship("ST_PI_DATABASE", "");
		String[] strFrom = { "DBTYPE" }; // 设置数据库中对象的列
		String[] strTo = { thisname }; // 设置要填充的界面对象上的列,该列要与数据库表列一一对应;
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("1=1");
		return super.getList();
	}
	@Override
	public void action() throws MXException, RemoteException {
		MboRemote thisMbo=this.getMboValue().getMbo();
		MboSetRemote databaseSet = thisMbo.getMboSet("DBTYPE");
		if(!databaseSet.isEmpty()){
			MboRemote databaseMbo = databaseSet.getMbo(0);
			thisMbo.setValue("DBDRIVER", databaseMbo.getString("DBDRIVER"),11L);
			thisMbo.setValue("DBURL", databaseMbo.getString("DBURL"),11L);
		}
		super.action();
	}
}
