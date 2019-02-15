package com.shuto.mam.webclient.beans.asset.assettz;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.beans.common.DrilldownTreeBean;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.asset.assettz.LocDrilldownTreeBean 设备台帐
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年5月8日 下午5:01:47
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class LocDrilldownTreeBean extends DrilldownTreeBean {

	protected void initialize() throws MXException, RemoteException {
		super.initialize();
		if (LocDrilldownBean.init) {
			this.setuniqueidvalue(this.getMboSet().getMbo(0)
					.getString("locationsid"));
			setRefreshTree(true);
			LocDrilldownBean.init = false;
		}
		reloadTables();
	}

	/**
	 * 刷新各子表
	 */
	private void reloadTables() {
		String[] tables = new String[] { "specifications_childloc_table",
				"jxls_childloc_table", "childloc_table", "sr_table",
				"workordr_table", "matu_table", "woticket_table" };
		DataBean resultsBean = null;
		for (int i = 0; i < tables.length; i++) {
			resultsBean = this.app.getDataBean(tables[i]);
			if (resultsBean != null) {
				resultsBean.reloadTable();
			}
		}
	}
}
