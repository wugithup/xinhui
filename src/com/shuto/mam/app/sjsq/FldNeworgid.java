package com.shuto.mam.app.sjsq;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.sjsq.FldNeworgid 变更人员授权选择新的组织
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月16日 上午9:58:33
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldNeworgid extends MAXTableDomain {
	public FldNeworgid(MboValue mbv) {
		super(mbv);
		setRelationship("organization", "1=1");
		String[] strFrom = new String[] { "orgid" };
		String thisAttr = getMboValue().getAttributeName();
		String strTo[] = { thisAttr };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo mbo = getMboValue().getMbo();
		String orgid = mbo.getString("orgid");
		String siteid = mbo.getString("siteid");
		String yetai = mbo.getString("site1.yetai");// 机构级别
		String sql = "";
		if ("控股".equals(yetai)) {
			sql = " dfltitemstatus = '活动' ";
		} else if ("本部".equals(yetai)) {
			sql = "dfltitemstatus = '活动' and locationorg = '" + orgid + "'";
		} else {
			sql = "dfltitemstatus = '活动' and locationsite = '" + siteid + "'";
		}

		setListCriteria(sql);

		return super.getList();
	}

	@Override
	public void action() throws MXException, RemoteException {
		Mbo mbo = getMboValue().getMbo();
		mbo.setValue("NEWSITEID", "", 2L);
		super.action();

	}
}
