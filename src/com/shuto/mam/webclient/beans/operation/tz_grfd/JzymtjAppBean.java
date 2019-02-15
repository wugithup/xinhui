package com.shuto.mam.webclient.beans.operation.tz_grfd;

import java.math.BigDecimal;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 机组用煤统计        TZ_JZYMTJ
 com.shuto.mam.webclient.beans.operation.tz_grfd.JzymtjAppBean 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:48:49
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class JzymtjAppBean extends AppBean {

	public int INSERT() throws MXException, RemoteException {
		return super.INSERT();
	}

	// 存煤=当天来煤-当天一二期合计用煤 =1的时候

	// 存煤=前一天存煤+当天来煤-当天一二期合计用煤
	// MCCM MCCM LAIMEI
	public int SAVE() throws MXException, RemoteException {
		// 获得当前Mbo
		MboRemote mbo = this.app.getAppBean().getMbo();
		String siteid = mbo.getString("siteid");
		String TZ_JZYMTJNUM = mbo.getString("TZ_JZYMTJNUM");
 
		// 获得结果集
		MboSetRemote gykgdztempSet = mbo.getMboSet("TZ_JZYMTJLINE");
		int acount = gykgdztempSet.count();
		if (!gykgdztempSet.isEmpty()) {// 如果数据不为空

			MboSetRemote gykgdztempSet1 = null;
			for (int i = 0; i < acount; i++) {
				String bsjz1 = gykgdztempSet.getMbo(i).getString("JLDATE");
				double YQYM = gykgdztempSet.getMbo(i).getDouble("YQYM");
				double ERYM = gykgdztempSet.getMbo(i).getDouble("ERYM");
				double yeqymtj = YQYM + ERYM;
				gykgdztempSet.getMbo(i).setValue("YEQYM", yeqymtj, 2L);
				if (gykgdztempSet.getMbo(i).getString("MCCM") == null
						|| "".equals(gykgdztempSet.getMbo(i).getString("MCCM"))) {
					gykgdztempSet1 = mbo.getMboSet("#TZ_JZYMTJLINE","TZ_JZYMTJLINE", "siteid='" + siteid+ "'  and JLDATE = to_date('" + bsjz1+ "','yyyy-MM-dd') -1  ");
					if (gykgdztempSet1.isEmpty()) {
						double LAIMEI = gykgdztempSet.getMbo(i).getDouble("LAIMEI");
						double YEQYM = gykgdztempSet.getMbo(i).getDouble("YEQYM");
						BigDecimal a1 = new BigDecimal(LAIMEI);
						BigDecimal a2 = new BigDecimal(YEQYM);
						BigDecimal CMONE = a1.subtract(a2);
						gykgdztempSet.getMbo(i).setValue("MCCM", CMONE + "", 2L);
					}
					if (!gykgdztempSet1.isEmpty()) {
						double LAIMEINEW = gykgdztempSet.getMbo(i).getDouble("LAIMEI");
						double YEQYMNEW = gykgdztempSet.getMbo(i).getDouble("YEQYM");
						String MCCMNEW = gykgdztempSet1.getMbo(0).getString("MCCM");
						BigDecimal ac1 = new BigDecimal(LAIMEINEW);
						BigDecimal ac2 = new BigDecimal(YEQYMNEW);
						BigDecimal ac3 = new BigDecimal(MCCMNEW);
						BigDecimal CMTWO = ac1.subtract(ac2);
						BigDecimal ZXCM = CMTWO.add(ac3);

						gykgdztempSet.getMbo(i).setValue("MCCM", ZXCM + "", 2L);
					}
					gykgdztempSet1.close();
				}
			}

			gykgdztempSet.close();
		}
		return super.SAVE();
	}
}
