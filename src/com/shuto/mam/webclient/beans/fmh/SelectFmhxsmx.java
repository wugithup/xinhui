package com.shuto.mam.webclient.beans.fmh;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.fmh.SelectFmhxsmx 华东大区 阜阳项目 副产品结算 结算明细列表选择弹出框类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月17日 下午3:01:10
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SelectFmhxsmx extends DataBean {
	public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String type = mainMbo.getString("JSTYPE");
		String COMPANY = mainMbo.getString("COMPANY.NAME");
		String sql = "rlpz='" + type + "' and gys='" + COMPANY
				+ "' and fmhjsnum is null";
		DataBean mx = app.getDataBean("MX");
		MboSetRemote dialogSet = mx.getMboSet();
		dialogSet.setWhere(sql);
		return super.getMboSetRemote();
	}

	protected void initialize() throws MXException, RemoteException {

		super.initialize();
	}

	public int execute() throws MXException, RemoteException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String mainnum = mainMbo.getString("FMHJSNUM");

		MboSetRemote fmhjslineset = mainMbo.getMboSet("fmhjsline");
		try {
			DataBean mx = app.getDataBean("MX");
			MboSetRemote dialogSet1 = mx.getMboSet();
			dialogSet1.resetWithSelection();
			MboRemote dialogMbo1 = null;
			MboRemote lineMbo = null;
			Double ZCZL = null;
			Double KCZL = null;
			Double JZ = null;
			Double UNITCOST = null;

			if (!dialogSet1.isEmpty()) {
				for (int a = 0; a < dialogSet1.count(); ++a) {
					dialogMbo1 = dialogSet1.getMbo(a);
					ZCZL = Double.valueOf(dialogMbo1.getDouble("mz"));
					KCZL = Double.valueOf(dialogMbo1.getDouble("pz"));
					JZ = Double.valueOf(dialogMbo1.getDouble("jzjhk"));
					UNITCOST = Double.valueOf(dialogMbo1.getDouble("UNITCOST"));
					lineMbo = fmhjslineset.add();
					lineMbo.setValue("ZCZL", ZCZL.doubleValue(), 2L);
					lineMbo.setValue("KCZL", KCZL.doubleValue(), 2L);
					lineMbo.setValue("JZ", JZ.doubleValue(), 2L);
					lineMbo.setValue("UNITCOST", UNITCOST.doubleValue(), 2L);
					lineMbo.setValue("FMHJSNUM", mainnum, 2L);
					lineMbo.setValue("zj",
							JZ.doubleValue() * UNITCOST.doubleValue(), 2L);
					dialogMbo1.setValue("fmhjsnum", mainnum);
					dialogMbo1.setValue("fmhjslinenum",
							lineMbo.getString("fmhjslinenum"));
				}
				dialogSet1.save();
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		app.getAppBean().save();
		return 1;
	}
}