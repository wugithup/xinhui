package com.shuto.mam.workflow.ticket;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.icu.text.DecimalFormat;
import com.shuto.mam.app.expand.AutoDateSiteNum;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class GzpScbh implements ActionCustomClass{

	@Override
	public void applyCustomAction(MboRemote mainmbo, Object[] arg1)
			throws MXException, RemoteException {
		String orgid = mainmbo.getString("orgid");
		String siteid = mainmbo.getString("siteid");
		// 取得票种简拼
		String abbreviation = mainmbo.getString("S_ORDERTYPE.MAXVALUE");
		String S_ORDERTYPE = mainmbo.getString("S_ORDERTYPE");
		// 工作票编号
		String S_WOTKNUM = mainmbo.getString("S_WOTKNUM");
		// 应用程序名
		String appName = mainmbo.getThisMboSet().getApp();
		// 获取系统时间
		String date = new SimpleDateFormat("yyMM").format(new Date());
		// 获取许可时间
		String xkDate = mainmbo.getString("S_ZZPZDATE");
		if (!xkDate.isEmpty()) {
			if (!S_ORDERTYPE.isEmpty()) {
				if (S_WOTKNUM.isEmpty()) {
					// 实例化自动编号方法类
					AutoDateSiteNum adsn = new AutoDateSiteNum(mainmbo.getThisMboSet());
					// 获取自动编号
					int num = adsn.getNextAutoDateSiteNum(orgid, siteid,
							appName, null, abbreviation);
					mainmbo.setValue("S_WOTKNUM", abbreviation + date
							+ new DecimalFormat("000").format(num), 11L);
				}
			}
		}
		
	}

}
