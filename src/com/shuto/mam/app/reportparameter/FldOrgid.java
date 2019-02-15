package com.shuto.mam.app.reportparameter;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年6月1日 下午5:40:27
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldOrgid extends MAXTableDomain {

	public FldOrgid(MboValue mbv) {
		super(mbv);
				setRelationship("organization", "1=1");
				setListCriteria("orgid <> 'CRPH000' and ACTIVE = 1");
				String[] target = { this.getMboValue().getName() };
				String[] source = { "orgid" };
				setLookupKeyMapInOrder(target, source);	
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		return super.getList();
	}

}
