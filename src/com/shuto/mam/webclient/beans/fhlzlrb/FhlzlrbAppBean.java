package com.shuto.mam.webclient.beans.fhlzlrb;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.icu.math.BigDecimal;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 *
 * com.shuto.mam.webclient.beans.fhlzlrb.FhlzlrbAppBean.java
 * 
 * Email:xiamy@shuto.cn 2017年8月30日 下午10:43:26
 *
 */
public class FhlzlrbAppBean extends AppBean {

	public void makeZiBiao() throws RemoteException, MXException, SQLException {
		if (hasReport()) {
			throw new MXApplicationException("", "该日期录入记录,不能重复创建!");
		}
		SAVE();
		MboRemote mbo = getMbo();
		Date mkDate = mbo.getDate("mkdate");// 创建日期
		MboSetRemote mboSet = mbo.getMboSet("FHLZLRZB");
		if (mboSet.isEmpty()) {
			MboRemote add = mboSet.add();
			add.setValue("bc", "夜班", 2L);
			add.setValue("zb", getZhiBie(mkDate, "夜班"), 2L);
			add.setValue("FHLZLRBID", mbo.getString("FHLZLRBID"), 2L);
			add = mboSet.add();
			add.setValue("bc", "白班", 2L);
			add.setValue("zb", getZhiBie(mkDate, "白班"), 2L);
			add.setValue("FHLZLRBID", mbo.getString("FHLZLRBID"), 2L);
			add = mboSet.add();
			add.setValue("bc", "中班", 2L);
			add.setValue("zb", getZhiBie(mkDate, "中班"), 2L);
			add.setValue("FHLZLRBID", mbo.getString("FHLZLRBID"), 2L);
		} else {
			MboRemote add = mboSet.add();
			add.setValue("FHLZLRBID", mbo.getString("FHLZLRBID"), 2L);
		}

		this.app.getAppBean().reloadTable();
		this.app.getAppBean().refreshTable();
	}

	public int getZhiBie(Date mkDate, String banci) throws RemoteException, MXException, SQLException {
		MboSetRemote indicator_dbbSet = app.getAppBean().getMbo().getMboSet("$INDICATOR_DBB", "INDICATOR_DBB");
		indicator_dbbSet.setOrderBy("jiezhitime");
		indicator_dbbSet.reset();
		if (indicator_dbbSet.isEmpty()) {
			return 1;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate;
		int zhibie = 0;
		try {
			startdate = sdf.parse(indicator_dbbSet.getMbo(0).getString("jiezhitime"));
			long diff = mkDate.getTime() - startdate.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			zhibie = (int) (days % 10)*4;
			if ("夜班".equals(banci)) {
				zhibie = indicator_dbbSet.getMbo(zhibie + 1).getInt("zhibie");
			} else if ("白班".equals(banci)) {
				zhibie = indicator_dbbSet.getMbo(zhibie + 2).getInt("zhibie");
			} else {
				zhibie = indicator_dbbSet.getMbo(zhibie + 3).getInt("zhibie");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		indicator_dbbSet.close();
		return zhibie;
	}

	public boolean hasReport() throws RemoteException, MXException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		MboRemote mbo = getMbo();
		Date mkdate = mbo.getDate("mkdate");
		if (mkdate == null) {
			return false;
		}
		SqlFormat sql = new SqlFormat(" MKDATE=:1  and FHLZLRBID!=:2");
		sql.setObject(1, "FHLZLRB", "MKDATE", sdf.format(mkdate));
		sql.setObject(2, "FHLZLRB", "FHLZLRBID", mbo.getString("FHLZLRBID"));
		MboSetRemote rdSet = mbo.getMboSet("$FHLZLRB", "FHLZLRB", sql.format());
		if (rdSet.count() > 0) {
			rdSet.close();
			return true;
		}
		rdSet.close();
		return false;
	}

	public void save() throws MXException {
		super.save();
		BigDecimal f1 = new BigDecimal("0.00");
		BigDecimal f2 = new BigDecimal("0.00");
		BigDecimal f1b = new BigDecimal("0.00");
		BigDecimal f2b = new BigDecimal("0.00");
		BigDecimal g = new BigDecimal("0.00");
		BigDecimal h = new BigDecimal("0.00");
		try {
			MboRemote mbo = getMbo();
			MboSetRemote mboSet = mbo.getMboSet("FHLZLRZB");

			if (!mboSet.isEmpty()) {
				for (int i = 0; i < mboSet.count(); ++i) {
					f1 = new BigDecimal(mboSet.getMbo(i).getDouble("YLFH"));
					f2 = new BigDecimal(mboSet.getMbo(i).getDouble("ELFH"));
					f1b = new BigDecimal(mboSet.getMbo(i).getDouble("YLFH2"));
					f2b = new BigDecimal(mboSet.getMbo(i).getDouble("ELFH2"));
					g = new BigDecimal(mboSet.getMbo(i).getDouble("DWFRL"));
					h = new BigDecimal(mboSet.getMbo(i).getDouble("HF2"));

					double hfss1 = 0.0D;
					double hfss2 = 0.0D;
					double hfss1b = 0.0D;
					double hfss2b = 0.0D;

					if ((g.doubleValue() == 0.0D) || (g == null)) {
						mboSet.getMbo(i).setValue("HFSS", hfss1, 11L);
						mboSet.getMbo(i).setValue("HFSS2", hfss2, 11L);
						mboSet.getMbo(i).setValue("HFSSB", hfss1b, 11L);
						mboSet.getMbo(i).setValue("HFSS2B", hfss2b, 11L);
					} else {
						BigDecimal js = new BigDecimal("337.27");
						BigDecimal js2 = new BigDecimal("4.18");

						hfss1 = js.divide(g.multiply(js2), 4, 4).multiply(f1.multiply(h)).doubleValue();
						hfss2 = js.divide(g.multiply(js2), 4, 4).multiply(f2.multiply(h)).doubleValue();
						hfss1b = js.divide(g.multiply(js2), 4, 4).multiply(f1b.multiply(h)).doubleValue();
						hfss2b = js.divide(g.multiply(js2), 4, 4).multiply(f2b.multiply(h)).doubleValue();

						mboSet.getMbo(i).setValue("HFSS", hfss1, 11L);
						mboSet.getMbo(i).setValue("HFSS2", hfss2, 11L);
						mboSet.getMbo(i).setValue("HFSSB", hfss1b, 11L);
						mboSet.getMbo(i).setValue("HFSS2B", hfss2b, 11L);
					}
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void TJ() throws RemoteException, MXException {
		getMbo().setValue("STATUS", "关闭", 11L);
		super.SAVE();
	}

}