package com.shuto.mam.app.sjsq;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.sjsq.FldPost 人员选择岗位
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月14日 下午5:24:48
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldPost extends MAXTableDomain {
	public FldPost(MboValue mbv) {
		super(mbv);
		setRelationship("POST", "");
		String[] strFrom = new String[] { "postnum" };
		String thisAttr = getMboValue().getAttributeName();
		String strTo[] = { thisAttr };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		String siteid = getMboValue("newsiteid").getString();
		String depnum = getMboValue("DEPARTMENT").getString();
		setListCriteria("status = '活动' and siteid = '" + siteid + "' and depnum = '" + depnum + "'");
		return super.getList();
	}

	@Override
	public void action() throws MXException, RemoteException {
		super.action();

	}
}
