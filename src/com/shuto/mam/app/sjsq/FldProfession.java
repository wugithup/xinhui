package com.shuto.mam.app.sjsq;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.sjsq.FldProfession 人员选择专业
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月14日 下午5:25:05
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldProfession extends MAXTableDomain {
	public FldProfession(MboValue mbv) {
		super(mbv);
		setRelationship("PROFESSION", "");
		String[] strFrom = new String[] { "PROFESSIONNUM" };
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
		super.action();

	}
}
