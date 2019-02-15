package com.shuto.mam.workflow.operation;

import com.shuto.mam.app.expand.AutoDateSiteNum;
import com.shuto.mam.app.expand.SiteLinkShort;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;

public class ActionTsdlxdCode implements ActionCustomClass {
	public void applyCustomAction(MboRemote paramMboRemote, Object[] paramArrayOfObject) {
		try {
			MboSetRemote localMboSetRemote = paramMboRemote.getThisMboSet();

			String profession = paramMboRemote.getString("TSD_PROFESSION");

			String orgid = paramMboRemote.getString("orgid");

			String siteid = paramMboRemote.getString("siteid");

			String appname = localMboSetRemote.getName();

			AutoDateSiteNum localAutoDateSiteNum = new AutoDateSiteNum(localMboSetRemote);

			Date localDate = MXServer.getMXServer().getDate();
			int i = localAutoDateSiteNum.getNextAutoDateSiteNum(orgid, siteid, appname, "DQ");

			String str7 = "";

			str7 = paramMboRemote.getString("TSD_PROFESSION_ALNDOMAIN.DESCRIPTION") + "(" + siteid + ")"
					+ new SimpleDateFormat("yyyyMM").format(localDate) + new DecimalFormat("000").format(i);
			paramMboRemote.setValue("tsd_num", str7, 11L);
		} catch (RemoteException localRemoteException) {
			localRemoteException.printStackTrace();
		} catch (MXException localMXException) {
			localMXException.printStackTrace();
		}
	}
}