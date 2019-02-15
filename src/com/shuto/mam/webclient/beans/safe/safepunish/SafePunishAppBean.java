package com.shuto.mam.webclient.beans.safe.safepunish;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * com.shuto.mam.webclient.beans.safe.safepunish.SafePunishAppBean 华东大区 阜阳项目
 * 安全处罚审批 appbean
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月6日 下午8:51:51
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SafePunishAppBean extends AppBean {
	@Override
	public int SAVE() throws MXException, RemoteException {

		// 得到主对象
		MboRemote mainMbo = app.getAppBean().getMbo();
		// 得到处罚部门对象
		MboSetRemote smawlineSet = mainMbo.getMboSet("SMAWLINE");

		// 计算处罚总金额
		double APPLYAMOUNT = smawlineSet.sum("AMOUNT");

		mainMbo.setValue("APPLYAMOUNT", APPLYAMOUNT, 11L);

		return super.SAVE();
	}
}
