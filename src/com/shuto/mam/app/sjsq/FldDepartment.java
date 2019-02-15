package com.shuto.mam.app.sjsq;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.sjsq.FldDepartment 人员应用 选择部门类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月14日 下午5:07:39
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldDepartment extends MAXTableDomain {
	public FldDepartment(MboValue mbv) {
		super(mbv);
		setRelationship("DEPARTMENT", "1=1");
		String[] strFrom = new String[] { "depnum" };
		String thisAttr = getMboValue().getAttributeName();
		String strTo[] = { thisAttr };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		String siteid = getMboValue("newsiteid").getString();
		setListCriteria("status = '活动' and siteid = '" + siteid + "'");
		return super.getList();
	}

	@Override
	public void action() throws MXException, RemoteException {
		Mbo mbo = getMboValue().getMbo();
		mbo.setValue("POSTNUM", "", 2L);
		super.action();

	}
}
