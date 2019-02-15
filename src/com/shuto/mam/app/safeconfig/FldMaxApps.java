package com.shuto.mam.app.safeconfig;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.safeconfig.FldMaxApps安健环流程配置选择应用程序
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月31日 下午4:35:41
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldMaxApps extends MAXTableDomain {

	public FldMaxApps(MboValue mbv) {
		super(mbv);
		setRelationship("MAXAPPS", "");
		String[] strFrom = { "APP" };
		String[] strTo = { "APP" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	/**
	 * 限制只选择安健环中的应用
	 */
	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria(" app in (select keyvalue from maxmenu  where  moduleapp ='SAFE' and elementtype ='APP') and app<> 'SAFECONFIG' ");
		return super.getList();
	}

}
