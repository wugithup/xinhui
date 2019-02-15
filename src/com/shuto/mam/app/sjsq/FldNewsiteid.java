package com.shuto.mam.app.sjsq;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 变更人员授权选择站点 com.shuto.mam.app.sjsq.FldNewsiteid
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月16日 上午10:05:33
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldNewsiteid extends MAXTableDomain {
	public FldNewsiteid(MboValue mbv) {
		super(mbv);
		setRelationship("site", "1=1");
		String[] strFrom = new String[] { "siteid" };
		String thisAttr = getMboValue().getAttributeName();
		String strTo[] = { thisAttr };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		String orgid = getMboValue("NEWORGID").getString();
		setListCriteria(" active = 1 and  orgid  = '" + orgid + "' ");
		return super.getList();
	}

	@Override
	public void action() throws MXException, RemoteException {
		Mbo mbo = getMboValue().getMbo();
		mbo.setValue("DEPARTMENT", "", 2L);
		super.action();

	}
}
