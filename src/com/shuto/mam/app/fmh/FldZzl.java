package com.shuto.mam.app.fmh;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.fmh.FldZzl 华东大区 阜阳项目 副产品结算总重量初始化
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月17日 下午4:23:09
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldZzl extends MboValueAdapter {
	public FldZzl(MboValue mbv) {
		super(mbv);
	}

	public void initValue() throws MXException, RemoteException {
		MboRemote mbo = getMboValue().getMbo();
		Double zzl = 0.0;
		MboSetRemote fmhjslineset = mbo.getMboSet("FMHJSLINE");
		if (!(fmhjslineset.isEmpty())) {
			for (int k = 0; k < fmhjslineset.count(); k++) {
				zzl += fmhjslineset.getMbo(k).getDouble("JZ");
			}
			mbo.setValue("ZZL", zzl);
		} else {
			mbo.setValue("ZZL", "0");
		}
		super.initValue();

	}
}
