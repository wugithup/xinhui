/**
 * <p>
 * 此类为供应商档案的AppBean类
 * 
 * @author HaoXing
 * @date 2013-6-8 下午04:17:49
 * 
 * @version Ver 1.0
 */

package com.shuto.mam.webclient.beans.rl.rlgysda;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * com.shuto.mam.webclient.beans.rl.rlgysda.RlGysDaAppBean华东大区 阜阳项目 副产品供应商档案
 * 用于删除时同时删除子表数据
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月7日 下午2:36:43
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class RlGysDaAppBean extends AppBean {
	/**
	 * <p>
	 * 删除方法，将子项级联删除
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 */
	@Override
	public int DELETE() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		// 主对象
		MboRemote mainMbo = app.getAppBean().getMbo();
		// 子表：供应商矿点
		mainMbo.getMboSet("rlgyszdline").deleteAll();
		// 子表：供应商站点
		// mainMbo.getMboSet("rlgyszd").deleteAll();
		// 子表：联系人
		mainMbo.getMboSet("contact").deleteAll();
		// 子表：分支结构
		mainMbo.getMboSet("companyparent").deleteAll();
		mainMbo.delete();
		return super.DELETE();
	}

	public int SAVE() throws MXException, RemoteException {
		MboRemote AppMbo = app.getAppBean().getMbo();
		MboSetRemote RlxgjlSet = AppMbo.getMboSet("RLXGJL");
		MboRemote RlxgjlMbo = null;
		RlxgjlMbo = RlxgjlSet.add();
		RlxgjlMbo.setValue("PERSONID", AppMbo.getUserInfo().getPersonId(), 11l);
		RlxgjlMbo.setValue("DATEXG", new Date(), 11l);
		RlxgjlMbo.setValue("APP", AppMbo.getThisMboSet().getApp(), 11l);
		RlxgjlMbo.setValue("NUM", AppMbo.getString("COMPANY"), 11l);
		BigDecimal ZYFCOST = new BigDecimal("0");
		BigDecimal ZYFCOSTS = new BigDecimal("0");
		/**
		 * 统计运杂费
		 */
		MboSetRemote RLCOMPYZF = AppMbo.getMboSet("RLCOMPYZF");
		if (!RLCOMPYZF.isEmpty()) {
			for (int i = 0; i < RLCOMPYZF.count(); i++) {
				MboRemote yzf = RLCOMPYZF.getMbo(i);
				if (!yzf.getString("ZYFCOST").isEmpty()) {
					ZYFCOST = new BigDecimal(yzf.getString("ZYFCOST").replace(
							",", ""));
				} else {
					ZYFCOST = new BigDecimal("0");
				}
				ZYFCOSTS = ZYFCOSTS.add(ZYFCOST);
				MboSetRemote RLCOMPYZFMX1 = yzf.getMboSet("RLCOMPYZFMX1");
				MboSetRemote RLCOMPYZFMX2 = yzf.getMboSet("RLCOMPYZFMX2");
				MboSetRemote RLCOMPYZFMX3 = yzf.getMboSet("RLCOMPYZFMX3");
				if (!RLCOMPYZFMX1.isEmpty()) {
					yzf.setValue("yfcost", RLCOMPYZFMX1.sum("ZYFCOST"));
				}
				if (!RLCOMPYZFMX2.isEmpty()) {
					yzf.setValue("FWCOST", RLCOMPYZFMX2.sum("ZYFCOST"));
				}
				double dtcost = 0.00d;
				for (int ii = 0; ii < RLCOMPYZFMX2.count(); ii++) {
					String type = RLCOMPYZFMX2.getMbo(ii).getString("type");
					if (type.equals("地铁费")) {
						dtcost = dtcost
								+ RLCOMPYZFMX2.getMbo(ii).getDouble("ZYFCOST");
					}
				}
				yzf.setValue("ZYFCOST",
						yzf.getDouble("yfcost") + yzf.getDouble("fwcost")
								+ RLCOMPYZFMX3.sum("ZYFCOST"));
				yzf.setValue("DTCOST", dtcost);
			}
			ZYFCOSTS = ZYFCOSTS.divide(new BigDecimal(RLCOMPYZF.count()), 2,
					BigDecimal.ROUND_HALF_UP);
			AppMbo.setValue("YZF", ZYFCOSTS.toString());
		} else {
			AppMbo.setValue("YZF", 0);
		}
		return super.SAVE();
	}

}
