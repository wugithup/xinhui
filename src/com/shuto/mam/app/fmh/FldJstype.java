package com.shuto.mam.app.fmh;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.fmh.FldJstype 华东大区 阜阳项目 副产品结算 结算类型
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月17日 下午4:13:55
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldJstype extends MboValueAdapter {
	public FldJstype(MboValue mbv) {
		super(mbv);
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
