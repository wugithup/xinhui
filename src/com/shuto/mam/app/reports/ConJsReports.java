package com.shuto.mam.app.reports;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.reports.ConJsReports 华东大区 阜阳项目 报表自动计算定时任务
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月18日 下午3:44:36
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class ConJsReports extends SimpleCronTask {
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
		try {
			this.mxserver = MXServer.getMXServer();
			this.userInfo = getRunasUserInfo();

			// ReportsDataMboRemote mbo=(ReportsDataMboRemote)getMbo();
			// Date mkdate = mbo.getDate("MKDATE");
			// if(mkdate==null||mbo.isNull("REPORTSID")){
			// throw new MXApplicationException("", "请先选择报表和日期!");
			// }
			// mbo.makeReport(true);

			ReportsDataMboSetRemote REPORTSZBSet = (ReportsDataMboSetRemote) this.mxserver.getMboSet("REPORTSDATA",
					this.userInfo);
			Date cdate = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(cdate);
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day - 3);
			String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
			System.out.println(dayBefore + "#############" + "+++++++++++++++++++++++");
			REPORTSZBSet.setWhere("REPORTSID='270' and to_char(MKDATE,'yyyy-MM-dd')>='" + dayBefore + "+'");
			REPORTSZBSet.reset();
			System.out.println(REPORTSZBSet.count() + "============================");
			for (int i = 0; i < REPORTSZBSet.count(); i++) {

				ReportsDataMboRemote mbo = (ReportsDataMboRemote) REPORTSZBSet.getMbo(i);
				Date mkdate = mbo.getDate("MKDATE");
				if (mkdate != null || !mbo.isNull("REPORTSID")) {
					mbo.makeReport(true);
					System.out.println("+++++++++++++++++++++++");
				}

			}
			REPORTSZBSet.save();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}