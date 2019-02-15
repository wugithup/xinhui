package com.shuto.mam.app.fmh;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.fmh.FldZj 华东大区 阜阳项目 副产品结算总价格初始化
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月17日 下午4:21:31
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldZj extends MboValueAdapter {
	public FldZj(MboValue mbv) {
		super(mbv);
	}

	public void initValue() throws MXException, RemoteException {
		MboRemote mbo = getMboValue().getMbo();
		Double zjg = 0.0;
		MboSetRemote fmhjslineset = mbo.getMboSet("FMHJSLINE");
		if (!(fmhjslineset.isEmpty())) {
			for (int k = 0; k < fmhjslineset.count(); k++) {
				zjg += fmhjslineset.getMbo(k).getDouble("ZJ");
			}
			mbo.setValue("ZJ", zjg, 2l);
		} else {
			mbo.setValue("ZJ", "000000", 2l);
		}
		super.initValue();

	}
}
