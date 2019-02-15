package com.shuto.mam.app.om.pm;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.om.pm.FldDepnum 华东大区阜阳项目 安全定期工作选择部门
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月31日 下午2:45:15
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldDepnum extends MAXTableDomain {
	public FldDepnum(MboValue mbovalue) throws MXException {
		super(mbovalue);
		setRelationship("DEPARTMENT", "");
		String[] strFrom = { "DEPNUM" };
		String[] strTo = { "DEPNUM" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote Mbo = getMboValue().getMbo();
		String app = Mbo.getThisMboSet().getApp();
		if ("WOPM".equals(app)) {
			// setListCriteria("isww = '1'");设置为外委
		} else {
			setListCriteria("");
		}

		return super.getList();
	}
}