package com.shuto.mam.app.wo.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldFadianbu extends MAXTableDomain {

	public FldFadianbu(MboValue mbv) {
		super(mbv);
		// 获得当前字段名
		String thisAtt = getMboValue().getName();
		// 设置要获取数据表的关系
		setRelationship("person", "personid=:" + thisAtt);
		// 目标字段名变量
		String[] strTo = { thisAtt };
		// 获取值 字段名变更
		String[] strFrom = { "personid" };
		// 此方法将获取到的值设置到目标字段 中
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mbo = getMboValue().getMbo().getOwner();
		String siteid = mbo.getString("siteid");
		this.setListCriteria(" status  = '活动' and locationsite='" + siteid + "' and department ='DEPT03' ");
		return super.getList();
	}
}
