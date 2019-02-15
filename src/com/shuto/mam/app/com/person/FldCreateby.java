package com.shuto.mam.app.com.person;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * com.shuto.mam.app.com.person.FldCreateby 河北分公司（曹妃甸）
 * 
 * @author songdd songdd@shuoto.cn
 * @date 2017年5月5日 下午3:17:13
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldCreateby extends MboValueAdapter {
	public FldCreateby(MboValue mbv) throws MXException, RemoteException {
		super(mbv);
	}

	public void action() throws RemoteException, MXException {
		super.action();
		Mbo mbo = getMboValue().getMbo();

		String department = mbo.getString("PERSON.DEPARTMENT");
		mbo.setValue("department", department, 11L);
	}
}