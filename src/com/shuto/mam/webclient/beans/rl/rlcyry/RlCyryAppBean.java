package com.shuto.mam.webclient.beans.rl.rlcyry;

/**
 com.shuto.mam.webclient.beans.rl.rlcyry.RlCyryAppBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月30日 上午11:42:25
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0
 */
import java.rmi.RemoteException;
import java.util.ArrayList;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import edu.emory.mathcs.backport.java.util.Collections;

public class RlCyryAppBean extends AppBean {
	public void random() throws RemoteException, MXException {
		super.SAVE();
		MboRemote AppMbo = this.app.getAppBean().getMbo();
		MboSetRemote DetailSet = AppMbo.getMboSet("PERSONGROUPTEAM");
		MboRemote DetailMbo = null;
		if (!DetailSet.isEmpty()) {
			for (int a = 0; a < DetailSet.count(); a++) {
				DetailMbo = DetailSet.getMbo(a);
				DetailMbo.setValue("CCXH", 0, 11L);
				DetailMbo.setValue("ISCC", false, 11L);
			}
			super.SAVE();
		}
		MboSetRemote DetailDbSet = AppMbo.getMboSet("PERSONGROUPTEAMDB");
		MboRemote DetailDbMbo = null;
		if (!DetailDbSet.isEmpty()) {
			ArrayList CcXh = new ArrayList();
			for (int b = 1; b <= DetailDbSet.count(); b++) {
				CcXh.add(Integer.valueOf(b));
			}
			Collections.shuffle(CcXh);
			for (int c = 0; c < DetailDbSet.count(); c++) {
				DetailDbMbo = DetailDbSet.getMbo(c);
				DetailDbMbo.setValue("CCXH",
						((Integer) CcXh.get(c)).intValue(), 11L);
			}
			super.SAVE();
		}
	}

	public int OPENREPORT() {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			MboSetRemote reportSet = null;
			if (mbo == null) {
				String mboname = this.app.getAppBean().getMboName();
				MboSetRemote mboSet = MXServer.getMXServer().getMboSet(mboname,
						MXServer.getMXServer().getSystemUserInfo());
				mbo = mboSet.getMbo(0);
			}
			reportSet = mbo.getMboSet("RQREPORT");
			if (reportSet.getMbo(0) != null && reportSet.getMbo(1) == null) {
				MboRemote reportMbo = reportSet.getMbo(0);
				String dialogID = reportMbo.getString("DIALOGID");
				String rqreportname = reportMbo.getString("RQREPORTNAME");// 报表名
				String rqreportnum = reportMbo.getString("rqreportnum");// 报表编号
				String remark = reportMbo.getString("REMARK"); // 备注
				System.out.println(remark);
				if (!"".equals(dialogID)) {
					this.clientSession.setAttribute("rqreportname",
							rqreportname);
					this.clientSession.setAttribute("rqreportnum", rqreportnum);
					this.clientSession.setAttribute("remark", remark);
					this.clientSession.loadDialog(dialogID);
					return 0;
				}
				StringBuffer url = new StringBuffer(MXServer.getMXServer()
						.getProperty("mxe.runqian.url"));
				url.append("/" + rqreportname.toLowerCase() + ".rpx");
				MboSetRemote rqreportset = mbo.getMboSet("$RQREPORTPARAM",
						"RQREPORTPARAM", "rqreportnum = '" + rqreportnum + "'");
				if (!rqreportset.isEmpty()) {
					for (int i = 0; i < rqreportset.count(); i++) {
						url.append("&");
						url.append(rqreportset.getMbo(i).getString("PARAMNAME")
								.toLowerCase());
						url.append("=");
						url.append(mbo.getString(rqreportset.getMbo(i)
								.getString("PARAMVALUE")));
					}

				}
				rqreportset.close();
				this.app.openURL(url.toString(), true);
			} else if (reportSet.count() > 1) {
				this.clientSession.loadDialog("RQREPORT1");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return 1;
	}
}