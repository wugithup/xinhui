/**
* @Title: RqRoport.java
* @Package com.shuto.mam.webclient.beans.base
* @Description: TODO(用一句话描述该文件做什么)
* @author lull lull@shuto.cn
* @date 2017年5月31日 上午11:40:49
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
 * @ClassName: RqRoport
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年5月31日 上午11:40:49
 *
 */
public class RqReportBean extends DataBean {
	// com.shuto.mam.webclient.beans.base.RqReport
	protected void initialize() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.initialize();
	}

	public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
		MboSetRemote set = super.getMboSetRemote();
		set.setWhere(" appname = '" + clientSession.getCurrentAppId().toUpperCase()
				+ "' AND RQREPORTNUM IN (SELECT RQREPORTNUM FROM RQREPORTQX WHERE (TYPE = '人员组' AND USERGROUP IN (SELECT GROUPNAME FROM GROUPUSER WHERE USERID = :USER))OR (TYPE = '人员' AND USERNUM = :USER))");
		set.reset();
		return set;
	}

	public int execute() throws MXException, RemoteException {
		super.execute();
		return 1;
	}

	public void selectRqReport() throws RemoteException, MXException {
		String rqreportname = getMbo().getString("rqreportname");// 报表名
		String rqreportnum = getMbo().getString("rqreportnum");// 报表编号
		String remark = getMbo().getString("REMARK"); // 备注
		String dialogID = getMbo().getString("DIALOGID");
		MboRemote mbo = this.app.getAppBean().getMbo();
		if (!"".equals(dialogID)) {
			this.clientSession.setAttribute("rqreportname", rqreportname);
			this.clientSession.setAttribute("rqreportnum", rqreportnum);
			this.clientSession.setAttribute("remark", remark);
			this.clientSession.loadDialog(dialogID);
			return;
		}
		StringBuffer url = new StringBuffer(MXServer.getMXServer().getProperty("mxe.runqian.url"));
		url.append("/" + rqreportname.toLowerCase() + ".rpx");
		MboSetRemote rqreportset = getMbo().getMboSet("$RQREPORTPARAM", "RQREPORTPARAM",
				"rqreportnum = '" + rqreportnum + "'");
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

	}
}
