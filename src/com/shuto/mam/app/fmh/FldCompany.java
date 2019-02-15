package com.shuto.mam.app.fmh;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.fmh.FldCompany 华东大区 阜阳项目 副产品选择供应商类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月17日 下午4:04:35
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldCompany extends MAXTableDomain {

	public FldCompany(MboValue mbv) {
		super(mbv);
		setRelationship("COMPANIES", "1=1");
		String[] strTo = new String[] { "company" };
		String[] strFrom = new String[] { "company" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		// 设置条件
		setListCriteria("TYPE='H' AND DISABLED='0'");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
		Mbo mbo = getMboValue().getMbo();
		MboSetRemote fmhjslineset = mbo.getMboSet("FMHJSLINE");
		if (!fmhjslineset.isEmpty()) {
			throw new MXApplicationException("提示", "如若更改,则先清空明细表");
		}

	}

}
