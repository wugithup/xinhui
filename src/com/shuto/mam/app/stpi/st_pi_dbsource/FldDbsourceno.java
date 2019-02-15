package com.shuto.mam.app.stpi.st_pi_dbsource;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 选择数据源编号
 * @author mabin
 * 2016年10月28日
 */
public class FldDbsourceno extends MAXTableDomain {
	public FldDbsourceno(MboValue mbovalue) throws MXException {
		super(mbovalue);
		String thisname = this.getMboValue().getName();
		setRelationship("ST_PI_DBSOURCE", "");
		String[] strFrom = { "DBSOURCENO" }; // 设置数据库中对象的列
		String[] strTo = { thisname }; // 设置要填充的界面对象上的列,该列要与数据库表列一一对应;
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("1=1");
		return super.getList();
	}
}
