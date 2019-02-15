package com.shuto.mam.webclient.beans.operation.bftt;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.shuto.mam.app.expand.AutoDateSiteNum;
import com.shuto.mam.app.expand.SiteLinkShort;

import psdi.mbo.MboRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月21日
 * @since:控股台账
 */
// com.shuto.mam.app.crontask.interfacelog.InterfacelogSet
public class OpamdopAppBean extends AppBean {
	public synchronized boolean fetchRecordData() throws MXException, RemoteException {
		MboRemote mbo = getMbo();
		if (mbo != null) {
			String status = mbo.getString("status");

			if ((!status.equals("新建")) && (!status.equals("退回修改"))) {
				mbo.setFieldFlag("NAME", 7L, true);
				mbo.setFieldFlag("YONGTU", 7L, true);
				mbo.setFieldFlag("LOCATION", 7L, true);
				mbo.setFieldFlag("ASSETNUM", 7L, true);
				mbo.setFieldFlag("TZ_OPAMDOPKGTYPE", 7L, true);
				mbo.setFieldFlag("TZ_OPAMDOPKGZY", 7L, true);
				mbo.setFieldFlag("ZYCD", 7L, true);
				mbo.setFieldFlag("TIME02", 7L, true);
				mbo.setFieldFlag("STRING03", 7L, true);
				mbo.setFieldFlag("STRING02", 7L, true);
				mbo.setFieldFlag("TIME03", 7L, true);
				mbo.setFieldFlag("TIME04", 7L, true);
				mbo.setFieldFlag("PERSONID02", 7L, true);
				mbo.setFieldFlag("PERSONID04", 7L, true);
				mbo.setFieldFlag("TIME01", 7L, true);
				mbo.setFieldFlag("STRING01", 7L, true);

				mbo.setFieldFlag("ZXPROFESS", 7L, true);
			}
			if (!status.equals("待执行专业主管审核")) {
				mbo.setFieldFlag("ZXPERSON", 7L, true);
			}

			if (status.equals("待执行专业主管审核")) {
				mbo.setFieldFlag("ZXPERSON", 128L, true);
			}
			if (!status.equals("已退出")) {
				mbo.setFieldFlag("STRING30_02", 7L, true);
				mbo.setFieldFlag("STRING30_01", 7L, true);
			}
			if (status.equals("已退出")) {
				mbo.setFieldFlag("STRING30_02", 11L, true);
				mbo.setFieldFlag("STRING30_01", 11L, true);
			}

		}

		return super.fetchRecordData();
	}

	public int SAVE() throws MXException, RemoteException {
		MboRemote mbo = getMbo();

		if ((mbo.getString("TZ_OPAMDOPKGCODE") == null) || ("".equals(mbo.getString("TZ_OPAMDOPKGCODE")))) {
			createOpamdopCode();
		}
		return super.SAVE();
	}

	public void createOpamdopCode() throws RemoteException, MXException {
		MboRemote mbo = getMbo();
		String siteid = mbo.getString("siteid");
		String orgid = mbo.getString("orgid");
		SiteLinkShort sls = new SiteLinkShort(mbo.getThisMboSet());
		String siteidShortEn = sls.getSiteEnShort(mbo.getString("SITEID"));
		AutoDateSiteNum adsn = new AutoDateSiteNum(mbo.getThisMboSet());
		Date imaDate = MXServer.getMXServer().getDate();
		int num = adsn.getNextAutoDateSiteNum(orgid, siteid, mbo.getThisMboSet().getApp());
		String opamdopCodeStr = "";
		opamdopCodeStr = siteidShortEn + "-" + "BHTT" + "-" + new SimpleDateFormat("yyyyMM").format(imaDate)
				+ new DecimalFormat("0000").format(num);
		mbo.setValue("TZ_OPAMDOPKGCODE", opamdopCodeStr, 11L);
	}
}