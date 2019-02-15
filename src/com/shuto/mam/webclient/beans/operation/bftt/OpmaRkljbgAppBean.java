package com.shuto.mam.webclient.beans.operation.bftt;

/**  
com.shuto.mam.webclient.beans.operation.bftt.OpmaRkljbgAppBean 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月31日 上午11:04:44
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import com.shuto.mam.app.expand.AutoDateSiteNum;
import com.shuto.mam.app.expand.AutoDateSiteNumHB;
import com.shuto.mam.app.expand.SiteLinkShort;
import com.shuto.mam.webclient.beans.base.CAppBean;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class OpmaRkljbgAppBean extends CAppBean {
	public synchronized boolean fetchRecordData() throws MXException, RemoteException {
		MboRemote localMboRemote = this.app.getAppBean().getMbo();
		String[] arrayOfString1 = { "PROFESSION", "TYPE", "DESCRIPTION", "BGQK", "ZYCD", "REMARK", "AQCS" };

		String[] arrayOfString2 = { "OPPERSON", "RATIFYPERSON" };
		if (localMboRemote != null) {
			String str = localMboRemote.getString("status");
			if ((!"新建".equals(str)) && (!"退回修改".equals(str)) && (!"待申请人指定执行人".equals(str))) {
				localMboRemote.setFlag(7L, true);
			}
			if ("待申请人指定执行人".equals(str)) {
				localMboRemote.setFieldFlag(arrayOfString2, 128L, true);
				localMboRemote.setFieldFlag(arrayOfString1, 7L, true);
			}
		}
		return super.fetchRecordData();
	}

	public int INSERT() throws MXException, RemoteException {
		super.INSERT();
		MboRemote localMboRemote = getMbo();
		MboSetRemote localMboSetRemote = localMboRemote.getMboSet("CREATEPERSON_PERSON");
		localMboRemote.setValue("dep", localMboSetRemote.getMbo(0).getString("DEPARTMENT"), 11L);

		return 1;
	}

	public int SAVE() throws MXException, RemoteException {
		MboRemote localMboRemote = getMbo();

		if ((localMboRemote.getString("RKLJNUM") == null) || ("".equals(localMboRemote.getString("RKLJNUM")))) {
			createRKLJNUM();
		}
		return super.SAVE();
	}

	public void createRKLJNUM() throws RemoteException, MXException {
		MboRemote localMboRemote = getMbo();
		String siteid = localMboRemote.getString("siteid");
		String orgid = localMboRemote.getString("orgid");
		String professionnum = localMboRemote.getString("PROFESSION");// 获取当前专业
		MboSetRemote professionMbo = localMboRemote.getMboSet("$PROFESSION", "PROFESSION",
				"siteid='" + siteid + "' and professionnum='" + professionnum + "'"); // 专业MBO
		String profession = professionMbo.getMbo(0).getString("professionabbr");
		AutoDateSiteNum localAutoDateSiteNum = new AutoDateSiteNum(localMboRemote.getThisMboSet());
		String appname = localMboRemote.getThisMboSet().getName();
		String date = new SimpleDateFormat("yyyyMM").format(new Date());// 获取系统时间
		int i = localAutoDateSiteNum.getNextAutoDateSiteNum(orgid, siteid, appname, profession);
		String str7 = siteid + "-" + "DZLJ" + "-" + profession + "-" + date + new DecimalFormat("000").format(i);
		localMboRemote.setValue("RKLJNUM", str7, 11L);
	}
}
