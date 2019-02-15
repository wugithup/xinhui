package com.shuto.mam.webclient.beans.wo.wotrack;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年5月18日 下午9:49:23
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class SeleteWorkorderBean extends DataBean {
	protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		MboSetRemote mboSet = super.getMboSetRemote();

		if (mboSet != null && !mboSet.isEmpty()) {
			MboRemote mbo = app.getAppBean().getMbo();
			mboSet.setWhere("istask = 0 and siteid='" + mbo.getString("SITEID") + "' and wonum <> '" + mbo.getString("WONUM")
					+ "' and wonum not in (select wonum from workorder where parent = '" + mbo.getString("WONUM") + "')");
			mboSet.reset();
		}
		return mboSet;
	}

	public synchronized int execute() throws MXException, RemoteException {
		MboRemote mainMbo = app.getAppBean().getMbo();
		MboSetRemote thisMboSet = getMboSet();
		thisMboSet.resetWithSelection();// 获取选择的数据
		for (int i = 0; i < thisMboSet.count(); i++) {
			MboRemote zgdwonum = thisMboSet.getMbo(i);
			zgdwonum.setValue("PARENT", mainMbo.getString("WONUM"), 11L);
		}
		return super.execute();
	}
}
