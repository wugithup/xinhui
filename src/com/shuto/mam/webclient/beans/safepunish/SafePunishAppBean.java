package com.shuto.mam.webclient.beans.safepunish;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.controller.Utility;

/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月18日
 * @since:控股台账
 */
public class SafePunishAppBean extends CAppBean {
	public int INSERT() throws MXException, RemoteException {
		int i = super.INSERT();
		MboRemote mboremote = this.app.getAppBean().getMbo();
		mboremote.setValue("APPTYPE", "考核", 2L);
		return i;
	}

	public void JSJF() throws MXException, RemoteException {
		MboRemote mbo = this.app.getAppBean().getMbo();
		String msg = "请选择被考核人再进行计算！";
		if (checkJF(mbo, msg)) {
			int sumnum = 0;
			MboSetRemote msrSet = mbo.getMboSet("TZ_ST_SAFEREWARD");
			for (int i = 0; msrSet.getMbo(++i) != null;) {
				int tempnum = msrSet.getMbo(i).getInt("KHRJF");
				sumnum += tempnum;
			}
			mbo.setFieldFlag("ZJF", 7L, false);
			mbo.setValue("ZJF", sumnum);
			super.save();
			Utility.showMessageBox(this.clientSession.getCurrentEvent(), "", "计算完成 ", 1);
		}
	}

	public void QCJF() throws MXException, RemoteException {
		MboRemote mbo = this.app.getAppBean().getMbo();
		String msg = "请选择被考核人再进行清空积分！";
		if (checkJF(mbo, msg)) {
			int num = 0;
			MboSetRemote msrSet = mbo.getMboSet("TZ_ST_SAFEREWARD");
			for (int i = 0; msrSet.getMbo(++i) != null;) {
				msrSet.getMbo(i).setValue("KHRJF", num);
				msrSet.getMbo(i).setValue("ZJF", num);
			}
			super.save();
			Utility.showMessageBox(this.clientSession.getCurrentEvent(), "", "清空积分完成 ", 1);
		}
	}

	public boolean checkJF(MboRemote mbo, String msg) throws RemoteException, MXException {
		boolean b = true;
		String selPerson = mbo.getString("SELPERSON");
		if ("".equals(selPerson)) {
			b = false;
			throw new MXApplicationException("configure", "BlankMsg", new String[] { msg });
		}
		return b;
	}
}