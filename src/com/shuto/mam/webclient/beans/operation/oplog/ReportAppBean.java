package com.shuto.mam.webclient.beans.operation.oplog;

/**  
com.shuto.mam.webclient.beans.operation.oplog.ReportAppBean 河南分公司  登封电厂
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月4日 上午11:35:56
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import java.text.DecimalFormat;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class ReportAppBean extends AppBean {
	@Override
	public synchronized void insert() throws MXException, RemoteException {
		super.insert();
		MboRemote mRemote = getMbo();
		String sitied = mRemote.getString("SITEID");
		String ORGID = mRemote.getString("ORGID");
		String REPORTNUM = mRemote.getString("REPORTNUM");
		String appName = mRemote.getThisMboSet().getApp();
		MboSetRemote mboSet = mRemote.getMboSet("REPORTMAINTAIN");
		MboSetRemote mboSet_mb = mRemote.getMboSet("REPORTINFO_MB");
		MboRemote mboRemote = null;
		if ((mboSet_mb.count() > 0) && (mboSet.count() <= 0)) {
			for (int num = 0; num < mboSet_mb.count(); num++) {
				mboRemote = mboSet.add();
				mboRemote.setValue("ORDERNUM", mboSet_mb.getMbo(num).getString("ORDERNUM"));
				mboRemote.setValue("WATERSAMPLE", mboSet_mb.getMbo(num).getString("WATERSAMPLE"));
				mboRemote.setValue("PROJECT", mboSet_mb.getMbo(num).getString("PROJECT"));
				mboRemote.setValue("UNIT", mboSet_mb.getMbo(num).getString("UNIT"));
				mboRemote.setValue("STANDARD", mboSet_mb.getMbo(num).getString("STANDARD"));
				mboRemote.setValue("A_STANDARD", mboSet_mb.getMbo(num).getString("A_STANDARD"));
				mboRemote.setValue("B_STANDARD", mboSet_mb.getMbo(num).getString("B_STANDARD"));
				mboRemote.setValue("REPORTTYPE", mboSet_mb.getMbo(num).getString("REPORTTYPE"));
				mboRemote.setValue("SITEID", sitied);
				mboRemote.setValue("ORGID", ORGID);
				mboRemote.setValue("REPORTNUM", REPORTNUM);
				mboRemote.setValue("ISMAINTAIN", 0);
			}
		}
		mboSet = null;
		mboSet_mb = null;
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		super.SAVE();
		MboRemote mRemote = getMbo();
		String appName = mRemote.getThisMboSet().getApp();
		if (appName.equals("RLM")) {
			DecimalFormat format = new DecimalFormat("0.00");
			double R_QSF = mRemote.getDouble("R_QSF");
			double R_KGSF = mRemote.getDouble("R_KGSF");
			double R_KGJHF = mRemote.getDouble("R_KGJHF");
			double R_KGJHFF = mRemote.getDouble("R_KGJHFF");
			double R_DTFRL = mRemote.getDouble("R_DTFRL");
			double R_KGJQL = mRemote.getDouble("R_KGJQL");

			double R_SDJHF1 = (100.0D - R_QSF) / (100.0D - R_KGSF) * R_KGJHF;
			String R_SDJHF2 = format.format(R_SDJHF1);
			double R_SDJHFF1 = (100.0D - R_QSF) / (100.0D - R_KGSF) * R_KGJHFF;
			String R_SDJHFF2 = format.format(R_SDJHFF1);

			double R_GZWHJHFF1 = 100.0D / (100.0D - R_KGSF - R_KGJHF) * R_KGJHFF;
			String R_GZWHJHFF2 = format.format(R_GZWHJHFF1);

			double R_SDJQL1 = (100.0D - R_QSF) / (100.0D - R_KGSF) * R_KGJQL;
			String R_SDJQL2 = format.format(R_SDJQL1);
			double R_KGJGDT1 = 100.0D - R_KGSF - R_KGJHF - R_KGJHFF;
			String R_KGJGDT2 = format.format(R_KGJGDT1);

			double R_KGJQ1 = (R_KGJHFF * 100.0D / (R_KGJGDT1 + R_KGJHFF) * 7.8D
					/ (R_KGJHFF * 100.0D / (R_KGJGDT1 + R_KGJHFF) + 10.0D) - 0.013D) * (R_KGJGDT1 + R_KGJHFF) / 100.0D;
			String R_KGJQ2 = format.format(R_KGJQ1);
			double a;
			// double a;
			if (R_DTFRL > 16.699999999999999D) {
				// double a;
				if (R_DTFRL < 25.100000000000001D) {
					a = 0.0012D;
				} else
					a = 0.0016D;
			} else {
				a = 0.001D;
			}
			double R_FRL1 = ((R_DTFRL * 1000.0D - 94.099999999999994D * R_KGJQL - R_DTFRL * a * 1000.0D
					- 206.0D * R_KGJQ1) * (100.0D - R_QSF) / (100.0D - R_KGSF) - 23.0D * R_QSF) / 1000.0D;
			String R_FRL2 = format.format(R_FRL1);
			double R_FRL3 = R_FRL1 / 4.1816D * 1000.0D;
			String R_FRL4 = format.format(R_FRL3);

			mRemote.setValue("R_SDJHF", R_SDJHF2);
			mRemote.setValue("R_SDJHFF", R_SDJHFF2);
			mRemote.setValue("R_GZWHJHFF", R_GZWHJHFF2);
			mRemote.setValue("R_FRL1", R_FRL2);
			mRemote.setValue("R_FRL2", R_FRL4);
			mRemote.setValue("R_SDJQL", R_SDJQL2);
			mRemote.setValue("R_KGJQ", R_KGJQ2);
			mRemote.setValue("R_KGJGDT", R_KGJGDT2);
		} else if ((appName.equals("YQSQ")) || (appName.equals("EQSQ")) || (appName.equals("NJSJCL"))
				|| (appName.equals("EQNJSJ"))) {
			MboSetRemote mboSet = mRemote.getMboSet("REPORTMAINTAIN");
			for (int i = 0; i < mboSet.count(); i++) {
				double MAXVALUE = -100000.0D;
				double MINVALUE = 100000.0D;
				double CONTVALUE = 0.0D;
				double LINEALLVALUE = 0.0D;

				System.err.println("ORDERNUM:         " + mboSet.getMbo(i).getString("ORDERNUM"));
				String TIME2 = mboSet.getMbo(i).getString("TIME2");
				if (TIME2.equals(""))
					TIME2 = "99999999";
				double D_TIME2 = Double.parseDouble(TIME2);
				System.err.println("time2:" + D_TIME2);

				String TIME4 = mboSet.getMbo(i).getString("TIME4");
				if (TIME4.equals(""))
					TIME4 = "99999999";
				double D_TIME4 = Double.parseDouble(TIME4);

				String TIME6 = mboSet.getMbo(i).getString("TIME6");
				if (TIME6.equals(""))
					TIME6 = "99999999";
				double D_TIME6 = Double.parseDouble(TIME6);

				String TIME8 = mboSet.getMbo(i).getString("TIME8");
				if (TIME8.equals(""))
					TIME8 = "99999999";
				double D_TIME8 = Double.parseDouble(TIME8);

				String TIME10 = mboSet.getMbo(i).getString("TIME10");
				if (TIME10.equals(""))
					TIME10 = "99999999";
				double D_TIME10 = Double.parseDouble(TIME10);

				String TIME12 = mboSet.getMbo(i).getString("TIME12");
				if (TIME12.equals(""))
					TIME12 = "99999999";
				double D_TIME12 = Double.parseDouble(TIME12);

				String TIME14 = mboSet.getMbo(i).getString("TIME14");
				if (TIME14.equals(""))
					TIME14 = "99999999";
				double D_TIME14 = Double.parseDouble(TIME14);

				String TIME16 = mboSet.getMbo(i).getString("TIME16");
				if (TIME16.equals(""))
					TIME16 = "99999999";
				double D_TIME16 = Double.parseDouble(TIME16);

				String TIME18 = mboSet.getMbo(i).getString("TIME18");
				if (TIME18.equals(""))
					TIME18 = "99999999";
				double D_TIME18 = Double.parseDouble(TIME18);

				String TIME20 = mboSet.getMbo(i).getString("TIME20");
				if (TIME20.equals(""))
					TIME20 = "99999999";
				double D_TIME20 = Double.parseDouble(TIME20);

				String TIME22 = mboSet.getMbo(i).getString("TIME22");
				if (TIME22.equals(""))
					TIME22 = "99999999";
				double D_TIME22 = Double.parseDouble(TIME22);

				String TIME24 = mboSet.getMbo(i).getString("TIME24");
				if (TIME24.equals(""))
					TIME24 = "99999999";
				double D_TIME24 = Double.parseDouble(TIME24);

				double[] Array = { D_TIME2, D_TIME4, D_TIME6, D_TIME8, D_TIME10, D_TIME12, D_TIME14, D_TIME16, D_TIME18,
						D_TIME20, D_TIME22, D_TIME24 };
				for (int n = 0; n < Array.length; n++) {
					if (Array[n] != 99999999.0D) {
						if (Array[n] > MAXVALUE) {
							MAXVALUE = Array[n];
						}
						if (Array[n] < MINVALUE) {
							MINVALUE = Array[n];
						}

						CONTVALUE += 1.0D;
						double num = Array[n];
						System.err.println("Array" + n + "   :" + num);
						LINEALLVALUE += num;
					}
				}
				mboSet.getMbo(i).setValue("MAXVALUE", Double.toString(MAXVALUE));
				System.err.println("MAXVALUE==========;" + mboSet.getMbo(i).getString("MAXVALUE"));
				mboSet.getMbo(i).setValue("MINVALUE", Double.toString(MINVALUE));
				System.err.println("MINVALUE==========;" + mboSet.getMbo(i).getString("MINVALUE"));
				mboSet.getMbo(i).setValue("CONTVALUE", Double.toString(CONTVALUE));
				System.err.println("CONTVALUE==========;" + mboSet.getMbo(i).getString("CONTVALUE"));
				mboSet.getMbo(i).setValue("LINEALLVALUE", Double.toString(LINEALLVALUE));
				System.err.println("LINEALLVALUE==========;" + mboSet.getMbo(i).getString("LINEALLVALUE"));
			}
		} else if (appName.equals("FHLZHY")) {
			MboSetRemote mboSet = mRemote.getMboSet("REPORTMAINTAIN");
			for (int i = 0; i < mboSet.count(); i++) {
				double MAXVALUE = -100000.0D;
				double MINVALUE = 100000.0D;
				double CONTVALUE = 0.0D;
				double LINEALLVALUE = 0.0D;

				String TIME1 = mboSet.getMbo(i).getString("TIME1");
				if (TIME1.equals(""))
					TIME1 = "99999999";
				double D_TIME1 = Double.parseDouble(TIME1);

				String TIME2 = mboSet.getMbo(i).getString("TIME2");
				if (TIME2.equals(""))
					TIME2 = "99999999";
				double D_TIME2 = Double.parseDouble(TIME2);

				String TIME3 = mboSet.getMbo(i).getString("TIME3");
				if (TIME3.equals(""))
					TIME3 = "99999999";
				double D_TIME3 = Double.parseDouble(TIME3);
				double[] Array = { D_TIME1, D_TIME2, D_TIME3 };
				for (int n = 0; n < Array.length; n++) {
					if (Array[n] != 99999999.0D) {
						if (Array[n] > MAXVALUE) {
							MAXVALUE = Array[n];
						}
						if (Array[n] < MINVALUE) {
							MINVALUE = Array[n];
						}

						CONTVALUE += 1.0D;
						double num = Array[n];
						System.err.println("Array" + n + "   :" + num);
						LINEALLVALUE += num;
					}
				}
				mboSet.getMbo(i).setValue("MAXVALUE", Double.toString(MAXVALUE));
				mboSet.getMbo(i).setValue("MINVALUE", Double.toString(MINVALUE));
				mboSet.getMbo(i).setValue("CONTVALUE", Double.toString(CONTVALUE));
				mboSet.getMbo(i).setValue("LINEALLVALUE", Double.toString(LINEALLVALUE));
			}
		}
		return super.SAVE();
	}

	public static void main(String[] args) {
		double[] Array = { 1.23D, 2.1D, 5.32D, -3.0D, 6.34D, 3.0D, 1.22D, 3.22D, 9.0D, 11.109999999999999D };
		double MIN = 10000.0D;
		for (int i = 0; i < Array.length; i++)
			if (Array[i] != 3.22D) {
				System.err.println(i);
			}
	}
}