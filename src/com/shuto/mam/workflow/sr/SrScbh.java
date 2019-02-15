package com.shuto.mam.workflow.sr;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.icu.text.DecimalFormat;
import com.shuto.mam.app.expand.AutoDateSiteNum;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 * com.shuto.mam.workflow.sr.SrScbh 生成缺陷编号
 *
 * @author quanhw
 * @date 2018/10/28
 */

public class SrScbh implements ActionCustomClass{

	@Override
	public void applyCustomAction(MboRemote mainMbo, Object[] arg1)
			throws MXException, RemoteException {
		 String str1 = mainMbo.getString("orgid");
	        String str2 = mainMbo.getString("siteid");
	        String str3 = "SCQX";
	        String str4 = mainMbo.getString("S_QXLB");
	        String str5 = mainMbo.getString("T_TICKETID");
	        String str6 = mainMbo.getThisMboSet().getApp();
	        String str7 = new SimpleDateFormat("yyMM").format(new Date());
	        if ((!str4.isEmpty()) && (str5.isEmpty())) {
	            AutoDateSiteNum localAutoDateSiteNum = new AutoDateSiteNum(mainMbo.getThisMboSet());
	            int i = localAutoDateSiteNum.getNextAutoDateSiteNum(str1, str2, str6, null, str3);
	            mainMbo.setValue("T_TICKETID", str3 + str7 + new DecimalFormat("000").format(i), 11L);
		
	}
  }
}
