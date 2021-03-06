package com.shuto.mam.webclient.beans.om.safetya;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.NonPersistentMboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.om.safetya.SelectPersonBean 华东大区 阜阳项目 安健环安全活动
 * 人员列表选择人员弹出框类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月6日 下午9:01:31
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SelectPersonBean extends DataBean {
	public MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		MboRemote Mbo = app.getAppBean().getMbo();
		MboSetRemote personSet = Mbo.getMboSet("$person", "PERSON",
				"status = '活动' and department is not null");
		return personSet;
	}

	public int execute() throws RemoteException, MXException {
		if (mboSetRemote != null)
			if (mboSetRemote instanceof NonPersistentMboSetRemote) {
				((NonPersistentMboSetRemote) mboSetRemote).execute();
				clientSession.addMXWarnings(mboSetRemote.getWarnings());
				if (parent != null)
					return parent.execute();
				app.getAppBean().save();
			} else {
				if (parent != null && parentRelationship != null)
					return parent.execute();
			}
		Vector personLineSet = getMboSet().getSelection();
		MboRemote personLineMbo = null;
		String personid = "";
		MboRemote Mbo = app.getAppBean().getMbo();
		String appName = Mbo.getThisMboSet().getApp();
		String noticebillnum = Mbo.getString("SAFETYACTIVITYNUM").replaceAll(
				",", "");
		MboSetRemote overhauljdpersonSet = Mbo.getMboSet("PERSONDATA");
		MboRemote overhauljdpersonMbo = null;
		for (int i = 0; i < personLineSet.size(); i++) {
			personLineMbo = (MboRemote) personLineSet.elementAt(i);
			personid = personLineMbo.getString("PERSONID");
			overhauljdpersonMbo = overhauljdpersonSet.add();
			overhauljdpersonMbo.setValue("DATANUM", noticebillnum);
			overhauljdpersonMbo.setValue("APP", appName);
			overhauljdpersonMbo.setValue("PERSONID", personid);
		}
		app.getAppBean().reloadTable();
		return 1;
	}

}
