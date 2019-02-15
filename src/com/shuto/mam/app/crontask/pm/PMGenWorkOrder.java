package com.shuto.mam.app.crontask.pm;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import psdi.app.pm.PM;
import psdi.app.system.CrontaskParamInfo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.ConnectionKey;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;
import psdi.workflow.WorkFlowServiceRemote;

/** 
 * @author huangpf huangpf@shuto.cn 
 * @date 创建时间：2017-10-25 下午03:45:01 
 * @version 1.0 
 */
public class PMGenWorkOrder extends SimpleCronTask {
	public static final String WORKTYPE = "预维护工单";
	public static final String ACTIVE = "活动";
	public static final String FIXEDDAY = "固定日期";
	public static final String WEEKDAY = "固定星期";
	public static final String DAYS = "天";
	public static final String WEEK = "周";
	public static final String MONTH = "月";
	public static final String YEAR = "年";
	private String siteid;
	private MXServer mxserver;
	private UserInfo userInfo;
	private SimpleDateFormat dtfmt1 = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat dtfmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Calendar today = null;
	private Connection con = null;
	private CallableStatement stmt = null;
	private ConnectionKey key = null;
	private String sql = " update pm set nextdate=to_date(?,'yyyy-MM-dd') where pmnum=? and siteid=? ";
	private boolean debug = true;
	private int m = 0;
	private int n = 0;
	private int k = 0;
	String pmwrong = "";

	public void init() throws MXException {
		try {
			super.init();
			readConfig();// 读取配置地点
			this.mxserver = MXServer.getMXServer();
			this.userInfo = getRunasUserInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		try {
			readConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cronAction() {
		println("\n>>>>>>>>>>" + this.siteid + "预防性维护定时任务 PM 执行  开始时间>>>>>> ("
				+ this.dtfmt2.format(new Date()) + ") >>>>>>>>>>");
		try {
			this.key = MXServer.getMXServer().getDBManager()
					.getSystemConnectionKey();
			this.con = MXServer.getMXServer().getDBManager()
					.getConnection(this.key);
			this.con.setAutoCommit(false);
			this.stmt = this.con.prepareCall(this.sql);

			this.today = Calendar.getInstance();
			this.today
					.setTime(this.dtfmt1.parse(this.dtfmt1.format(new Date())));

			checkNextdate();
		} catch (Exception e) {
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (this.stmt != null) {
					this.stmt.close();
				}
				if (this.con != null)
					this.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(">>>>>>>>>>本次成功创建并取到工单 " + m + " 个，成功发送工作流 " + n + " 个!");
		println("\n>>>>>>>>>>" + this.siteid + "预防性维护定时任务 PM 结束 结束时间>>>>> ("
				+ this.dtfmt2.format(new Date()) + ") >>>>>>>>>>\n");
	}

	private void checkNextdate() throws RemoteException, MXException {
		println("------- checkNextdate -------");
		//当天合格的，正常创建工单
		MboSetRemote pms1 = null;
		label1:
		{
			pms1 = this.mxserver.getMboSet("pm", this.userInfo);
			String pmwhere = " worktype='预维护工单' and status='活动' and trunc(nextdate)=trunc(sysdate) " +
			"and nextdate is not null and siteid='" + this.siteid + "'";
			if (!"".equals(pmwrong)) {
				pmwhere = pmwhere + " and pmnum not in (" + pmwrong + ")";
			}
			pms1.setWhere(pmwhere);
			pms1.setOrderBy("PMUID desc");
			pms1.reset();
			int count1 = pms1.count();
			System.out.println(">>>>>当天活动的预防性维护共 " + count1 + " 个");
			if (!pms1.isEmpty()) {
				for (int j = 1; j <= count1; j++) {
					MboRemote pm = pms1.getMbo(0);
					System.out.println(">>>>>循环检查中，总共 " + count1 + " 个，当前第 " + j + " 个，pmnum：" 
							+ pm.getString("pmnum"));
					System.out.println(">>>>>当前集合pms剩余 " + pms1.count() + " 个");
					try {
						createWO(pm); 
						if (k == 1) {
							if ("".equals(pmwrong)) {
								pmwrong = "'" + pm.getString("pmnum") + "'";
							} else {
								pmwrong = pmwrong + ",'" + pm.getString("pmnum") + "'";
							}
							System.out.println(">>>>>请注意，任务执行出错，出错 pmnum如下:\n" + pmwrong);
							pms1.close();
							break label1;
						}
						setNextdate(pm.getString("pmnum"), getNextdate(pm));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			pms1.close();
		}
		//nextdate为空但活动的，设为不活动
		MboSetRemote pms2 = this.mxserver.getMboSet("pm", this.userInfo);
		pms2.setWhere(" worktype='预维护工单' and status='活动' and nextdate is null and siteid='" 
				+ this.siteid + "'");
		pms2.setOrderBy("PMUID desc");
		pms2.reset();
		int count2 = pms2.count();
		System.out.println(">>>>>nextdate为空但活动的预防性维护共 " + count2 + " 个");
		if (!pms2.isEmpty()) {
			for (int i = 0; i < count2; i++) {
				MboRemote pm = pms2.getMbo(i);
				System.out.println(">>>>>循环检查中，总共 " + pms2.count() + " 个，当前第 " + (i + 1) + " 个，pmnum：" 
						+ pm.getString("pmnum"));
				try {
					setNextdate(pm.getString("pmnum"), getNextdate(pm));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		pms2.close();
		//过期但活动的，更新其nextdate
		MboSetRemote pms3 = this.mxserver.getMboSet("pm", this.userInfo);
		pms3.setWhere(" worktype='预维护工单' and status='活动' and trunc(nextdate)<trunc(sysdate) " +
				"and nextdate is not null and siteid='" + this.siteid + "'");
		pms3.setOrderBy("PMUID desc");
		pms3.reset();
		int count3 = pms3.count();
		System.out.println(">>>>>过期但活动的预防性维护共 " + count3 + " 个");
		if (!pms3.isEmpty()) {
			for (int i = 0; i < count3; i++) {
				MboRemote pm = pms3.getMbo(i);
				System.out.println(">>>>>循环检查中，总共 " + pms3.count() + " 个，当前第 " + (i + 1) + " 个，pmnum：" 
						+ pm.getString("pmnum"));
				try {
					setNextdate(pm.getString("pmnum"), getNextdate(pm));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		pms3.close();
		println("\n----- checkNextdate end -----\n");
	}

	private void createWO(MboRemote pm) throws RemoteException, MXException {
		System.out.println(">>>>>generating workorder begin..."
				+ pm.getString("pmnum"));
		try {
			((PM) pm).generateWork(false, 0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(">>>>>请注意，工单创建失败..." 
					+ pm.getString("pmnum"));
			k = 1;
			return;
		}
		k = 0;
		System.out.println(">>>>>generating workorder ok..."
				+ pm.getString("pmnum"));
		MboSetRemote woSet = this.mxserver
				.getMboSet("workorder", this.userInfo);
		woSet.setWhere("pmnum='" + pm.getString("pmnum") + "' and siteid='" + pm.getString("siteid") 
				+ "' and worktype='预维护工单' and trunc(targstartdate)=trunc(sysdate)");
		
		woSet.setOrderBy("workorderid desc");
		woSet.reset();
		if (!woSet.isEmpty()) {
			String location = pm.getString("LOCATION");
			String s_profession = pm.getString("s_profession");
			String SUPERVISOR = pm.getString("SUPERVISOR");
			String TEAMNAME = pm.getString("TEAMNAME");
			String LEAD = pm.getString("LEAD");
			String wonum = woSet.getMbo(0).getString("wonum");
			System.out.println(">>>>>generating workorder is ok工单已创建，开始设值..." 
					+ pm.getString("pmnum") + "---" + wonum);
			
			woSet.getMbo(0).setValue("location", location, 11L);
			woSet.getMbo(0).setValue("createperson", SUPERVISOR, 11L);
			woSet.getMbo(0).setValue("LEAD", LEAD, 11L);
			woSet.getMbo(0).setValue("TEAMNUM", TEAMNAME, 11L);
			woSet.getMbo(0).setValue("PROFESSION", s_profession, 11L);

			Date date = new Date();
			woSet.getMbo(0).setValue("SCHEDSTART", date, 11L);
			woSet.getMbo(0).setValue("SCHEDFINISH", date, 11L);
			woSet.save();

			System.out.println(">>>>>generating workorder is ok，设值结束..." 
					+ pm.getString("pmnum") + "---" + wonum);
			m++;
			WorkFlowServiceRemote wfs = (WorkFlowServiceRemote) MXServer
					.getMXServer().lookup("WORKFLOW");
			System.out.println(">>>>>generating workorder is ok，获取工作流..." 
					+ pm.getString("pmnum") + "---" + wonum);
			wfs.initiateWorkflow("WO", woSet.getMbo(0));
			System.out.println(">>>>>initiateWorkflow workorder is ok，工作流成功发送..."
					+ pm.getString("pmnum") + "---" + wonum);
			n++;
		}
		woSet.close();
	}

	private Calendar getNextdate(MboRemote pm) throws RemoteException,
			MXException {
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(this.today.getTime());
		if (pm.getString("frequnit").equals("固定日期")) {
			if (!pm.getString("fixedday").isEmpty()) {
				nextday = getNextdate_fixedday(pm);
			} else {
				println(">>>>>error - don't config fixedday.");
				nextday = null;
			}
		} else if (pm.getString("frequnit").equals("固定星期")) {
			if (!pm.getString("fixedday").isEmpty()) {
				nextday = getNextdate_weekday(pm);
			} else {
				println(">>>>>error - doesn't config fixedweekday\n");
				nextday = null;
			}
		} else if (pm.getString("frequnit").equals("天")) {
			if (!pm.getString("FREQUENCY").isEmpty()) {
				nextday = getNextdate_days(pm);
			} else {
				println(">>>>>error - doesn't config fixedweekday\n");
				nextday = null;
			}
		} else if (pm.getString("frequnit").equals("周")) {
			if (!pm.getString("FREQUENCY").isEmpty()) {
				nextday = getNextdate_week(pm);
			} else {
				println(">>>>>error - doesn't config fixedweekday\n");
				nextday = null;
			}
		} else if (pm.getString("frequnit").equals("月")) {
			if (!pm.getString("FREQUENCY").isEmpty()) {
				nextday = getNextdate_month(pm);
			} else {
				println(">>>>>error - doesn't config fixedweekday\n");
				nextday = null;
			}
		} else if (pm.getString("frequnit").equals("年")) {
			if (!pm.getString("FREQUENCY").isEmpty()) {
				nextday = getNextdate_year(pm);
			} else {
				println(">>>>>error - doesn't config fixedweekday\n");
				nextday = null;
			}
		}
		println(">>>>>nextdate="
				+ ((nextday == null) ? "" : this.dtfmt1.format(nextday
						.getTime())));
		return nextday;
	}

	private Calendar getNextdate_fixedday(MboRemote pm) throws RemoteException,
			MXException {
		if (pm.isNull("nextdate")) {
			return newNextdate_fixedday(pm, this.today);
		}
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(pm.getDate("nextdate"));
		do {
			nextday = newNextdate_fixedday(pm, nextday);
			try {
				nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday
						.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (nextday.getTime().getTime() <= this.today.getTime().getTime());
		return nextday;
	}

	private Calendar newNextdate_fixedday(MboRemote pm, Calendar curdate)
			throws RemoteException, MXException {
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(curdate.getTime());

		boolean flag = false;

		StringTokenizer st = new StringTokenizer(pm.getString("fixedday"), ",");
		while (st.hasMoreTokens()) {
			String day = st.nextToken();
			if (Integer.valueOf(day).intValue() > curdate.get(5)) {
				flag = true;
			}
			if (!flag)
				continue;
			nextday.set(5, Integer.valueOf(day).intValue());
			break;
		}

		if (!flag) {
			nextday.add(2, pm.getInt("frequency"));
			st = new StringTokenizer(pm.getString("fixedday"), ",");
			String defday = st.nextToken();
			nextday.set(5, Integer.valueOf(defday).intValue());
		}
		System.out.println(">>>>>newNextdate_fixedday="
				+ this.dtfmt1.format(nextday.getTime()));
		return nextday;
	}

	private Calendar getNextdate_weekday(MboRemote pm) throws RemoteException,
			MXException {
		if (pm.isNull("nextdate")) {
			return newNextdate_weekday(pm, this.today);
		}
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(pm.getDate("nextdate"));
		do {
			nextday = newNextdate_weekday(pm, nextday);
			try {
				nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday
						.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (nextday.getTime().getTime() <= this.today.getTime().getTime());
		return nextday;
	}

	private Calendar newNextdate_weekday(MboRemote pm, Calendar curdate)
			throws RemoteException, MXException {
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(curdate.getTime());

		boolean flag = false;

		StringTokenizer st = new StringTokenizer(pm.getString("fixedday"), ",");
		while (st.hasMoreTokens()) {
			String weekday = st.nextToken();
			if (Integer.valueOf(weekday).intValue() > nextday.get(7) - 1) {
				flag = true;
			}
			if (flag) {
				if (Integer.valueOf(weekday).intValue() < 7) {
					nextday.set(7, Integer.valueOf(weekday).intValue() + 1);
					break;
				}
				nextday.add(5, 7);
				nextday.set(7, 1);

				break;
			}
		}
		if (!flag) {
			nextday.add(5, pm.getInt("frequency") * 7);
			st = new StringTokenizer(pm.getString("fixedday"), ",");
			String weekday = st.nextToken();
			if (Integer.valueOf(weekday).intValue() < 7) {
				nextday.set(7, Integer.valueOf(weekday).intValue() + 1);
			} else {
				nextday.add(5, 7);
				nextday.set(7, 1);
			}
		}
		System.out.println(">>>>>newNextdate_weekday="
				+ this.dtfmt1.format(nextday.getTime()));
		return nextday;
	}

	private Calendar getNextdate_days(MboRemote pm) throws RemoteException,
			MXException {
		if (pm.isNull("nextdate")) {
			if (pm.getInt("frequency") == 1) {
				return this.today;
			}
			return null;
		}
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(pm.getDate("nextdate"));
		do {
			nextday.add(5, pm.getInt("frequency"));
			try {
				nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday
						.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (nextday.getTime().getTime() <= this.today.getTime().getTime());
		return nextday;
	}

	private Calendar getNextdate_week(MboRemote pm) throws RemoteException,
			MXException {
		if (pm.isNull("nextdate")) {
			return null;
		}
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(pm.getDate("nextdate"));
		do {
			nextday.add(5, pm.getInt("frequency") * 7);
			try {
				nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday
						.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (nextday.getTime().getTime() <= this.today.getTime().getTime());
		return nextday;
	}

	private Calendar getNextdate_month(MboRemote pm) throws RemoteException,
			MXException {
		if (pm.isNull("nextdate")) {
			return null;
		}
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(pm.getDate("nextdate"));
		do {
			nextday.add(2, pm.getInt("frequency"));
			try {
				nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday
						.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (nextday.getTime().getTime() <= this.today.getTime().getTime());
		return nextday;
	}

	private Calendar getNextdate_year(MboRemote pm) throws RemoteException,
			MXException {
		if (pm.isNull("nextdate")) {
			return null;
		}
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(pm.getDate("nextdate"));
		do {
			nextday.add(1, pm.getInt("frequency"));
			try {
				nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday
						.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (nextday.getTime().getTime() <= this.today.getTime().getTime());
		return nextday;
	}

	private void setNextdate(String pmnum, Calendar nextdate)
			throws RemoteException, MXException, SQLException {
		if (nextdate == null) {
			println(">>>>>deactivate pm " + pmnum);
			this.stmt = this.con
					.prepareCall("update pm set status='不活动' where pmnum='"
							+ pmnum + "' and siteid='" + this.siteid + "'");
		} else {
			println(">>>>>setNextdate="
					+ this.dtfmt1.format(nextdate.getTime()));
			this.stmt.setString(1, this.dtfmt1.format(nextdate.getTime()));
			this.stmt.setString(2, pmnum);
			this.stmt.setString(3, this.siteid);
		}
		this.stmt.execute();
		this.con.commit();
	}

	private void readConfig() throws RemoteException, MXException {
		this.siteid = getParamAsString("SITEID");
	}

	public CrontaskParamInfo[] getParameters() throws MXException,
			RemoteException {
		try {
			String[] names = { "siteid" };
			String[] defs = { "修改成对应的siteid" };
			String[][] descriptions = { { "cronprepr", "CronParamSiteid" } };
			CrontaskParamInfo[] ret = new CrontaskParamInfo[names.length];
			for (int i = 0; i < names.length; ++i) {
				ret[i] = new CrontaskParamInfo();
				ret[i].setName(names[i]);
				ret[i].setDefault(defs[i]);
				ret[i].setDescription(descriptions[i][0], descriptions[i][1]);
			}
			return ret;
		} catch (Exception e) {
			if (getCronTaskLogger().isErrorEnabled())
				getCronTaskLogger().error(e);
		}
		return null;
	}

	private void println(String str) {
		if (this.debug)
			System.out.println(str);
	}
}
