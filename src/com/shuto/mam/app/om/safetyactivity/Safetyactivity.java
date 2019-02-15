package com.shuto.mam.app.om.safetyactivity;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.om.safetyactivity.Safetyactivity 华东大区 阜阳项目 安全活动表 对象类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月6日 下午8:33:15
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class Safetyactivity extends Mbo implements SafetyactivityRemote {
	public Safetyactivity(MboSet ms) throws RemoteException {
		super(ms);
	}

	public void init() throws MXException {
		super.init();
		String Str01[] = { "PERSONZC", "PERSONJL", "DESCRIPTION", "NOTE1",
				"TYPEHD", "UNIT", "STARTTIME", "ENDTIME" };
		String Str0201[] = { "CREATEDATE", "STARTTIME", "ENDTIME", "PERSONDT",
				"DESCRIPTION" };
		String Str0202[] = { "NOTEDT" };
		String Str0203[] = { "NOTEBC" };
		String Str03[] = { "STARTTIME", "ENDTIME", "DESCRIPTION", "NOTE2",
				"NOTE1" };
		String Str04[] = { "PERSONZC", "STARTTIME", "DESCRIPTION", "NOTE1",
				"NOTE2" };
		String Str05[] = { "PERSONZC", "MEETINGTYPE", "STARTTIME", "ENDTIME",
				"NOTE1" };
		try {
			String appName = getThisMboSet().getApp();
			if ("SAFETYA".equals(appName)) {
				String status = getString("STATUS");
				if (!"新建".equals(status)) {
					setFieldFlag(Str01, 7L, true);
				} else {
					setFieldFlag(Str01, 7L, false);
				}
				if (isNew()) {
					setFieldFlag(Str01, 7L, false);
				}
			}
			if ("TZ_EXPLAIN".equals(appName) || "TZ_SKILL".equals(appName)
					|| "TZ_EXPECT".equals(appName)) {
				String status = getString("STATUS");
				if ("待答题人回答".equals(status)) {
					setFieldFlag(Str0201, 7L, true);
					setFieldFlag(Str0202, 128L, true);
					setFieldFlag(Str0203, 7L, true);
				}
				if ("待出题人审核".equals(status)) {
					setFieldFlag(Str0201, 7L, true);
					setFieldFlag(Str0202, 128L, false);
					setFieldFlag(Str0202, 7L, true);
					setFieldFlag(Str0203, 7L, false);
				}
				if ("关闭".equals(status)) {
					setFieldFlag(Str0201, 7L, true);
					setFieldFlag(Str0202, 7L, true);
					setFieldFlag(Str0203, 7L, true);
				}
				if ("新建".equals(status)) {
					setFieldFlag(Str0201, 7L, false);
					setFieldFlag(Str0202, 7L, false);
					setFieldFlag(Str0203, 7L, false);
				}
				if (isNew()) {
					setFieldFlag(Str0201, 7L, false);
					setFieldFlag(Str0202, 7L, false);
					setFieldFlag(Str0203, 7L, false);
				}
			}
			if ("TZ_DRILL".equals(appName)) {
				String status = getString("STATUS");
				if (!"新建".equals(status)) {
					setFieldFlag(Str03, 7L, true);
				} else {
					setFieldFlag(Str03, 7L, false);
				}
				if (isNew()) {
					setFieldFlag(Str03, 7L, false);
				}
			}
			if ("TZ_CHECK".equals(appName)) {
				String status = getString("STATUS");
				if (!"新建".equals(status)) {
					setFieldFlag(Str04, 7L, true);
				} else {
					setFieldFlag(Str04, 7L, false);
				}
				if (isNew()) {
					setFieldFlag(Str04, 7L, false);
				}
			}
			if ("TZ_MEETING".equals(appName)) {
				String status = getString("STATUS");
				if (!"新建".equals(status)) {
					setFieldFlag(Str05, 7L, true);
				} else {
					setFieldFlag(Str05, 7L, false);
				}
				if (isNew()) {
					setFieldFlag(Str05, 7L, false);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}