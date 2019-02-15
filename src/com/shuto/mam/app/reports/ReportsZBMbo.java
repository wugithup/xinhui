package com.shuto.mam.app.reports;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**  
com.shuto.mam.app.reports.ReportsZBMbo 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 上午11:08:43
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class ReportsZBMbo extends Mbo implements ReportsZBMboRemote {

	public ReportsZBMbo(MboSet ms) throws RemoteException {
		super(ms);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param date
	 *            生成该日期的报表
	 * @throws RemoteException
	 * @throws MXException
	 */
	public String jisuanValue(Date date) throws RemoteException, MXException {

		MboRemote mbo = this;
		if (date == null) {
			throw new MXApplicationException("", "请先输入'用于测试该时间按对应公式计算值'!");
		}

		String FORTYPE = mbo.getString("FORTYPE"); // 公式类型
		SimpleDateFormat testSdf = new SimpleDateFormat("yyyy-MM-dd");
		if (FORTYPE.equals("报表公式")) {

			MboSetRemote rfMboSet = mbo.getMboSet("REPORTSFORMULA");
			rfMboSet.setOrderBy("SEQNUM desc");
			rfMboSet.reset();
			Evaluator eval = new Evaluator();
			ZhibiaoTuuyou zt = new ZhibiaoTuuyou();
			String tongjiType = "";

			boolean jisuan = true;

			for (int i = 0; i < rfMboSet.count(); i++) {
				MboRemote rfMbo = rfMboSet.getMbo(i);
				String type = rfMbo.getString("TYPE");
				String statisticsType = rfMbo.getString("STATISTICSTYPE");
				String reportId = rfMbo.getString("GETREPORTID");
				String indicatorId = rfMbo.getString("GETINDICATORID");
				String value = "0";
				tongjiType = rfMbo.getString("GETREPORTSNAME.TONGJITYPE");
				if ((!rfMbo.toBeDeleted()) && ("报表".equals(type))) {
					SqlFormat sql = null;
					if (("直接取值".equals(statisticsType))
							|| ("昨天/上月/去年".equals(statisticsType))) {
						Date getDate = zt.formatDate(date, tongjiType,
								statisticsType);
						sql = new SqlFormat("REPORTSID=:1 and MKDATE=:2");
						sql.setObject(1, "reportsdata", "REPORTSID", reportId);
						sql.setObject(2, "reportsdata", "MKDATE",
								testSdf.format(getDate));
						MboSetRemote getDataSet1 = MXServer.getMXServer()
								.getMboSet("reportsdata",
										getThisMboSet().getUserInfo());
						getDataSet1.setWhere(sql.format());
						getDataSet1.reset();
						int getDataSetCount = getDataSet1.count();
						if (getDataSetCount == 1) {
							String reportsdataId = getDataSet1.getMbo(0)
									.getString("REPORTSDATAID");
							MboSetRemote getDataZBSet = MXServer.getMXServer()
									.getMboSet("reportszbdata",
											getThisMboSet().getUserInfo());
							sql = new SqlFormat(
									"REPORTSZBID=:1 and REPORTSDATAID=:2");
							sql.setObject(1, "reportszbdata", "REPORTSZBID",
									indicatorId);
							sql.setObject(2, "reportszbdata", "REPORTSDATAID",
									reportsdataId);
							getDataZBSet.setWhere(sql.format());
							getDataZBSet.reset();
							if (getDataZBSet.count() == 1) {
								if (getDataZBSet.getMbo(0).isNull("VALUE")) {
									rfMbo.setValue("RESULT", "日期报表对应的指标値为空", 2L);
									jisuan = false;
								} else {
									value = getDataZBSet.getMbo(0).getDouble(
											"VALUE")
											+ "";
									rfMbo.setValue("RESULT", value, 2L);
								}
							} else if (getDataZBSet.count() == 0) {
								rfMbo.setValue("RESULT", "无该日期报表对应的指标", 2L);
								jisuan = false;
							}

							getDataZBSet.close();
						} else if (getDataSetCount == 0) {
							rfMbo.setValue("RESULT", "无该日期报表", 2L);

							jisuan = false;
						}

						getDataSet1.close();
					} else if (("月累计".equals(statisticsType))
							|| ("年累计".equals(statisticsType))) {
						Date getDate = zt.formatDate(date, tongjiType,
								statisticsType);
						sql = new SqlFormat(
								"REPORTSZBID=:1 and reportsdataid in (select reportsdataid from reportsdata where MKDATE between :2 and :3 and REPORTSID=:4)");

						sql.setObject(1, "reportszbdata", "REPORTSZBID",
								indicatorId);
						Calendar cal = Calendar.getInstance();
						cal.setTime(getDate);
						cal.set(5, 1);
						if ("年累计".equals(statisticsType)) {
							cal.set(2, 0);
						}

						sql.setObject(2, "reportsdata", "MKDATE",
								testSdf.format(cal.getTime()));
						sql.setObject(3, "reportsdata", "MKDATE",
								testSdf.format(getDate));
						sql.setObject(4, "reportsdata", "REPORTSID", reportId);
						MboSetRemote getDataSet = MXServer.getMXServer()
								.getMboSet("reportsZBdata",
										getThisMboSet().getUserInfo());

						getDataSet.setWhere(sql.format());
						getDataSet.reset();
						if (!getDataSet.isEmpty()) {
							value = getDataSet.sum("value") + "";
						}
						getDataSet.close();
					} else {
						rfMbo.setValue("RESULT", "次统计类型未启用", 2L);
						return "";
					}
				}

				eval.putVariable(rfMbo.getString("VARIABLE"), value);
			}
			String formulaValue = "";
			rfMboSet.close();
			try {
				if (mbo.isNull("FORMULA")) {
					return "";
				}
				if (jisuan) {
					formulaValue = eval.evaluate(mbo.getString("FORMULA"));
					int format = mbo.getInt("format");
					if (format > 0) {
						String formats = "#0.";
						for (int i = 0; i < format; i++) {
							formats = formats + "0";
						}
						DecimalFormat df = new DecimalFormat(formats);
						try {
							formulaValue = df.format(new BigDecimal(
									formulaValue));
						} catch (Exception e) {
							formulaValue = "";
						}
					}
				}

				mbo.setValue("RESULT", formulaValue, 2L);
			} catch (EvaluationException localEvaluationException) {
			}
			return formulaValue;
		}

		if (FORTYPE.equals("SQL公式")) {
			String sqlstr = mbo.getString("SQLSTR");
			if (sqlstr.equals("")) {
				throw new MXApplicationException("configure", "BlankMsg",
						new String[] { "请填写SQL公式！" });
			}

			String tongjitype = mbo.getString("REPORTSNAME.TONGJITYPE");
			Connection con = ((Mbo) mbo).getMboServer().getDBConnection(
					mbo.getUserInfo().getConnectionKey());
			try {
				SimpleDateFormat df = null;
				if (tongjitype.equals("日")) {
					df = new SimpleDateFormat("yyyy-MM-dd");
				} else if (tongjitype.equals("月")) {
					df = new SimpleDateFormat("yyyy-MM");
				} else if (tongjitype.equals("年")) {
					df = new SimpleDateFormat("yyyy");
				}
				//System.out.println(mbo.getInt("REPORTSZBID"));
				String datestr = df.format(date);
				sqlstr = sqlstr.replace(":mkdate", "'" + datestr + "'"); // 替换SQL公式中变量
				SqlFormat sfsql = null;
				Statement state1 = con.createStatement(); // 创建数据流
				sfsql = new SqlFormat(sqlstr);
				ResultSet rs = state1.executeQuery(sfsql.format());
				rs.next();
				String gsjg = rs.getString(1).toString();// 取第一列值
				state1.close();

				//根据设置数值位数进行约数
				int format = mbo.getInt("format");
				if (format > 0) {
					String formats = "#0.";
					for (int i = 0; i < format; i++) {
						formats = formats + "0";
					}
					DecimalFormat ddf = new DecimalFormat(formats);
					try {
						gsjg = ddf.format(new BigDecimal(
								gsjg));
					} catch (Exception e) {
						gsjg = "0";
					}
				}
				mbo.setValue("RESULT", gsjg, 2L);
				return gsjg;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

}
