package com.shuto.mam.app.safety.mbo;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.safety.mbo.FldYsnum 华东大区阜阳项目 隐患整改 元素编号字段
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月30日 下午5:37:06
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */

public class FldYsnum extends MAXTableDomain {
	public FldYsnum(MboValue mbv) {
		super(mbv);
		String thisAtt = getMboValue().getName();
		setRelationship("YHZGYS", "1=1");
		String[] strFrom = { "YSNUM" };
		String[] strTo = { thisAtt };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mbo = getMboValue().getMbo();
		return super.getList();
	}

	public void action() throws RemoteException, MXException {
		super.action();

		String ysnum = null;

		String ysname = null;

		String ysry = null;
		MboSetRemote comsignSet = getMboValue().getMbo().getMboSet("YSNUM");
		if (!getMboValue().isNull()) {
			if (!comsignSet.isEmpty()) {
				ysnum = comsignSet.getMbo(0).getString("YSNUM");
				ysname = comsignSet.getMbo(0).getString("YSNAME");
				ysry = comsignSet.getMbo(0).getString("YSRY");
			}
			getMboValue("YSNUM").setValue(ysnum, 11L);
			getMboValue("YSNAME").setValue(ysname, 11L);
			getMboValue("YSPERSON").setValue(ysry, 11L);
		}
		if (getMboValue().isNull()) {
			getMboValue("YSNUM").setValue(ysnum, 11L);
			getMboValue("YSNAME").setValue(ysname, 11L);
			getMboValue("YSPERSON").setValue(ysry, 11L);
		}
	}
}