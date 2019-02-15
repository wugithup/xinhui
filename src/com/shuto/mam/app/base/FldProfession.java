package com.shuto.mam.app.base;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.base.FldProfession 选择专业
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年5月9日 下午4:14:43
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
		Mbo mbo = getMboValue().getMbo();
		String orgid = mbo.getString("orgid");
		String siteid = mbo.getString("siteid");
		setListCriteria("(status = '活动' or status = '临时') and orgid = '" + orgid
				+ "'  and siteid = '" + siteid + "'");
		return super.getList();
	}

	@Override
	public void action() throws MXException, RemoteException {
		super.action();

	}
}
