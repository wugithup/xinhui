/**
 * 自定义基础AppBean
 */
package com.shuto.mam.webclient.beans.base;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * @author Administrator
 *
 */
/**
 *
 com.shuto.mam.webclient.beans.base.BaseAppBean 江苏
 *
 * @author zhaowei zhaowei@shuoto.cn
 * @date 2017年5月17日 上午9:25:44
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class OpenrqAppBean extends AppBean {

	public int OPENREPORT() {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			MboSetRemote reportSet = null;
			if (mbo == null) {
				String mboname = this.app.getAppBean().getMboName();
				MboSetRemote mboSet = MXServer.getMXServer().getMboSet(mboname,
						MXServer.getMXServer().getSystemUserInfo());
				mbo = mboSet.getMbo(0);
			}
			reportSet = mbo.getMboSet("RQREPORT");
			if (reportSet.getMbo(0) != null && reportSet.getMbo(1) == null) {
				MboRemote reportMbo = reportSet.getMbo(0);
				String dialogID = reportMbo.getString("DIALOGID");
				String rqreportname = reportMbo.getString("RQREPORTNAME");// 报表名
				String rqreportnum = reportMbo.getString("rqreportnum");// 报表编号
				String remark = reportMbo.getString("REMARK"); // 备注
				System.out.println(remark);
				if (!"".equals(dialogID)) {
					this.clientSession.setAttribute("rqreportname",
							rqreportname);
					this.clientSession.setAttribute("rqreportnum", rqreportnum);
					this.clientSession.setAttribute("remark", remark);
					this.clientSession.loadDialog(dialogID);
					return 0;
				}
				StringBuffer url = new StringBuffer(MXServer.getMXServer()
						.getProperty("mxe.runqian.url"));
				url.append("/" + rqreportname.toLowerCase() + ".rpx");
				MboSetRemote rqreportset = mbo.getMboSet("$RQREPORTPARAM",
						"RQREPORTPARAM", "rqreportnum = '" + rqreportnum + "'");
				if (!rqreportset.isEmpty()) {
					for (int i = 0; i < rqreportset.count(); i++) {
						url.append("&");
						url.append(rqreportset.getMbo(i).getString("PARAMNAME")
								.toLowerCase());
						url.append("=");
						url.append(mbo.getString(rqreportset.getMbo(i)
								.getString("PARAMVALUE")));
					}

				}
				rqreportset.close();
				this.app.openURL(url.toString(), true);
			} else if (reportSet.count() > 1) {
				this.clientSession.loadDialog("RQREPORT1");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return 1;
	}
}
