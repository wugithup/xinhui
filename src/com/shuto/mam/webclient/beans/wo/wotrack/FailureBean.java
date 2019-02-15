package com.shuto.mam.webclient.beans.wo.wotrack;

import java.rmi.RemoteException;
import psdi.app.workorder.WORemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.RequestHandler;
import psdi.webclient.system.controller.Utility;

/**
 * 工单(H_WOTRACK)
 * 
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年5月18日 上午11:58:25
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FailureBean extends DataBean {
	@SuppressWarnings("deprecation")
	public int listfailurecodes() throws MXException, RemoteException {
		DataBean appBean = Utility.getDataSource(this.sessionContext, this.app.getAppHandler());
		try {
			appBean.save();
			WORemote wo = (WORemote) appBean.getMbo();
			if ((wo.getBoolean("HistoryFlag")) && (!(wo.isWOInEditHist()))) {
				String[] param = { wo.getString("wonum"), wo.getString("woclass") };
				Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "workorder", "WOHistory", param);
				return 1;
			}
		} catch (MXException mxe) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), mxe);
			return 1;
		}
		RequestHandler.showDialog(this.sessionContext, "wolistfailurecodes");
		return 1;
	}
}