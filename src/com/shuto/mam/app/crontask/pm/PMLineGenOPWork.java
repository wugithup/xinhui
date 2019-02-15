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

import psdi.app.system.CrontaskParamInfo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.ConnectionKey;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;

/**
 * 运行定期工作定时任务类
 * 
 * @author zhenglb@shuoto.cn
 * @date 2017年7月25日
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class PMLineGenOPWork extends SimpleCronTask {
	// 定义变量
	public static final String ACTIVE = "活动";
	public static final String WORKTYPE = "运行定期工作";
	private String siteid;
	private MXServer mxserver;
	private UserInfo userInfo;

	private Connection con = null;
	private CallableStatement stmt = null;
	private ConnectionKey key = null;
	private String sql = "update pmline set nextdate=to_date(?, 'yyyy-MM-dd') where pmlineid=?";
	private boolean debug = true;

	/**
	 * yyyy/MM/dd
	 */
	private SimpleDateFormat dtfmt1 = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	private SimpleDateFormat dtfmt2 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private Calendar today = null;

	/**
	 * <p>
	 * 此方法为初始化方法， 初始化时将得到到maxserver与userinfo
	 * 
	 * @throws MXException
	 */
	public void init() throws MXException {
		try {
			super.init();
			siteid = null;
			mxserver = MXServer.getMXServer();
			userInfo = getRunasUserInfo();
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

	/**
	 * <p>
	 * 此方法为定时执行方法
	 */
	@Override
	public void cronAction() {
		System.out.println("\n>>>>>>>>>> 火电业态-" + siteid
				+ "-运行定期工作 执行开始PMLine>>>>>>>(" + this.dtfmt2.format(new Date())
				+ ") >>>>>>>>>>");
		MboSetRemote pms = null;
		try {

			this.key = MXServer.getMXServer().getDBManager()
					.getSystemConnectionKey();
			this.con = MXServer.getMXServer().getDBManager()
					.getConnection(this.key);
			con.setAutoCommit(false);
			this.stmt = this.con.prepareCall(this.sql);

			// 格式化时间
			today = Calendar.getInstance();
			this.today
					.setTime(this.dtfmt1.parse(this.dtfmt1.format(new Date())));

			pms = mxserver.getMboSet("pm", userInfo);
			pms.setWhere(" worktype = '" + WORKTYPE + "' and status='" + ACTIVE
					+ "' and siteid='" + this.siteid + "'");
			pms.reset();
			MboRemote pm = null;

			if (!pms.isEmpty()) {
				for (int i = 0; i < pms.count(); i++) {
					pm = pms.getMbo(i);
					System.out.println(">>>>>pmnum=" + pm.getString("pmnum"));
					// 将pm传入解析时间行方法中，返回时间点
					// 检查是否能创建工单，调用方法创建工单
					checkDate(pm);
				}
				pms.close();
			}
			System.out.println("\n>>>>>>>>>> 火电业态-" + siteid
					+ "-运行定期工作 执行结束PMLine>>>>>>>>>("
					+ this.dtfmt2.format(new Date()) + ") >>>>>>>>>>");
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
				if (this.con != null) {
					this.con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <p>
	 * 此方法为检查“下一个到期时间方法”， 当前时间等于下一个开始时间时， 触发创建工单 检查pmline 中的下一个到期时间（nextdate）
	 * 当前时间等于下一个到期日时间（nextdate），创建运行定期工作并且更新下一个到期时间
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public void checkDate(MboRemote pm) throws RemoteException, MXException,
			SQLException {

		// 得到时间行（pmline）的mboset集合
		MboSetRemote pmlineset = pm.getMboSet("pmnum_pmline");

		Calendar nextday = Calendar.getInstance();
		Date nextdate = new Date();
		// 循环遍历pmline结果集
		if (!pmlineset.isEmpty()) {

			for (int i = 0; i < pmlineset.count(); i++) {
				MboRemote pmline = pmlineset.getMbo(i);
				// 获得当前到期日期
				nextday = Calendar.getInstance();
				nextdate = pmline.getDate("nextdate");
				if (nextdate != null) {
					nextday.setTime(nextdate);
				} else {
					setNextdate(pmline.getLong("pmlineid"), getNextdate(pmline));
					continue;
				}

				if (nextday.getTime().getTime() < this.today.getTime()
						.getTime()) {// 下一个日期失效
					System.out
							.println(">>>>>nextdate is expired, set a new nextdate");
					// 设置新的下一个日期
					setNextdate(pmline.getLong("pmlineid"), getNextdate(pmline));
				} else if (this.dtfmt1.format(nextday.getTime()).equals(
						this.dtfmt1.format(this.today.getTime()))) {
					System.out
							.println(">>>>>nextdate is OK, start create OplogWorkor");
					createOplogWork(pm, pmline.getString("SHIFT"));
					setNextdate(pmline.getLong("pmlineid"), getNextdate(pmline));
				}
			}
		}
		pmlineset.save();
		pmlineset.close();
	}

	/**
	 * <p>
	 * 此方法为创建运行定期工作的方法
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 */
	private void createOplogWork(MboRemote pm, String shift)
			throws RemoteException, MXException {
		System.out.println("====PMLine 创建运行定期工作 开始====");

		String pmnum = pm.getString("pmnum");// 编号
		String DESCRIPTION = pm.getString("DESCRIPTION");// pm描述
		String LOCATION = pm.getString("LOCATION");// 位置编码
        String LOCATIONDETAIL = pm.getString("LOCATIONDETAIL");// 位置描述
		String ASSETNUM = pm.getString("ASSETNUM");// 资产编码
		String S_PROFESSION = pm.getString("S_PROFESSION");// 专业
		String OPLOGTYPE = pm.getString("OPLOGTYPE");// 日志类别
		String worktype = pm.getString("worktype");// 业务类别——运行定期工作
		String REMARK = pm.getString("REMARK");// 备注
		String YX_TAS = pm.getString("YX_TASK");// 工作任务

		// 如果已经生成过工单，则不再生成
		MboSetRemote wos = mxserver.getMboSet("oplogwork", userInfo);
		wos.setWhere(" pmnum='" + pmnum + "' and siteid='"
				+ pm.getString("siteid")
				+ "' and trunc(OPLOG_CREATEDATE,'DD')=trunc(sysdate,'DD')");
		wos.setOrderBy("OPLOGWORKID desc");

		wos.reset();

		System.out.println("开始生成>>>>>>>>>>>>>>>>>>>>>>>>pmnum===" + pmnum);
		MboRemote tagout = wos.addAtEnd();

		tagout.setValue("PMNUM", pmnum, 11L);// pmnum
		tagout.setValue("DESCRIPTION", DESCRIPTION, 11L);
		tagout.setValue("LOCATION", LOCATION, 11L);
        tagout.setValue("LOCATIONDETAIL", LOCATIONDETAIL, 11L);
		tagout.setValue("ASSETNUM", ASSETNUM, 11L);
		tagout.setValue("OPLOG_CREATEDATE", new Date(), 11L);// 生成时间
		tagout.setValue("OPLOG_BANCI", shift, 11L);// 班次
		tagout.setValue("OPLOG_PROFESSION", S_PROFESSION, 11L);// 专业
		tagout.setValue("OPLOG_TASK", YX_TAS, 11L);// 工作任务
		tagout.setValue("WORKTYPE", worktype, 11L);// 业务类别——运行定期工作
		tagout.setValue("OPLOG_REMARK", REMARK, 11L);// 备注
		tagout.setValue("OPLOGTYPE", OPLOGTYPE, 11L);// 日志类别
		tagout.setValue("OPLOG_STATUS", "生成", 11L);// 状态
		tagout.setValue("siteid", siteid, 11L);// 地点

		System.out.println("生成成功>>>>>>>>>>>>>>>>>>>>>>>>wonum="
				+ tagout.getString("WONUM"));

		wos.save();
		System.out.println("===========PMlLINE 创建运行定期工作 结束（成功）====");
		wos.close();
	}

	/**
	 * 获取定期工作的下一个日期
	 * 
	 * @param pmline
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private Calendar getNextdate(MboRemote pmline) throws RemoteException,
			MXException {
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(this.today.getTime());
		if (pmline.getString("UNIT").equals("固定日期")) {
			if (!pmline.getString("FIXEDDATE").isEmpty()) {
				nextday = getNextdate_fixedday(pmline);
			} else {
				println(">>>>>error - don't config fixedday.");
				nextday = null;
			}
		} else if (pmline.getString("UNIT").equals("固定星期")) {
			if (!pmline.getString("FIXEDDATE").isEmpty()) {
				nextday = getNextdate_weekday(pmline);
			} else {
				println(">>>>>error - doesn't config fixedweekday\n");
				nextday = null;
			}
		} else if (pmline.getString("UNIT").equals("天")) {
			if (!pmline.getString("SCHEDULE").isEmpty()) {
				nextday = getNextdate_days(pmline);
			} else {
				println(">>>>>error - doesn't config fixedweekday\n");
				nextday = null;
			}
		} else if (pmline.getString("UNIT").equals("周")) {
			if (!pmline.getString("SCHEDULE").isEmpty()) {
				nextday = getNextdate_week(pmline);
			} else {
				println(">>>>>error - doesn't config fixedweekday\n");
				nextday = null;
			}

		} else if (pmline.getString("UNIT").equals("月")) {
			if (!pmline.getString("SCHEDULE").isEmpty()) {
				nextday = getNextdate_month(pmline);
			} else {
				println(">>>>>error - doesn't config fixedweekday\n");
				nextday = null;
			}

		} else if (pmline.getString("UNIT").equals("年")) {
			if (!pmline.getString("SCHEDULE").isEmpty()) {
				nextday = getNextdate_year(pmline);
			} else {
				println(">>>>>error - doesn't config fixedweekday\n");
				nextday = null;
			}
		}
		println(">>>>>nextdate="
				+ (nextday == null ? "null" : this.dtfmt1.format(nextday
						.getTime())));
		return nextday;
	}

	/**
	 * 下一个指定日期
	 * 
	 * @param pmline
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private Calendar getNextdate_fixedday(MboRemote pmline)
			throws RemoteException, MXException {
		// 指定日期为空，不更新下一个执行日期
		if (pmline.isNull("FIXEDDATE")) {
			return null;
		}
		if (pmline.isNull("nextdate")) {
			return newNextdate_fixedday(pmline, this.today);
		}
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(pmline.getDate("nextdate"));
		do {
			nextday = newNextdate_fixedday(pmline, nextday);
			try {
				nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday
						.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (nextday.getTime().getTime() <= this.today.getTime().getTime());
		return nextday;
	}

	private Calendar newNextdate_fixedday(MboRemote pmline, Calendar curdate)
			throws RemoteException, MXException {
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(curdate.getTime());

		boolean flag = false;

		StringTokenizer st = new StringTokenizer(pmline.getString("FIXEDDATE"),
				",");
		while (st.hasMoreTokens()) {
			String day = st.nextToken();
			if (Integer.valueOf(day).intValue() > curdate
					.get(Calendar.DAY_OF_MONTH)) {
				flag = true;
			}
			if (flag) {
				nextday.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day)
						.intValue());
				break;
			}
		}
		if (!flag) {
			nextday.add(Calendar.MONTH, 1);// 下一个月
			st = new StringTokenizer(pmline.getString("FIXEDDATE"), ",");
			String defday = st.nextToken();
			nextday.set(Calendar.DAY_OF_MONTH, Integer.valueOf(defday)
					.intValue());// 指定日期
		}
		System.out.println(">>>>>newNextdate_fixedday="
				+ this.dtfmt1.format(nextday.getTime()));
		return nextday;
	}

	/**
	 * 下一指定星期
	 * 
	 * @param pmline
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private Calendar getNextdate_weekday(MboRemote pmline)
			throws RemoteException, MXException {
		// 指定日期为空，不更新下一个执行日期
		if (pmline.isNull("FIXEDDATE")) {
			return null;
		}
		if (pmline.isNull("nextdate")) {
			return newNextdate_weekday(pmline, this.today);
		}
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(pmline.getDate("nextdate"));
		do {
			nextday = newNextdate_weekday(pmline, nextday);
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

		StringTokenizer st = new StringTokenizer(pm.getString("FIXEDDATE"), ",");
		while (st.hasMoreTokens()) {
			String weekday = st.nextToken();
			if (Integer.valueOf(weekday).intValue() > nextday
					.get(Calendar.DAY_OF_WEEK) - 1) {
				flag = true;
			}
			if (flag) {
				if (Integer.valueOf(weekday).intValue() < 7) {// 周一至周六
					nextday.set(7, Integer.valueOf(weekday).intValue() + 1);
					break;
				}
				nextday.add(Calendar.DAY_OF_MONTH, 7);// 下周
				nextday.set(Calendar.DAY_OF_WEEK, 1);// 周日

				break;
			}
		}
		if (!flag) {
			nextday.add(Calendar.DAY_OF_MONTH, 7);
			st = new StringTokenizer(pm.getString("FIXEDDATE"), ",");
			String weekday = st.nextToken();
			if (Integer.valueOf(weekday).intValue() < 7) {// 周一至周六
				nextday.set(Calendar.DAY_OF_WEEK, Integer.valueOf(weekday)
						.intValue() + 1);
			} else {
				nextday.add(Calendar.DAY_OF_MONTH, 7);// 下周
				nextday.set(Calendar.DAY_OF_WEEK, 1);// 周日
			}
		}
		System.out.println(">>>>>newNextdate_weekday="
				+ this.dtfmt1.format(nextday.getTime()));
		return nextday;
	}

	/**
	 * 频率单位 天，获取下一个执行日期
	 * 
	 * @param pmline
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private Calendar getNextdate_days(MboRemote pmline) throws RemoteException,
			MXException {
		if (pmline.isNull("nextdate")) {
			return null;
		}
		if (pmline.isNull("SCHEDULE") || pmline.getInt("SCHEDULE") <= 0) {
			return null;
		}
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(pmline.getDate("nextdate"));
		do {
			nextday.add(Calendar.DAY_OF_MONTH, pmline.getInt("SCHEDULE"));
			try {
				nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday
						.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (nextday.getTime().getTime() <= this.today.getTime().getTime());
		return nextday;
	}

	/**
	 * 频率单位 周，获取下一个执行日期
	 * 
	 * @param pmline
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private Calendar getNextdate_week(MboRemote pmline) throws RemoteException,
			MXException {
		if (pmline.isNull("nextdate")) {
			return null;
		}
		if (pmline.isNull("SCHEDULE") || pmline.getInt("SCHEDULE") <= 0) {
			return null;
		}
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(pmline.getDate("nextdate"));
		do {
			nextday.add(5, pmline.getInt("SCHEDULE") * 7);
			try {
				nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday
						.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (nextday.getTime().getTime() <= this.today.getTime().getTime());

		System.out.println(">>>>>newNextdate_week="
				+ this.dtfmt1.format(nextday.getTime()));
		return nextday;
	}

	/**
	 * 频率单位 月，获取下一个执行日期
	 * 
	 * @param pmline
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private Calendar getNextdate_month(MboRemote pmline)
			throws RemoteException, MXException {
		if (pmline.isNull("nextdate")) {
			return null;
		}
		if (pmline.isNull("SCHEDULE") || pmline.getInt("SCHEDULE") <= 0) {
			return null;
		}
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(pmline.getDate("nextdate"));
		do {
			nextday.add(2, pmline.getInt("SCHEDULE"));
			try {
				nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday
						.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (nextday.getTime().getTime() <= this.today.getTime().getTime());
		return nextday;
	}

	/**
	 * 频率单位 年，获取下一个执行日期
	 * 
	 * @param pmline
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private Calendar getNextdate_year(MboRemote pmline) throws RemoteException,
			MXException {
		if (pmline.isNull("nextdate")) {
			return null;
		}
		if (pmline.isNull("SCHEDULE") || pmline.getInt("SCHEDULE") <= 0) {
			return null;
		}
		Calendar nextday = Calendar.getInstance();
		nextday.setTime(pmline.getDate("nextdate"));
		do {
			nextday.add(1, pmline.getInt("SCHEDULE"));
			try {
				nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday
						.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (nextday.getTime().getTime() <= this.today.getTime().getTime());
		return nextday;
	}

	/**
	 * 更新设置下一个执行日期
	 * 
	 * @param pmlineid
	 * @param nextdate
	 * @throws RemoteException
	 * @throws MXException
	 * @throws SQLException
	 */
	private void setNextdate(long pmlineid, Calendar nextdate)
			throws RemoteException, MXException, SQLException {

		if (nextdate == null) {
			println(">>>>>deactivate pmlineid===== " + pmlineid);
			// this.stmt =
			// this.con.prepareCall("update pm set status='不活动' where pmnum='" +
			// pmnum + "'");
		} else {
			println(">>>>>setNextdate pmlineid===== " + pmlineid);
			println(">>>>>setNextdate="
					+ this.dtfmt1.format(nextdate.getTime()));
			this.stmt.setString(1, this.dtfmt1.format(nextdate.getTime()));
			this.stmt.setLong(2, pmlineid);
		}
		this.stmt.execute();
		this.con.commit();
	}

	/**
	 * <p>
	 * 取得任务参数：<br>
	 * siteid ：地点（每个任务实例只负责计算一个地点，简化逻辑，且减少错误影响面）<br>
	 */
	private void readConfig() throws RemoteException, MXException {
		siteid = getParamAsString("siteid");
	}

	public CrontaskParamInfo[] getParameters() throws MXException,
			RemoteException {
		try {
			String names[] = { "siteid" };
			String defs[] = { "修改成对应的地点" };
			String descriptions[][] = { { "cronprepr", "CronParamSiteid" } };
			CrontaskParamInfo ret[] = new CrontaskParamInfo[names.length];
			for (int i = 0; i < names.length; i++) {
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
		if (this.debug) {
			System.out.println(str);
		}
	}

}