package com.shuto.mam.app.om.pm;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.om.pm.FldPmstatus华东大区阜阳项目 安全定期工作状态字段
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月31日 下午4:10:27
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldPmstatus extends MboValueAdapter {
	public FldPmstatus(MboValue mbo) {
		super(mbo);
	}

	public void init() throws MXException, RemoteException {
		super.init();
		String[] Str = { "NOTEZX", "NOTEYY", "PMSTATUS" };
		MboRemote Mbo = getMboValue().getMbo();
		String app = Mbo.getThisMboSet().getApp();
		if (app == null) {
			MboRemote Owner = Mbo.getOwner();
			if (Owner != null && !Owner.getName().equals("PROWO")) {
				String statusOwner = Owner.getString("STATUS");
				String appName = Owner.getThisMboSet().getApp();
				if ("OPLOG".equals(appName))
					if ("已交班".equals(statusOwner))
						Mbo.setFieldFlag(Str, 7L, true);
					else
						Mbo.setFieldFlag(Str, 7L, false);
			}
		} else {
			String status;
			String appName = getMboValue().getMbo().getThisMboSet().getApp();
			if ("OPPMOW".equals(appName)) {
				status = getMboValue().getString();
				if ((!("新建".equals(status))) && (!("超期已批准".equals(status))))
					Mbo.setFieldFlag(Str, 7L, true);
				else
					Mbo.setFieldFlag(Str, 7L, false);
			}

			if ("WOPMWO".equals(appName)) {
				status = getMboValue().getString();
				if (!("新建".equals(status))) {
					Mbo.setFieldFlag(Str, 7L, true);
					Mbo.setFieldFlag("DATEZX", 7L, true);
					Mbo.setFieldFlag("NOTECQ", 7L, true);
				} else {
					Mbo.setFieldFlag(Str, 7L, false);
					Date reportDate = Mbo.getDate("REPORTDATE");
					Date sysDate = new Date();
					if (!(date01(sysDate).equals(date01(reportDate)))) {
						Mbo.setFieldFlag(Str, 7L, true);
						Mbo.setFieldFlag("DATEZX", 128L, false);
						Mbo.setFieldFlag("DATEZX", 7L, true);
						Mbo.setFieldFlag("NOTECQ", 7L, false);
						Mbo.setFieldFlag("NOTECQ", 128L, true);
					} else {
						int sysDateInt = Integer.parseInt(date02(sysDate));
						if (sysDateInt > 193000) {
							Mbo.setFieldFlag(Str, 7L, true);
							Mbo.setFieldFlag("DATEZX", 128L, false);
							Mbo.setFieldFlag("DATEZX", 7L, true);
							Mbo.setFieldFlag("NOTECQ", 7L, false);
							Mbo.setFieldFlag("NOTECQ", 128L, true);
						} else {
							Mbo.setFieldFlag(Str, 7L, false);
							Mbo.setFieldFlag("DATEZX", 128L, true);
							Mbo.setFieldFlag("DATEZX", 7L, false);
							Mbo.setFieldFlag("NOTECQ", 7L, true);
							Mbo.setFieldFlag("NOTECQ", 128L, false);
						}
					}
				}
			}
		}
	}

	public String date01(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String Str = formatter.format(date);
		return Str;
	}

	public String date02(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		String Str = formatter.format(date);
		return Str;
	}
}