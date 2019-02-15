package com.shuto.mam.app.fmh;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.fmh.FldFcmgyszrnum 华东大区 阜阳项目 副产品选择供应商准入
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月17日 下午4:11:49
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldFcmgyszrnum extends MAXTableDomain {

	public FldFcmgyszrnum(MboValue mbv) {
		super(mbv);
		setRelationship("FCMGYSZR", "1=1");
		String[] strTo = new String[] { "FCMGYSZRNUM" };
		String[] strFrom = new String[] { "FCMGYSZRNUM" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		// 设置条件
		setListCriteria(
				"STATUS='已批准' AND FCMGYSZRNUM NOT IN (SELECT FCMGYSZRNUM FROM COMPANIES WHERE FCMGYSZRNUM IS NOT NULL)");
		return super.getList();
	}

}
