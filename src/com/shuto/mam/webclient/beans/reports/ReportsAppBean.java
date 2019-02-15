package com.shuto.mam.webclient.beans.reports;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import com.shuto.mam.app.reports.ReportsDataMboRemote;

/**
 * com.shuto.mam.webclient.beans.reports.ReportsAppBean 华东大区
 *
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年8月11日 上午10:52:38
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class ReportsAppBean extends AppBean {

	public void TBSJ() throws RemoteException, MXException {
		MboRemote mbo = this.app.getAppBean().getMbo();

		//System.out.println("开始运行 生成报表》》》》》》》》》》》》》》" + mbo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// Date date = this.mxserver.getDate();
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			cal.setTime(date);
			String where = "TOBEADD=1 and TONGJITYPE in ('日')";
			if (cal.get(Calendar.DATE) == 1) {// 如果是一号
				where = "TOBEADD=1 and TONGJITYPE in ('日','月')";
				if (cal.get(Calendar.MONTH) == 0) {// 如果是一月
					where = "TOBEADD=1 and TONGJITYPE in ('日','月','年')";
				}
			}
			MboSetRemote reportsMboSet = mbo.getMboSet("$REPORTS", "REPORTS",
					where);
			// reportsMboSet.setWhere(where);
			reportsMboSet.reset();
			for (int i = 0; i < reportsMboSet.count(); i++) {
				MboRemote reportsMbo = reportsMboSet.getMbo(i);
				String reportsid = reportsMbo.getString("reportsid");
				String reportname = reportsMbo.getString("reportname");
				String tongjiType = reportsMbo.getString("TONGJITYPE");
				//System.out.println("生成报表id：  " + reportsid);
				// ZhibiaoTuuyou zt = new ZhibiaoTuuyou();
				// Date formatDate = zt.formatDate(date, tongjiType, null);
				MboSetRemote reportsdataMboSet = mbo.getMboSet("$REPORTSDATA",
						"REPORTSDATA");
				SqlFormat sql = new SqlFormat(" MKDATE=:1 and REPORTSID=:2");
				sql.setObject(1, "REPORTSDATA", "MKDATE", sdf.format(date));
				sql.setObject(2, "REPORTSDATA", "REPORTSID", reportsid);
				reportsdataMboSet.setWhere(sql.format());
				reportsdataMboSet.reset();
				//System.out.println("生成报表 sql:" + sql.format());
				if (reportsdataMboSet.isEmpty()) {
					ReportsDataMboRemote reportsdataMbo = (ReportsDataMboRemote) reportsdataMboSet
							.add();
					reportsdataMbo.setValue("REPORTSID", reportsid,
							DataBean.ATTR_READONLY);
					reportsdataMbo.setValue("MKDATE", sdf.format(date),
							DataBean.ATTR_READONLY);
					reportsdataMbo.setValue("SITEID", "FYHR000",
							DataBean.ATTR_READONLY);
					reportsdataMbo.setValue("ORGID", "CRPH500EC",
							DataBean.ATTR_READONLY);
					reportsdataMbo.makeReport(false);
//					System.out.println("生成报表 makeReport:ConReports.reportsid="
//							+ reportsid + ",mkdate=" + sdf.format(date) + "\n"
//							+ "reportsdataMbo.siteid="
//							+ reportsdataMbo.getString("SITEID"));
					reportsdataMboSet.save();
				}
				reportsdataMboSet.close();

				if ("日".equals(tongjiType)) {
				} else if ("月".equals(tongjiType)) {
				} else if ("年".equals(tongjiType)) {

				}

			}

			reportsMboSet.close();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				String remark = reportMbo.getString("REMARK");			//备注
				System.out.println(remark);
				if (!"".equals(dialogID)) {
					this.clientSession.setAttribute("rqreportname", rqreportname);
					this.clientSession.setAttribute("rqreportnum", rqreportnum);
					this.clientSession.setAttribute("remark", remark);
					this.clientSession.loadDialog(dialogID);
					return 0;
				}
				StringBuffer url = new StringBuffer(MXServer.getMXServer().getProperty("mxe.runqian.url"));
				url.append("/" + rqreportname.toLowerCase() + ".rpx");
				MboSetRemote rqreportset = mbo.getMboSet("$RQREPORTPARAM", "RQREPORTPARAM",
						"rqreportnum = '" + rqreportnum + "'");
				if (!rqreportset.isEmpty()) {
					for (int i = 0; i < rqreportset.count(); i++) {
						url.append("&");
						url.append(rqreportset.getMbo(i).getString("PARAMNAME").toLowerCase());
						url.append("=");
						url.append(mbo.getString(rqreportset.getMbo(i).getString("PARAMVALUE")));
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
