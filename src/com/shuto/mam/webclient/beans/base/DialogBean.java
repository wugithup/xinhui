/**
* @Title: DialogBean.java
* @Package com.shuto.mam.webclient.beans.base
* @Description: TODO(用一句话描述该文件做什么)
* @author lull lull@shuto.cn
* @date 2017年5月31日 下午5:58:33
* @version V1.0.0
*/
package com.shuto.mam.webclient.beans.base;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

import java.rmi.RemoteException;

/**
 * @ClassName: DialogBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年5月31日 下午5:58:33
 *
 */
public class DialogBean extends DataBean {
	protected void initialize() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.initialize();
	}

	public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
		return super.getMboSetRemote();
	}

	public int execute() throws MXException, RemoteException {
		super.execute();
		openReport();
		return 1;
	}

	public int openReport() throws MXException, RemoteException {
		String rqreportname = (String) clientSession.getAttribute("rqreportname");
		String rqreportNum = (String) clientSession.getAttribute("rqreportnum");
		MboRemote mbo = getMbo();
		MboSetRemote rqreportset = mbo.getMboSet("$RQREPORTPARAM", "RQREPORTPARAM",
				"rqreportnum = '" + rqreportNum + "'");
		StringBuffer url = new StringBuffer(MXServer.getMXServer().getProperty("mxe.runqian.url"));
		url.append("/" + rqreportname.toLowerCase() + ".rpx");
		if (!rqreportset.isEmpty()) {
			for (int i = 0; i < rqreportset.count(); i++) {
				url.append("&");
				url.append(rqreportset.getMbo(i).getString("PARAMNAME").toLowerCase());
				url.append("=");
				url.append(mbo.getString(rqreportset.getMbo(i).getString("PARAMVALUE")));
			}

		}
		rqreportset.close();
		this.app.openURL(url.toString(), true);
		clientSession.queueEvent(new WebClientEvent("dialogok", clientSession.getCurrentPageId(), "", clientSession));
		return 1;
	}

}
