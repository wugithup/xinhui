package com.shuto.mam.webclient.beans.gls;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * com.shuto.mam.webclient.beans.gls.HseTaglockAppBean 华东大区 隔离点
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年6月23日 上午11:30:26
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class HseTaglockAppBean extends AppBean {

	@Override
	public int SAVE() throws MXException, RemoteException {
		MboRemote mbo = this.getMbo();
		String GLDJC = "";
		MboSetRemote HSEWOWHMSR = mbo.getMboSet("HSEWOWH");// 获取隔离锁工作票使用维护
		if (!HSEWOWHMSR.isEmpty()) {
			GLDJC = HSEWOWHMSR.getMbo(0).getString("GLDJC");
		}
		String LOCATION = mbo.getString("LOCATION");
		String newnum = GLDJC + "_" + LOCATION;
		mbo.setValue("TAGOUTID", newnum, 11l);

		return super.SAVE();

	}

}
