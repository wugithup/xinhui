package com.shuto.mam.webclient.beans.asset.assettz;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.beans.common.AssetLocDrilldownBean;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.asset.assettz.LocDrilldownBean 设备台帐
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年5月8日 下午5:01:28
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class LocDrilldownBean extends AssetLocDrilldownBean {
	public static boolean init = false;

	protected void initialize() throws MXException, RemoteException {
		super.initialize();
		init = true;
		MboRemote drilldown = getMboSet().getMbo(0);
		drilldown.setValue("systemid",
				this.getParent().getString("profession"), 2L);
		drilldown.setValue("locvalue", this.getParent().getString("location"),
				2L);
		DataBean resultsBean = this.app.getDataBean("locations_tree");
		resultsBean.resetQbe();
	}
}
