package com.shuto.mam.app.om.pm;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import psdi.app.pm.PM;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.om.pm.CronPMGenWO华东大区阜阳项目安全定期工作定时任务类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月31日 下午4:39:42
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class CronPMGenWO extends SimpleCronTask {
	public static final String ACTIVE = "活动";
	public static final String FIXEDDAY = "固定日期";
	public static final String WEEKDAY = "固定星期";
	public static final String SHIFT = "班次";
	public static final String HOURS = "小时";
	MXServer mxserver;
	UserInfo userInfo;
	SimpleDateFormat dtfmt = new SimpleDateFormat("yyyy年MM月dd日");

	public void init() throws MXException {
		try {
			System.out.println("================ CronPMGenWO ================");
			super.init();
			this.mxserver = MXServer.getMXServer();
			this.userInfo = getRunasUserInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cronAction() {
		System.out.println("================ CronPMGenWO ================");

		Calendar today = Calendar.getInstance();
		try {
			MboSetRemote pms = this.mxserver.getMboSet("pm", this.userInfo);
			pms.setWhere("status='活动'");
			for (int i = 0; i < pms.count(); ++i) {
				MboRemote pm = pms.getMbo(i);

				String frequnit = pm.getString("frequnit");
				int frequency = pm.getInt("frequency");
				String shiftnum = pm.getString("shiftnum");
				String fixedday = pm.getString("fixedday");
				int alertlead = pm.getInt("alertlead");

				today.add(5, alertlead);

				if (frequnit.equals("固定日期")) {
					if ((fixedday != null) && (!fixedday.equals(""))) {
						System.out.println("pm=" + pm.getString("pmnum")
								+ ", fixedday=" + fixedday);
						StringTokenizer st = new StringTokenizer(fixedday, ",");
						for (int j = 0; st.hasMoreTokens(); ++j) {
							String day = st.nextToken();
							System.out.println("fixedday." + j + "=" + day);
							if (today.get(5) == Integer.valueOf(day).intValue()) {
								System.out.println("generating...");
								((PM) pm).generateWork(false, 0);

								MboSetRemote wos = this.mxserver.getMboSet(
										"workorder", this.userInfo);
								wos.setWhere("pmnum='"
										+ pm.getString("pmnum")
										+ "' and targstartdate=trunc(sysdate,'DD')");
								wos.setOrderBy("wonum desc");
								if (wos.count() > 0) {
									CreateWoBz(wos, pm);
								}
								String Professional = pm
										.getString("PROFESSIONAL");
								String depnum = pm.getString("DEPNUM");
								if ("".equals(depnum)) {
									depnum = "1000228";
								}
								String worktype = pm.getString("WORKTYPE");
								if ("JXWO".equals(worktype)) {
									if (wos.count() > 0) {
										wos.getMbo(0).setValue("ZY",
												Professional, 11L);
										wos.getMbo(0).setValue("DEPNUM",
												depnum, 11L);
										wos.save();
									}
								}
								break;
							}
						}
					} else {
						System.out.println("error - doesn't config fixedday.");
					}

				} else if (frequnit.equals("固定星期")) {
					if ((fixedday != null) && (!fixedday.equals(""))) {
						System.out.println("pm=" + pm.getString("pmnum")
								+ ", fixedweekday=" + fixedday);
						StringTokenizer st = new StringTokenizer(fixedday, ",");
						for (int j = 0; st.hasMoreTokens(); ++j) {
							String weekday = st.nextToken();
							System.out
									.println("fixedweekday." + j + "="
											+ weekday + ", DAY_OF_WEEK="
											+ today.get(7));
							if (today.get(7) - 1 == Integer.valueOf(weekday)
									.intValue()) {
								System.out.println("generating...");
								((PM) pm).generateWork(false, 0);

								MboSetRemote wos = this.mxserver.getMboSet(
										"workorder", this.userInfo);
								wos.setWhere("pmnum='"
										+ pm.getString("pmnum")
										+ "' and targstartdate=trunc(sysdate,'DD')");
								wos.setOrderBy("wonum desc");
								if (wos.count() <= 0)
									break;
								wos.getMbo(0).setValue("SHIFTNUM",
										pm.getString("shiftnum"), 11L);
								wos.save();

								break;
							}
						}
					} else {
						System.out
								.println("error - doesn't config fixedweekday.");
					}
				} else if (frequnit.equals("班次")) {
					MboSetRemote shifts = this.mxserver.getMboSet("shift",
							this.userInfo);
					shifts.setWhere("starttime is not null");

					if ((shiftnum != null) && (!shiftnum.equals(""))) {
						System.out.println("pm=" + pm.getString("pmnum")
								+ ", shift=" + shiftnum);
						System.out.println("generating...");
						((PM) pm).generateWork(false, 0);

						MboSetRemote wos = this.mxserver.getMboSet("workorder",
								this.userInfo);
						wos.setWhere("pmnum='" + pm.getString("pmnum")
								+ "' and targstartdate=trunc(sysdate,'DD')");
						wos.setOrderBy("wonum desc");
						if (wos.count() > 0) {
							wos.getMbo(0).setValue("SHIFTNUM",
									pm.getString("shiftnum"), 11L);
							wos.save();
						}
					} else {
						System.out.println("pm=" + pm.getString("pmnum")
								+ ", shift");
						for (int j = 0; j < shifts.count(); ++j) {
							System.out.println("generating...");
							((PM) pm).generateWork(false, 0);

							MboSetRemote wos = this.mxserver.getMboSet(
									"workorder", this.userInfo);
							wos.setWhere("pmnum='" + pm.getString("pmnum")
									+ "' and targstartdate=trunc(sysdate,'DD')");
							wos.setOrderBy("wonum desc");
							if (wos.count() > 0) {
								wos.getMbo(0).setValue("SHIFTNUM",
										shifts.getMbo(j).getString("shiftnum"),
										11L);
								wos.save();
							}
						}
					}
				} else if (frequnit.equals("小时")) {
					System.out.println("pm=" + pm.getString("pmnum")
							+ ", hours");
					for (int j = 0; j < 24; j += frequency) {
						System.out.println("generating...");
						((PM) pm).generateWork(false, 0);
					}
				} else {
					System.out.println("----------Begin----------");
					System.out.println("Pmnum =" + pm.getString("pmnum")
							+ "  Loading ...");
					((PM) pm).generateWork(true, alertlead);
					MboSetRemote wos = this.mxserver.getMboSet("workorder",
							this.userInfo);
					wos.setWhere("pmnum='" + pm.getString("pmnum")
							+ "' and targstartdate=trunc(sysdate,'DD')");
					wos.setOrderBy("wonum desc");
					System.out.println("0");
					if (wos.count() > 0) {
						CreateWoBz(wos, pm);
					}
					String Professional = pm.getString("PROFESSIONAL");
					String depnum = pm.getString("DEPNUM");
					if ("".equals(depnum)) {
						depnum = "1000228";
					}
					String worktype = pm.getString("WORKTYPE");
					if ("JXWO".equals(worktype)) {
						if (wos.count() > 0) {
							wos.getMbo(0).setValue("ZY", Professional, 11L);
							wos.getMbo(0).setValue("DEPNUM", depnum, 11L);
							wos.save();
						}
					}
					System.out.println("----------Over----------");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SimpleDateFormat dtfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("=========== (" + dtfmt.format(new Date())
				+ ") ===========");
	}

	public void CreateWoBz(MboSetRemote WoSet, MboRemote PmMbo)
			throws RemoteException, MXException {

		MboSetRemote PmDetailSet = PmMbo.getMboSet("PMDETAIL");
		MboRemote PmDetailMbo = null;
		MboSetRemote BenchmarkbzSet = null;
		MboRemote BenchmarkbzMbo = null;
		MboRemote WoMbo = WoSet.getMbo(0);
		MboSetRemote BenchmarkbzWoSet = WoMbo.getMboSet("BENCHMARKBZ");
		MboRemote BenchmarkbzWoMbo = null;
		String benchmarkjdnum = "";
		String description = "";
		String unit = "";
		double up = 0.00;
		double down = 0.00;
		String wonum = WoMbo.getString("WONUM");
		if (!PmDetailSet.isEmpty()) {
			for (int i = 0; i < PmDetailSet.count(); i++) {
				PmDetailMbo = PmDetailSet.getMbo(i);
				BenchmarkbzSet = PmDetailMbo.getMboSet("BENCHMARKBZ");
				if (!BenchmarkbzSet.isEmpty()) {
					for (int ii = 0; ii < BenchmarkbzSet.count(); ii++) {
						BenchmarkbzMbo = BenchmarkbzSet.getMbo(ii);
						BenchmarkbzMbo = BenchmarkbzSet.getMbo(i);
						benchmarkjdnum = BenchmarkbzMbo
								.getString("BENCHMARKJDNUM");
						description = BenchmarkbzMbo.getString("DESCRIPTION");
						unit = BenchmarkbzMbo.getString("UNIT");
						up = BenchmarkbzMbo.getDouble("UP");
						down = BenchmarkbzMbo.getDouble("DOWN");
						BenchmarkbzWoMbo = BenchmarkbzWoSet.add();
						BenchmarkbzWoMbo.setValue("BENCHMARKJDNUM",
								benchmarkjdnum, 11L);
						BenchmarkbzWoMbo.setValue("DESCRIPTION", description,
								11L);
						BenchmarkbzWoMbo.setValue("UNIT", unit, 11L);
						BenchmarkbzWoMbo.setValue("UP", up, 11L);
						BenchmarkbzWoMbo.setValue("DOWN", down, 11L);
						BenchmarkbzWoMbo.setValue("WONUM", wonum, 11L);
					}
					BenchmarkbzWoSet.save();
				}
			}
		}
	}
}