package com.shuto.mam.webclient.beans.operation.bftt;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.GregorianCalendar;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;

/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月31日
 * @since:控股台账
 */
public class TZ_SJLRAppBean extends AppBean {
	public void JKONLY() throws RemoteException, MXException {
		MboRemote localMboRemote = getMbo();
		String str1 = localMboRemote.getString("status");
		String str2 = localMboRemote.getString("RLML1");
		String str3 = localMboRemote.getString("RLML2");
		String str4 = localMboRemote.getString("BQ1");
		String str5 = localMboRemote.getString("BQ2");
		String str6 = localMboRemote.getString("S2");
		String str7 = localMboRemote.getString("S3");
		String str8 = localMboRemote.getString("S4");
		String str9 = localMboRemote.getString("S5");

		int i = ((str2 != null) && (!"".equals(str2)) && (str3 != null) && (!"".equals(str3)) && (str4 != null)
				&& (!"".equals(str4)) && (str5 != null) && (!"".equals(str5)) && (str6 != null) && (!"".equals(str6))
				&& (str6 != null) && (!"".equals(str7)) && (str7 != null) && (!"".equals(str7)) && (str8 != null)
				&& (!"".equals(str8)) && (str9 != null) && (!"".equals(str9))) ? 1 : 0;

		if (i != 0) {
			if (("辅控已提交".equals(str1)) || ("已提交".equals(str1)))
				localMboRemote.setValue("status", "已提交", 11L);
			else
				localMboRemote.setValue("status", "集控已提交", 11L);
		} else {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "", "指标有空项，不能提交！", 1);

			this.sessionContext.queueRefreshEvent();
		}
		super.SAVE();
	}

	public void FKONLY() throws RemoteException, MXException {
		MboRemote localMboRemote = getMbo();
		String str1 = localMboRemote.getString("status");
		String str2 = localMboRemote.getString("YAXH2");
		String str3 = localMboRemote.getString("YSXH1");
		String str4 = localMboRemote.getString("YAJL");
		String str5 = localMboRemote.getString("SHSFJL");
		String str6 = localMboRemote.getString("SHSXH");
		String str7 = localMboRemote.getString("RYJL");
		String str8 = localMboRemote.getString("S1");
		String str9 = localMboRemote.getString("QSZS");
		String str10 = localMboRemote.getString("QSNH");
		String str11 = localMboRemote.getString("RZSL");
		String str12 = localMboRemote.getString("QPCL");
		String str13 = localMboRemote.getString("QPJL");
		String str14 = localMboRemote.getString("MCKC");

		int i = ((str2 != null) && (!"".equals(str2)) && (str3 != null) && (!"".equals(str3)) && (str4 != null)
				&& (!"".equals(str4)) && (str5 != null) && (!"".equals(str5)) && (str6 != null) && (!"".equals(str6))
				&& (str7 != null) && (!"".equals(str7)) && (str8 != null) && (!"".equals(str8)) && (str9 != null)
				&& (!"".equals(str9)) && (str10 != null) && (!"".equals(str10)) && (str11 != null)
				&& (!"".equals(str11)) && (str12 != null) && (!"".equals(str12)) && (str13 != null)
				&& (!"".equals(str13)) && (str14 != null) && (!"".equals(str14))) ? 1 : 0;

		if (i != 0) {
			if (("集控已提交".equals(str1)) || ("已提交".equals(str1)))
				localMboRemote.setValue("status", "已提交", 11L);
			else
				localMboRemote.setValue("status", "辅控已提交", 11L);
		} else {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "", "指标有空项，不能提交！", 1);

			this.sessionContext.queueRefreshEvent();
		}
		SAVE();
	}

	public void QUIT() throws RemoteException, MXException {
		MboRemote localMboRemote = getMbo();
		localMboRemote.setValue("status", "新建", 11L);
		SAVE();
	}

	public int INSERT() throws MXException, RemoteException {
		int i = super.INSERT();
		MboRemote mbo = this.app.getAppBean().getMbo();
		MboSetRemote localMboSetRemote = mbo.getMboSet("$TZ_SJLR", "TZ_SJLR",
				"siteid='" + mbo.getString("siteid") + "'");
		localMboSetRemote.setOrderBy("LRDATE desc");
		if (localMboSetRemote.count() > 0) {
			Date localDate1 = new Date();
			Date localDate2 = localMboSetRemote.getMbo(0).getDate("LRDATE");
			GregorianCalendar localGregorianCalendar = new GregorianCalendar();
			localGregorianCalendar.setTime(localDate2);
			localGregorianCalendar.add(5, 1);
			if (localGregorianCalendar.getTime().getTime() <= localDate1.getTime()) {
				localMboSetRemote.close();
				getMbo().setValue("lrdate", localGregorianCalendar.getTime(), 11L);
				getMbo().setValue("status", "新建", 11L);
				SAVE();
				refreshTable();
			} else {
				Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "提示", "当天记录已存在！", 1);

				this.sessionContext.queueRefreshEvent();
			}
		}
		return i;
	}
}