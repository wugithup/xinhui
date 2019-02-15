package com.shuto.mam.app.reports;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.app.reports.ConReports 华东大区 阜阳项目 报表自动生成任务
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月18日 下午3:48:14
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class ConReports extends SimpleCronTask {
	MXServer mxserver;
	UserInfo userInfo;

	@Override
	public void init() throws MXException {
		// TODO Auto-generated method stub
		super.init();
		try {
			this.mxserver = MXServer.getMXServer();
			this.userInfo = getRunasUserInfo();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void cronAction() {
		System.out.println("定时任务生成报表 开始》》》》》》》》》》》》》》");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = this.mxserver.getDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String where = "TOBEADD=1 and TONGJITYPE in ('日')";
			if (cal.get(Calendar.DATE) == 1) {// 如果是一号
				where = "TOBEADD=1 and TONGJITYPE in ('日','月')";
				if (cal.get(Calendar.MONTH) == 0) {// 如果是一月
					where = "TOBEADD=1 and TONGJITYPE in ('日','月','年')";
				}
			}
			MboSetRemote reportsMboSet = this.mxserver.getMboSet("REPORTS",
					userInfo);
			reportsMboSet.setWhere(where);
			reportsMboSet.reset();
			for (int i = 0; i < reportsMboSet.count(); i++) {
				MboRemote reportsMbo = reportsMboSet.getMbo(i);
				String reportsid = reportsMbo.getString("reportsid");
				String reportname = reportsMbo.getString("reportname");
				String tongjiType = reportsMbo.getString("TONGJITYPE");
				System.out.println("定时任务生成报表 id：  " + reportsid);
				ZhibiaoTuuyou zt = new ZhibiaoTuuyou();
				Date formatDate = zt.formatDate(date, tongjiType, null);
				MboSetRemote reportsdataMboSet = this.mxserver.getMboSet(
						"REPORTSDATA", userInfo);
				SqlFormat sql = new SqlFormat(" MKDATE=:1 and REPORTSID=:2");
				sql.setObject(1, "REPORTSDATA", "MKDATE",
						sdf.format(formatDate));
				sql.setObject(2, "REPORTSDATA", "REPORTSID", reportsid);
				reportsdataMboSet.setWhere(sql.format());
				reportsdataMboSet.reset();
				System.out.println("定时任务生成报表 sql:" + sql.format());
				if (reportsdataMboSet.isEmpty()) {
					ReportsDataMboRemote reportsdataMbo = (ReportsDataMboRemote) reportsdataMboSet
							.add();
					reportsdataMbo.setValue("REPORTSID", reportsid,
							DataBean.ATTR_READONLY);
					reportsdataMbo.setValue("MKDATE", formatDate,
							DataBean.ATTR_READONLY);
					reportsdataMbo.setValue("SITEID", "FYHR000",
							DataBean.ATTR_READONLY);
					reportsdataMbo.setValue("ORGID", "CRPH500EC",
							DataBean.ATTR_READONLY);
					reportsdataMbo.makeReport(false);
					System.out
							.println("定时任务生成报表 makeReport:ConReports.reportsid="
									+ reportsid
									+ ",mkdate="
									+ sdf.format(formatDate)
									+ ",userInfo.siteid="
									+ userInfo.getInsertSite()
									+ ",personid="
									+ userInfo.getPersonId()
									+ "\n"
									+ "reportsdataMbo.siteid="
									+ reportsdataMbo.getString("SITEID"));
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

}
