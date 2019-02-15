package com.shuto.mam.crontask.pm;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import psdi.app.system.CrontaskParamInfo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;

/**
    *  文件名： com.shuto.mam.crontask.pm.PMLineGenOPWork.java
    *  说明：运行定期工作定时任务
    *  创建日期： 2017年8月11日
    *  修改历史 :   
    *     1. [2017年8月11日下午5:21:31] 创建文件 by lull lull@shuto.cn
 */
public class PMLineGenOPWork extends SimpleCronTask {
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
	private String today;

	@Override
	public void init() throws MXException {
		super.init();
	}

	@Override
	public void cronAction() {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		logfile("============  " + sdf2.format(new Date()) + " 开始执行定期工作   ============");
		cronAction1();
		long endTime = System.currentTimeMillis(); // 获取结束时间
		logfile("============  " + sdf2.format(new Date()) + " 结束定期工作       ============" + "   执行时间： "
				+ (endTime - startTime) / 1000 + "s");

	}

	/**
	 *  开始执行定期工作
	 *
	 */
	private void cronAction1() {
		try {
			today = sdf1.format(new Date());
			String siteid = getParamAsString("siteid");
			String selSql = "  exists (select 1 from pm where pmline.pmnum = pm.pmnum and worktype ='运行定期工作' "
					+ "and status = '活动') and to_char(nextdate,'yyyy-MM-dd') <= '" + today + "'  ";
			if (siteid != null && !"".equals(siteid)) {
				selSql = "  exists (select 1 from pm where pmline.pmnum = pm.pmnum and worktype ='运行定期工作' "
						+ "and status = '活动' ) and to_char(nextdate,'yyyy-MM-dd') <= '" + today + "'  and siteid = '"
						+ siteid + "'";
			}
			MboSetRemote pmlineSet = MXServer.getMXServer().getMboSet("pmline", getRunasUserInfo());
			pmlineSet.setWhere(selSql);
			pmlineSet.setOrderBy("nextdate desc ,unit ,  fixeddate ");
			pmlineSet.reset();
			logfile("=      本次执行共  " + pmlineSet.count() + "  条");
			if (!pmlineSet.isEmpty()) {
				MboRemote pmline;
				String nextdate;
				String unit;
				String oplogWorkNum;
				MboSetRemote oplogworkSet = MXServer.getMXServer().getMboSet("oplogwork", getRunasUserInfo());
				MboRemote oplogwork;
				for (int i = 0; (pmline = pmlineSet.getMbo(i)) != null; i++) {
					nextdate = sdf1.format(pmline.getDate("nextdate"));// 下一次执行时间
					unit = pmline.getString("unit");// 频率{ 天、周、月、年、固定日期、固定星期 }

					if (today.equals(nextdate)) {
						oplogwork = oplogworkSet.addAtEnd();
						/** 下一次执行日期与今天相同 则创建工单**/
						oplogWorkNum = createOplogWork(pmline, oplogwork);
						logfile("=      pmnum =   " + pmline.getString("pmnum") + "  执行频率为 ：" + unit
								+ "  开始生成任务  任务编号为 ：  " + oplogWorkNum);
					}
					/** 下一次执行日期小于今天不同 则更新下一次执行日期**/
					nextdate = updateNextDate(pmline);
					logfile("=      更新下次执行时间为 : " + nextdate);
					oplogworkSet.save();
					oplogworkSet.close();

				}
				pmlineSet.save();
			}
			pmlineSet.clear();
			pmlineSet.close();
		} catch (RemoteException e) {
			logfile(e.getMessage());
			e.printStackTrace();
		} catch (MXException e) {
			logfile(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 当前时间大于下一次执行时间时
	 *  更新下一执行时间
	 *
	 * @param pmline
	 * @param i
	 */
	private String updateNextDate(MboRemote pmline) {
		String nextdate = "";
		try {
			String unit = pmline.getString("unit");// 频率{ 天、周、月、年、固定日期、固定星期 }
			int schedule = pmline.getInt("schedule");// 周期
			Date oldNextDate = pmline.getDate("nextdate");// 下一次执行时间
			int fixeddate = pmline.getInt("fixeddate");// 指定日期
			if (oldNextDate == null) {
				pmline.setValue("pm.status", "不活动", 11l);
				return "";
			}
			if ("天".equals(unit)) {
				nextdate = getNextDate(today, schedule);
			} else if ("周".equals(unit)) {
				nextdate = getNextDate(sdf1.format(oldNextDate), schedule * 7);
			} else if ("月".equals(unit)) {
				nextdate = getNextDate(sdf1.format(oldNextDate), "month", schedule);
			} else if ("年".equals(unit)) {
				nextdate = getNextDate(sdf1.format(oldNextDate), "year", schedule);
			} else if ("固定日期".equals(unit)) {
				nextdate = getNextDate(fixeddate);
			} else if ("固定星期".equals(unit)) {
				nextdate = getNextWeek(fixeddate);
			} else {
				pmline.setValue("pm.status", "不活动", 11l);
				return "";
			}
			pmline.setValue("nextdate", nextdate, 11l);
		} catch (RemoteException e) {
			logfile(e.getMessage());
			// e.printStackTrace();
		} catch (MXException e) {
			logfile(e.getMessage());
			// e.printStackTrace();
		}
		return nextdate;
	}

	/**
	 *  TODO 添加方法功能描述
	 *
	 * @return
	 */
	private String getNextDate(int fixeddate) {
		String nextdate = "";
		int day = Integer.parseInt(sdf3.format(new Date()));
		if (day == fixeddate) {
			nextdate = getNextDate(today, "month", 1);
		} else if (day > fixeddate) {
			Calendar cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, fixeddate);
			nextdate = sdf1.format(cal.getTime());
			cal.clear();
		} else {
			Calendar cal = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), fixeddate);
			nextdate = sdf1.format(cal.getTime());
			cal.clear();
		}
		return nextdate;
	}

	/**
	 * 
	 *  获取下一周次
	 *
	 * @param date
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public String getNextWeek(int fixeddate) {
		String nextdate = "";
		if (fixeddate > 7 || fixeddate < 1) {
			return "";
		}
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf1.parse(today));
		} catch (ParseException e) {
			logfile(e.getMessage());
			e.printStackTrace();
		}
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week == 0) {// 如果是周日则为7
			week = 7;
		}
		if (fixeddate == week) {
			week = 7;
			nextdate = getNextDate(today, 7);
		} else if (week > fixeddate) {
			nextdate = getNextDate(today, 7 - week + fixeddate);
		} else {
			nextdate = getNextDate(today, fixeddate - week);
		}
		return nextdate;
	}

	/**
	 *  获取下一执行日期
	 *
	 * @param flag
	 * @param schedule
	 * @return 
	 */
	private String getNextDate(String today, String flag, int schedule) {
		String nextdate = "";
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf1.parse(today));
		} catch (ParseException e) {
			logfile(e.getMessage());
			// e.printStackTrace();
		}
		if ("month".equals(flag)) {
			cal.add(Calendar.MONTH, schedule);
		} else if ("year".equals(flag)) {
			cal.add(Calendar.YEAR, schedule);
		}
		nextdate = sdf1.format(cal.getTime());
		return nextdate;
	}

	/**
	 *  获取下一执行日期 { 天 、 周}
	 *
	 * @param i
	 * @throws ParseException 
	 */
	private String getNextDate(String today, int i) {
		String nextdate = "";
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf1.parse(today));
		} catch (ParseException e) {
			logfile(e.getMessage());
			// e.printStackTrace();
		}
		cal.add(Calendar.DAY_OF_YEAR, i);
		nextdate = sdf1.format(cal.getTime());
		cal.clear();
		return nextdate;
	}

	/**
	 *  创建oplogwork
	 *
	 * @param pmline
	 * @param oplogwork2 
	 * @return
	 */
	private String createOplogWork(MboRemote pmline, MboRemote oplogwork) {
		String oplogworknum = "";
		try {
			String pmnum = pmline.getString("pm.pmnum");
			String DESCRIPTION = pmline.getString("pm.DESCRIPTION");// pm描述
			String LOCATION = pmline.getString("pm.LOCATION");// 位置编码
			String ASSETNUM = pmline.getString("pm.ASSETNUM");// 资产编码
			String S_PROFESSION = pmline.getString("pm.S_PROFESSION");// 专业
			String OPLOGTYPE = pmline.getString("pm.OPLOGTYPE");// 日志类别
			// String worktype = pmline.getString("worktype");// 业务类别——运行定期工作
			String REMARK = pmline.getString("pm.REMARK");// 备注
			String YX_TAS = pmline.getString("pm.YX_TASK");// 工作任务
			String shift = pmline.getString("shift");
			oplogwork.setValue("PMNUM", pmnum, 11L);// pmnum
			oplogwork.setValue("DESCRIPTION", DESCRIPTION, 11L);
			oplogwork.setValue("LOCATION", LOCATION, 11L);
			oplogwork.setValue("ASSETNUM", ASSETNUM, 11L);
			oplogwork.setValue("OPLOG_CREATEDATE", new Date(), 11L);// 生成时间
			oplogwork.setValue("OPLOG_BANCI", shift, 11L);// 班次
			oplogwork.setValue("OPLOG_PROFESSION", S_PROFESSION, 11L);// 专业
			oplogwork.setValue("OPLOG_TASK", YX_TAS, 11L);// 工作任务
			oplogwork.setValue("WORKTYPE", "运行定期工作", 11L);// 业务类别——运行定期工作
			oplogwork.setValue("OPLOG_REMARK", REMARK, 11L);// 备注
			oplogwork.setValue("OPLOGTYPE", OPLOGTYPE, 11L);// 日志类别
			oplogwork.setValue("OPLOG_STATUS", "生成", 11L);// 状态
			oplogwork.setValue("siteid", pmline.getString("siteid"), 11L);// 地点
			oplogworknum = oplogwork.getString("WONUM");
		} catch (RemoteException e) {
			logfile(e.getMessage());
			// e.printStackTrace();
		} catch (MXException e) {
			logfile(e.getMessage());
			// e.printStackTrace();
		}
		return oplogworknum;
	}

	public CrontaskParamInfo[] getParameters() throws MXException, RemoteException {
		try {
			String names[] = { "siteid", "logfile" };
			String defs[] = { "", "" };
			CrontaskParamInfo ret[] = new CrontaskParamInfo[names.length];
			for (int i = 0; i < names.length; i++) {
				ret[i] = new CrontaskParamInfo();
				ret[i].setName(names[i]);
				ret[i].setDefault(defs[i]);
			}
			return ret;
		} catch (Exception e) {
			if (getCronTaskLogger().isErrorEnabled())
				getCronTaskLogger().error(e);
		}
		return null;
	}

	/**
	 * 
	 *  写入日志文件
	 *
	 * @param str
	 */
	private void logfile(String str) {
		System.out.println(str);
	}
}