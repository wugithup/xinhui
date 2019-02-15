package com.shuto.mam.app.operation.opticket;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年7月9日 下午3:31:25
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldOptPerson extends MAXTableDomain {

	public FldOptPerson(MboValue mbv) {
		super(mbv);
		setRelationship("person", "1=1");
		String[] target = { getMboValue().getName() };
		String[] source = { "personid" };
		setLookupKeyMapInOrder(target, source);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = getMboValue().getMbo();
		String siteid = mainMbo.getInsertSite();
		setListCriteria("status ='活动' and locationsite = '" + siteid + "'");
		return super.getList();
	}

}
