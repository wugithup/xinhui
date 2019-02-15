package com.shuto.mam.workflow.operation;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.shuto.mam.app.expand.AutoDateSiteNum;
import com.shuto.mam.app.expand.SiteLinkShort;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;

public class ActionTsdCode implements ActionCustomClass {
	public void applyCustomAction(MboRemote mbo, Object[] arg1) throws MXException, RemoteException {
		try {
			MboSetRemote thisMboSet = mbo.getThisMboSet();

			String prepro = mbo.getString("zy");

			String siteid = mbo.getString("siteid");

			String orgid = mbo.getString("orgid");

			SiteLinkShort sls = new SiteLinkShort(thisMboSet);

			AutoDateSiteNum adsn = new AutoDateSiteNum(thisMboSet);

			String ownerTable = mbo.getThisMboSet().getApp();;

			// String ownerAttributeName = "TSDBM-" + prepro;
			Date imaDate = MXServer.getMXServer().getDate();
			int num = adsn.getNextAutoDateSiteNum(orgid, siteid, ownerTable, prepro);
			// String otpType = mbo.getString("ZY");
			String optNumStr = "";

			optNumStr = "TSDBM-" + prepro + "-" + new SimpleDateFormat("yyyyMM").format(imaDate) + "-"
					+ new DecimalFormat("000").format(num);
			mbo.setValue("TSDBM", optNumStr, 11L);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
	}
}