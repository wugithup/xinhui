package com.shuto.mam.workflow.operation;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import com.ibm.icu.text.DecimalFormat;
import com.shuto.mam.app.expand.AutoDateSiteNum;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年5月8日 下午8:12:46
 * @since
 * @version 1.1
 * @Copyright: 2017 Shuto版权所有
 */
public class ActionOpticketCode implements ActionCustomClass {
	public void applyCustomAction(MboRemote mbo, Object[] arg1)
			throws RemoteException, MXException {
		String appName = mbo.getThisMboSet().getApp();
		if ("H_OPTICKET".equals(appName)) {
			MboSetRemote thisMboSet = mbo.getThisMboSet();
			String siteid = mbo.getString("siteid");// 获取当前站点
			String orgid = mbo.getString("orgid");// 获取当前组织
			// 取得票种简拼
			String abbreviation = mbo.getString("H_TYPE.MAXVALUE");
			String date = new SimpleDateFormat("yyyyMM").format(new Date());// 获取系统时间
			AutoDateSiteNum adsn = new AutoDateSiteNum(thisMboSet); // 实例化自动编号方法类
			// 获取自动编号
			int num = adsn.getNextAutoDateSiteNum(orgid, siteid, appName, null,
					abbreviation);
			mbo.setValue("OPTICKETAPPRNUM", abbreviation + date
					+ new DecimalFormat("000").format(num), 11L);
		}
	}
}
