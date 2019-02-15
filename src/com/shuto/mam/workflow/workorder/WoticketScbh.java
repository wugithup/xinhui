package com.shuto.mam.workflow.workorder;

import com.shuto.mam.app.expand.AutoDateSiteNum;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.shuto.mam.workflow.workorder.WoticketScbh 工作票生成编号
 *
 * @author shanbh shanbh@shuoto.cn
 * @version V1.0
 * @date 2017年6月28日 下午5:20:20
 * @Copyright: 2017 Shuto版权所有
 */
public class WoticketScbh implements ActionCustomClass {

	@Override
	public void applyCustomAction(MboRemote mainmbo, Object[] aobj) throws MXException, RemoteException {
		MboSetRemote thisMboSet = mainmbo.getThisMboSet();
		String orgid = mainmbo.getString("orgid");
		String siteid = mainmbo.getString("siteid");
		// 取得票种简拼
		String S_ORDERTYPE = mainmbo.getString("S_ORDERTYPE");
		// 工作票编号
		String S_WOTKNUM = mainmbo.getString("S_WOTKNUM");
		//应用程序名
		String appName = mainmbo.getThisMboSet().getApp();
		// 获取系统时间
		String date = new SimpleDateFormat("yyMM").format(new Date());
		// 流水号格式化
		String abbreviation = null;
		if ("一级动火工作票".equalsIgnoreCase(S_ORDERTYPE)) {
			abbreviation = "CPF";
		} else if ("二级动火工作票".equalsIgnoreCase(S_ORDERTYPE)) {
			abbreviation = "CPF";
		} else {
			abbreviation = "CPW";
		}
		if (S_WOTKNUM.isEmpty()) {
			// 实例化自动编号方法类
			AutoDateSiteNum adsn = new AutoDateSiteNum(thisMboSet);
			// 获取自动编号
			int num = adsn.getNextAutoDateSiteNum(orgid, siteid, appName, null, abbreviation);
			mainmbo.setValue("S_WOTKNUM", abbreviation + date + num, 11L);
		}

	}

}