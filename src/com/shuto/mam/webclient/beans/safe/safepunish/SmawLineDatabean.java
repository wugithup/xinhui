package com.shuto.mam.webclient.beans.safe.safepunish;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.safe.safepunish.SmawLineDatabean 华东大区 阜阳项目
 * 安全处罚审批 新建处罚项目bean
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月6日 下午8:53:52
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SmawLineDatabean extends DataBean {
	@Override
	public int addrow() throws MXException {
		int flag = super.addrow();

		try {

			// 得到新建的对象
			MboRemote newMbo = this.getMbo();

			MboRemote mainMbo = app.getAppBean().getMbo();
			// 得到处罚部门对象
			MboSetRemote smawlineSet = mainMbo.getMboSet("SMAWLINE");

			// 设置序号
			newMbo.setValue("sn", (smawlineSet.max("sn") + 1));
			// 设置app
			newMbo.setValue("app", "SAFEPUNISH");
			// 设置父级编号
			newMbo.setValue("APPLYID", mainMbo.getString("APPLYID"));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return flag;
	}
}
