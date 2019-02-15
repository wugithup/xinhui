package com.shuto.mam.webclient.beans.om.safetya;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.om.safetya.SafetyaDataBean 华东大区 阜阳项目 安健环安全活动
 * 新建人员列表类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月6日 下午9:00:12
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SafetyaDataBean extends DataBean {

	public int addrow() throws MXException {

		try {
			MboRemote Mbo = app.getAppBean().getMbo();
			String datanum = Mbo.getString("SAFETYACTIVITYNUM").replaceAll(",",
					"");
			String app = Mbo.getString("APP");
			String status = Mbo.getString("STATUS");
			if ("新建".equals(status)) {
				super.addrow();
				getMbo().setValue("DATANUM", datanum, 11L);
				getMbo().setValue("APP", app, 11L);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return 1;
	}

	public int toggledeleterow() throws MXException {

		try {
			MboRemote Mbo = app.getAppBean().getMbo();
			String status = Mbo.getString("STATUS");
			if ("新建".equals(status)) {
				super.toggledeleterow();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 1;
	}

}