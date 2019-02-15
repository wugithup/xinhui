package com.shuto.mam.webclient.beans.operation.bhttedit;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class BhtteditBean2 extends AppBean {
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

	public boolean hasAuth() throws MXException, RemoteException {
		long tablesid = getMbo().getLong("BAOHUEDITID");
		String s = getMbo().getString("STATUS");
		String s1 = getMbo().getUserInfo().getPersonId();
		if (s1.equalsIgnoreCase("maxadmin"))
			return true;
		if ("NEW".equals(s))
			return true;
		MboSetRemote mbosetremote = getMbo().getMboSet(
				"$instance",
				"WFINSTANCE",
				"ownertable='BAOHUEDIT' and ownerid='" + tablesid
						+ "' and active = 1");
		if (!mbosetremote.isEmpty()) {
			String s2 = "ownerid='"
					+ tablesid
					+ "' and ownertable='BAOHUEDIT' and assignstatus='活动' and assigncode='"
					+ s1 + "'";
			MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode",
					"WFASSIGNMENT", s2);
			return !mbosetremote1.isEmpty();
		}
		return false;
	}

	public int ROUTEWF() throws MXException, RemoteException {
		if (!hasAuth()) {
			throw new MXApplicationException("system", "SYSROUTEWF2");
		}

		return super.ROUTEWF();
	}

	public int SAVE() throws MXException, RemoteException {
		MboRemote mbo = this.app.getAppBean().getMbo();

		String personid = mbo.getUserInfo().getPersonId();

		String createperson = mbo.getString("NEWPERSON");
		if (!hasAuth()) {
			throw new MXApplicationException("system", "SYSSAVE2");
		}

		if (("NEW".equals(getString("status")))
				&& (!createperson.equals(personid))
				&& (!"MAXADMIN".equals(personid))) {
			throw new MXApplicationException("system", "SYSSAVE2");
		}

		return super.SAVE();
	}
}