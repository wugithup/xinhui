/**
 *
 */
package com.shuto.mam.webclient.beans.statistics;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.WebClientEvent;

import com.shuto.mam.sis.service.impl.FormulaServiceImpl;

/**
 * @author Administrator
 *
 */
public class ZhtjjgcxAppBean extends AppBean {
	private static String DAILYREPORT = "生产日报";
	private static String MONTHREPORT = "生产月报";
	private static String QUARTERREPORT = "生产季报";
	private static String YEARREPORT = "生产年报";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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

	public int INSERT() {
		try {
			super.INSERT();
			String appname = this.app.getApp();
			MboRemote mbo = this.getMbo();
			MboSetRemote reporttargets = null;
			MboRemote reporttarget = null;
			MboSetRemote reporttargetdatas = getMbo().getMboSet(
					"DAILYREPORTTARGETDATA");
			MboRemote reporttargetdata = null;
			String reportnum = mbo.getString("num");
			mbo.setValue("createuser", mbo.getThisMboSet().getUserInfo()
					.getPersonId());
			// 生产日报
			if ("zhtjscrb".equals(appname)) {
				mbo.setValue("reporttype", DAILYREPORT);
				reporttargets = getMbo()
						.getMboSet(
								"$reporttargets",
								"DAILYREPORTTARGET",
								"orgid=:orgid and reportnum in(select num from DAILYREPORT where orgid=:orgid and reporttype='"
										+ DAILYREPORT + "') and active=1");

				// 生产月报
			} else if ("zhtjscyb".equals(appname)) {
				mbo.setValue("reporttype", MONTHREPORT);
				reporttargets = getMbo()
						.getMboSet(
								"$reporttargets",
								"DAILYREPORTTARGET",
								"orgid=:orgid and reportnum in(select num from DAILYREPORT where orgid=:orgid and reporttype='"
										+ MONTHREPORT
										+ "') and active=1 and targetident=0");
			}
			if (!reporttargets.isEmpty()) {
				for (int i = 0; i < reporttargets.count(); i++) {
					reporttarget = reporttargets.getMbo(i);
					reporttargetdata = reporttargetdatas.add();
					reporttargetdata.setValue("reportdatanum", reportnum);
					reporttargetdata.setValue("reporttargetnum",
							reporttarget.getString("num"));
					reporttargetdata.setValue("description",
							reporttarget.getString("name"));
					reporttargetdata.setValue("unit",
							reporttarget.getString("unit"));
					reporttargetdata.setValue("type",
							reporttarget.getString("type"));
					reporttargetdata.setValue("ordernum",
							reporttarget.getString("ordernum"));
					reporttargetdata.setValue("jz",
							reporttarget.getString("jz"));
					reporttargetdata.setValue("targettype",
							reporttarget.getString("targettype"));
					reporttargetdata.setValue("scale",
							reporttarget.getInt("scale"));
					// 设置缺省值
					reporttargetdata.setValue("value",
							reporttarget.getDouble("defuvalue"));
				}
				super.save();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 保存
	 */
	public int SAVE() throws MXException, RemoteException {
		String appname = this.app.getApp();
		MboRemote mbo = this.getMbo();
		// 生产日报
		if ("zhtjscrb".equals(appname)) {
			MboSetRemote reporttargetdatas = mbo
					.getMboSet("DAILYREPORTTARGETDATA");
			Date reportdate = mbo.getDate("reportdate");
			MboSetRemote dailyreportdatas = mbo
					.getMboSet("$DAILYREPORTDATA", "DAILYREPORTDATA",
							"reporttype='生产日报' and reportdate=:reportdate and num<>:num");
			if (!dailyreportdatas.isEmpty()) {
				throw new MXApplicationException("zhtj", sdf.format(reportdate)
						+ "的日报已存在,不能重复创建!");
			}
			// 设置报表统计日期
			if (!reporttargetdatas.isEmpty()) {
				for (int i = 0; i < reporttargetdatas.count(); i++) {
					reporttargetdatas.getMbo(i).setValue("reportdate",
							reportdate);
				}
			}
			// 生产月报
		} else if ("zhtjscyb".equals(appname)) {
			Date enddate = mbo.getDate("enddate");
			String year = null;
			String month = null;
			if (enddate != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(enddate);
				year = cal.get(Calendar.YEAR) + "";
				month = (cal.get(Calendar.MONTH) + 1) + "";
				mbo.setValue("year", year);
				mbo.setValue("month", month);
				MboSetRemote dailyreportdatas = mbo
						.getMboSet(
								"$DAILYREPORTDATA",
								"DAILYREPORTDATA",
								"reporttype='生产月报' and year=:year and month=:month and ynfb=0 and ynjd=0 and num<>:num");
				if (!dailyreportdatas.isEmpty()) {
					throw new MXApplicationException("zhtj", year + "年" + month
							+ "月份的月报已存在,不能重复创建!");
				}
			}
		}
		return super.SAVE();
	}

	/**
	 * 删除
	 */
	public int DELETE() throws RemoteException, MXException {
		MboRemote mbo = this.getMbo();
		super.DELETE();
		WebClientEvent event = clientSession.getCurrentEvent();
		int msgRet = event.getMessageReturn();
		if (msgRet == 8) {
			MboSetRemote reporttargetdatas = mbo
					.getMboSet("DAILYREPORTTARGETDATA");
			if (!reporttargetdatas.isEmpty()) {
				reporttargetdatas.deleteAll();
				reporttargetdatas.save();
			}
		}
		return 1;
	}

	/**
	 * 获取日报取SIS值得指标结果
	 *
	 * @throws MXApplicationException
	 * @throws Exception
	 */
	public void STATISSIS() throws MXApplicationException {
		try {
			super.SAVE();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String type = getMbo().getString("reporttype");
			String orgid = getMbo().getString("orgid");
			String reportnum = getMbo().getString("num");
			String reportdate = null;
			if (getMbo().getDate("reportdate") != null) {
				reportdate = sdf.format(getMbo().getDate("reportdate"));
			}
			FormulaServiceImpl formulaService = new FormulaServiceImpl(orgid);
			String formula = "";
			if (DAILYREPORT.equals(type)) {
				formula = "math.statisDailyReportSis('" + orgid + "','"
						+ reportnum + "','" + reportdate + "')";
			}
			formulaService.getByFormulaAsMap(formula);
			super.SAVE();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MXApplicationException("zhtj", "网络故障,请稍后再试!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new MXApplicationException("zhtj", "网络故障,请稍后再试!");
		}
	}

	/**
	 * 报表计算
	 *
	 * @throws MXApplicationException
	 * @throws Exception
	 */
	public void STATIS() throws MXApplicationException {
		try {
			MboSetRemote lrzs = getMbo().getMboSet("#lrz",
					"DAILYREPORTTARGETDATA",
					"reportdatanum=:num and type='录入值' and value is null");
			if (!lrzs.isEmpty()) {
				throw new MXApplicationException("zhtj", "请先录入值后再进行报表计算!");
			}
			super.SAVE();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String type = getMbo().getString("reporttype");
			String orgid = getMbo().getString("orgid");
			String reportnum = getMbo().getString("num");
			boolean ynfb = getMbo().getBoolean("ynfb");
			String reportdate = null;
			String datefrom = null;
			String dateto = null;
			if (getMbo().getDate("reportdate") != null) {
				reportdate = sdf.format(getMbo().getDate("reportdate"));
			}
			if (getMbo().getDate("startdate") != null) {
				datefrom = sdf.format(getMbo().getDate("startdate"));
			}
			if (getMbo().getDate("enddate") != null) {
				dateto = sdf.format(getMbo().getDate("enddate"));
			}
			FormulaServiceImpl formulaService = new FormulaServiceImpl(orgid);
			String formula = "";
			if (DAILYREPORT.equals(type)) {
				formula = "math.statisDailyReport('" + orgid + "','"
						+ reportnum + "','" + reportdate + "')";
			} else if (MONTHREPORT.equals(type)) {
				if (ynfb) {
					formula = "math.statisReport('" + orgid + "','" + reportnum
							+ "','" + datefrom + "','" + dateto + "','" + type
							+ "',1)";
				} else {
					formula = "math.statisReport('" + orgid + "','" + reportnum
							+ "','" + datefrom + "','" + dateto + "','" + type
							+ "',0)";
				}
			} else if (YEARREPORT.equals(type)) {
				if (ynfb) {
					formula = "math.statisReport('" + orgid + "','" + reportnum
							+ "','" + datefrom + "','" + dateto + "','" + type
							+ "',1)";
				} else {
					formula = "math.statisReport('" + orgid + "','" + reportnum
							+ "','" + datefrom + "','" + dateto + "','" + type
							+ "',0)";
				}
			}
			formulaService.getByFormulaAsMap(formula);
			super.SAVE();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MXApplicationException("zhtj", "网络故障,请稍后再试!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new MXApplicationException("zhtj", "网络故障,请稍后再试!");
		}
	}

	/**
	 * 生成报表SQL
	 */
	public void REPORTSQL() {
		try {
			MboSetRemote dailyreporttargets = getMbo().getMboSet(
					"DAILYREPORTTARGETDATA");
			dailyreporttargets.setOrderBy("ordernum");
			StringBuffer sql = new StringBuffer("select reportdatanum" + "\r\n");
			if (!dailyreporttargets.isEmpty()) {
				MboRemote reporttarget = null;
				for (int i = 0; i < dailyreporttargets.count(); i++) {
					reporttarget = dailyreporttargets.getMbo(i);
					sql.append(",max(case when reporttargetnum='"
							+ reporttarget.getString("reporttargetnum")
							+ "' then value end) "
							+ reporttarget.getString("reporttargetnum")
							+ "\r\n");
				}
				sql.append(" from dailyreporttargetdata where reportdatanum='"
						+ getMbo().getString("num")
						+ "' group by reportdatanum");
			}
			getMbo().setValue("reportsql", sql.toString());
			super.SAVE();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 创建发布数据
	 *
	 * @throws MXException
	 * @throws RemoteException
	 */
	public void CREATPUB() throws RemoteException, MXException {
		MboRemote report = getMbo();
		MboSetRemote fbreports = report.getMboSet("FBREPORTS");
		if (!fbreports.isEmpty()) {
			throw new MXApplicationException("zhtj", "已存在对应的发布报表:"
					+ report.getString("publishnum"));
		}

		MboSetRemote reporttargets = report.getMboSet("DAILYREPORTTARGETDATA");
		MboSetRemote mboset = report.getMboSet("#dailyreportdata",
				"DAILYREPORTDATA", "1<>1");
		MboRemote publishreport = mboset.add();
		publishreport.setValue("reporttype", report.getString("reporttype"));
		publishreport.setValue("reportdate", report.getDate("reportdate"));
		publishreport.setValue("year", report.getString("year"));
		publishreport.setValue("month", report.getString("month"));
		publishreport.setValue("quarter", report.getString("quarter"));
		publishreport.setValue("startdate", report.getDate("startdate"));
		publishreport.setValue("enddate", report.getDate("enddate"));
		publishreport.setValue("notes", report.getString("notes"));
		publishreport.setValue("ynfb", 1);
		publishreport.setValue("createdate", MXServer.getMXServer().getDate());
		publishreport.setValue("createuser", this.getMboSet().getUserInfo()
				.getPersonId());
		report.setValue("publishnum", publishreport.getString("num"));
		if (!reporttargets.isEmpty()) {
			MboSetRemote publishtargets = publishreport
					.getMboSet("DAILYREPORTTARGETDATA");
			MboRemote publishtarget = null;
			for (int i = 0; i < reporttargets.count(); i++) {
				publishtarget = publishtargets.add();
				publishtarget.setValue("reporttargetnum",
						reporttargets.getMbo(i).getString("reporttargetnum"));
				publishtarget.setValue("description", reporttargets.getMbo(i)
						.getString("description"));
				publishtarget.setValue("reportdatanum",
						publishreport.getString("num"));
				publishtarget.setValue("value", reporttargets.getMbo(i)
						.getDouble("value"));
				publishtarget.setValue("unit", reporttargets.getMbo(i)
						.getString("unit"));
				publishtarget.setValue("type", reporttargets.getMbo(i)
						.getString("type"));
				publishtarget.setValue("ordernum", reporttargets.getMbo(i)
						.getInt("ordernum"));
				publishtarget.setValue("reportdate", reporttargets.getMbo(i)
						.getDate("reportdate"));
				publishtarget.setValue("yntj",
						reporttargets.getMbo(i).getInt("yntj"));
				publishtarget.setValue("jz",
						reporttargets.getMbo(i).getString("jz"));
				publishtarget.setValue("targettype", reporttargets.getMbo(i)
						.getString("targettype"));
				publishtarget.setValue("scale",
						reporttargets.getMbo(i).getInt("scale"));
				publishtarget.setValue("creator", this.getMboSet()
						.getUserInfo().getPersonId());
				publishtarget.setValue("createdate", MXServer.getMXServer()
						.getDate());
			}
		}
		super.SAVE();
	}

	/**
	 * 创建监督数据
	 *
	 * @throws MXException
	 * @throws RemoteException
	 */
	public void CREATJD() throws RemoteException, MXException {
		MboRemote report = getMbo();
		MboSetRemote fbreports = report.getMboSet("JDREPORTS");
		if (!fbreports.isEmpty()) {
			throw new MXApplicationException("zhtj", "已存在对应的监督报表:"
					+ report.getString("jdnum"));
		}
		// 对应的发布月报指标数据
		MboSetRemote reporttargets = report.getMboSet("DAILYREPORTTARGETDATA");
		// 监督报表指标
		MboSetRemote jdreporttargets = getMbo()
				.getMboSet(
						"$reporttargets",
						"DAILYREPORTTARGET",
						"orgid=:orgid and reportnum in(select num from DAILYREPORT where orgid=:orgid and reporttype='"
								+ MONTHREPORT
								+ "') and active=1 and targetident=10");
		MboSetRemote mboset = report.getMboSet("#dailyreportdata",
				"DAILYREPORTDATA", "1<>1");
		MboRemote publishreport = mboset.add();
		publishreport.setValue("reporttype", report.getString("reporttype"));
		publishreport.setValue("reportdate", report.getDate("reportdate"));
		publishreport.setValue("year", report.getString("year"));
		publishreport.setValue("month", report.getString("month"));
		publishreport.setValue("quarter", report.getString("quarter"));
		publishreport.setValue("startdate", report.getDate("startdate"));
		publishreport.setValue("enddate", report.getDate("enddate"));
		publishreport.setValue("notes", report.getString("notes"));
		publishreport.setValue("ynjd", 1);
		publishreport.setValue("createdate", MXServer.getMXServer().getDate());
		publishreport.setValue("createuser", this.getMboSet().getUserInfo()
				.getPersonId());
		report.setValue("jdnum", publishreport.getString("num"));
		if (!reporttargets.isEmpty()) {
			MboSetRemote jdtargets = publishreport
					.getMboSet("DAILYREPORTTARGETDATA");
			MboRemote jdtarget = null;
			// 复制对应发布月报的指标数据
			for (int i = 0; i < reporttargets.count(); i++) {
				jdtarget = jdtargets.add();
				jdtarget.setValue("reporttargetnum", reporttargets.getMbo(i)
						.getString("reporttargetnum"));
				jdtarget.setValue("description", reporttargets.getMbo(i)
						.getString("description"));
				jdtarget.setValue("reportdatanum",
						publishreport.getString("num"));
				jdtarget.setValue("value",
						reporttargets.getMbo(i).getDouble("value"));
				jdtarget.setValue("unit",
						reporttargets.getMbo(i).getString("unit"));
				jdtarget.setValue("type",
						reporttargets.getMbo(i).getString("type"));
				jdtarget.setValue("ordernum",
						reporttargets.getMbo(i).getInt("ordernum"));
				jdtarget.setValue("reportdate", reporttargets.getMbo(i)
						.getDate("reportdate"));
				jdtarget.setValue("yntj", reporttargets.getMbo(i)
						.getInt("yntj"));
				jdtarget.setValue("jz", reporttargets.getMbo(i).getString("jz"));
				jdtarget.setValue("targettype", reporttargets.getMbo(i)
						.getString("targettype"));
				jdtarget.setValue("scale",
						reporttargets.getMbo(i).getInt("scale"));
				jdtarget.setValue("creator", this.getMboSet().getUserInfo()
						.getPersonId());
				jdtarget.setValue("createdate", MXServer.getMXServer()
						.getDate());
			}
			// 插入监督报表指标
			for (int j = 0; j < jdreporttargets.count(); j++) {
				jdtarget = jdtargets.add();
				jdtarget.setValue("reporttargetnum", jdreporttargets.getMbo(j)
						.getString("num"));
				jdtarget.setValue("description", jdreporttargets.getMbo(j)
						.getString("name"));
				jdtarget.setValue("reportdatanum",
						publishreport.getString("num"));
				jdtarget.setValue("unit",
						jdreporttargets.getMbo(j).getString("unit"));
				jdtarget.setValue("type",
						jdreporttargets.getMbo(j).getString("type"));
				jdtarget.setValue("ordernum",
						jdreporttargets.getMbo(j).getInt("ordernum"));
				jdtarget.setValue("jz",
						jdreporttargets.getMbo(j).getString("jz"));
				jdtarget.setValue("targettype", jdreporttargets.getMbo(j)
						.getString("targettype"));
				jdtarget.setValue("scale",
						jdreporttargets.getMbo(j).getInt("scale"));
				// 设置缺省值
				jdtarget.setValue("value",
						jdreporttargets.getMbo(j).getDouble("defuvalue"));
				jdtarget.setValue("creator", this.getMboSet().getUserInfo()
						.getPersonId());
				jdtarget.setValue("createdate", MXServer.getMXServer()
						.getDate());
			}
		}
		super.SAVE();
	}

	/**
	 * 创建报表数据快照
	 *
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void CREATSNAP() throws RemoteException, MXException {
		MboRemote report = getMbo();
		// 删除老数据
		MboSetRemote snapreports = report.getMboSet("REPORTDATASNAP");
		if (!snapreports.isEmpty()) {
			for (int i = 0; i < snapreports.count(); i++) {
				snapreports.getMbo(i).getMboSet("TARGETDATA").deleteAll();
			}
			snapreports.deleteAll();
		}
		// 插入新数据
		MboSetRemote reporttargets = report.getMboSet("DAILYREPORTTARGETDATA");
		MboSetRemote mboset = report.getMboSet("#reportdatasnap",
				"REPORTDATASNAP", "1<>1");
		MboRemote snapreport = mboset.add();
		snapreport.setValue("reporttype", report.getString("reporttype"));
		snapreport.setValue("year", report.getString("year"));
		snapreport.setValue("month", report.getString("month"));
		snapreport.setValue("quarter", report.getString("quarter"));
		snapreport.setValue("startdate", report.getDate("startdate"));
		snapreport.setValue("enddate", report.getDate("enddate"));
		snapreport.setValue("createdate", MXServer.getMXServer().getDate());
		snapreport.setValue("createuser", this.getMboSet().getUserInfo()
				.getPersonId());
		snapreport.setValue("notes", report.getString("notes"));
		String reportsql = report.getString("reportsql");
		String newreportsql = null;
		if (reportsql != null) {
			newreportsql = reportsql.replace("dailyreporttargetdata",
					"targetdatasnap").replace(
					"reportdatanum='" + report.getString("num") + "'",
					"reportdatanum='" + snapreport.getString("num") + "'");
		}
		snapreport.setValue("reportsql", newreportsql);

		if (!reporttargets.isEmpty()) {
			MboSetRemote snaptargets = snapreport.getMboSet("TARGETDATA");
			MboRemote snaptarget = null;
			for (int i = 0; i < reporttargets.count(); i++) {
				snaptarget = snaptargets.add();
				snaptarget.setValue("reporttargetnum", reporttargets.getMbo(i)
						.getString("reporttargetnum"));
				snaptarget.setValue("description", reporttargets.getMbo(i)
						.getString("description"));
				snaptarget.setValue("reportdatanum",
						snapreport.getString("num"));
				snaptarget.setValue("value",
						reporttargets.getMbo(i).getDouble("value"));
				snaptarget.setValue("unit",
						reporttargets.getMbo(i).getString("unit"));
				snaptarget.setValue("type",
						reporttargets.getMbo(i).getString("type"));
				snaptarget.setValue("ordernum",
						reporttargets.getMbo(i).getInt("ordernum"));
				snaptarget.setValue("jz",
						reporttargets.getMbo(i).getString("jz"));
				snaptarget.setValue("targettype", reporttargets.getMbo(i)
						.getString("targettype"));
				snaptarget.setValue("scale",
						reporttargets.getMbo(i).getInt("scale"));
			}
		}
		super.SAVE();
	}
}
