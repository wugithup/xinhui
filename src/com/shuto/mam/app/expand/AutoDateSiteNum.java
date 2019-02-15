package com.shuto.mam.app.expand;

import java.rmi.RemoteException;
import java.util.Calendar;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.server.MXServer;
import psdi.util.MXException;

public class AutoDateSiteNum {
	private MboSetRemote msr = null;

	public AutoDateSiteNum() {
	}

	public AutoDateSiteNum(MboSetRemote msr) {
		this.msr = msr;
	}

	public int getNextAutoDateSiteNum(String orgid, String siteid, String appname) throws RemoteException, MXException {

		return getNextAutoDateSiteNum(orgid, siteid, appname, null, null);

	}

	public int getNextAutoDateSiteNum(String orgid, String siteid, String appname, String profession)
			throws RemoteException, MXException {

		return getNextAutoDateSiteNum(orgid, siteid, appname, profession, null);

	}

	public int getNextAutoDateSiteNum(String orgid, String siteid, String appname, String profession, String type)
			throws RemoteException, MXException {
		if (type == null) {
			type = "0";
		}
		if (profession == null) {
			profession = "0";
		}
		int num = 1;
		Calendar cal = Calendar.getInstance();
		cal.setTime(MXServer.getMXServer().getDate());
		MboSetRemote mboSet = MXServer.getMXServer().getMboSet("AUTODATESITENUM",
				MXServer.getMXServer().getSystemUserInfo());
		String sql = "orgid = :1 and siteid =:2 and appname = :3 and year =:4 and month=:5 and profession=:6 and type=:7";
		SqlFormat sqf = new SqlFormat(sql);
		sqf.setObject(1, "AUTODATESITENUM", "ORGID", orgid);
		sqf.setObject(2, "AUTODATESITENUM", "SITEID", siteid);
		sqf.setObject(3, "AUTODATESITENUM", "APPNAME", appname);
		sqf.setInt(4, cal.get(Calendar.YEAR));
		sqf.setInt(5, cal.get(Calendar.MONTH) + 1);
		sqf.setObject(6, "AUTODATESITENUM", "PROFESSION", profession);
		sqf.setObject(7, "AUTODATESITENUM", "TYPE", type);
		mboSet.setWhere(sqf.format());
		mboSet.reset();
		if (mboSet.count() == 0) {
			MboRemote autoMbo = mboSet.add();
			autoMbo.setValue("SITEID", siteid);
			autoMbo.setValue("ORGID", orgid);
			autoMbo.setValue("APPNAME", appname, 11l);
			autoMbo.setValue("YEAR", cal.get(Calendar.YEAR), 11l);
			autoMbo.setValue("MONTH", cal.get(Calendar.MONTH) + 1, 11l);
			autoMbo.setValue("PROFESSION", profession, 11l);
			autoMbo.setValue("TYPE", type, 11l);
			autoMbo.setValue("NUM", num, 11l);
		} else {
			MboRemote mbo = mboSet.getMbo(0);
			num = mbo.getInt("NUM");
			mbo.setValue("NUM", ++num);
		}
		mboSet.save();
		mboSet.close();
		return num;
	}

}
