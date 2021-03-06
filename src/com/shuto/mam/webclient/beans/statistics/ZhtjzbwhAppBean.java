/**
 *
 */
package com.shuto.mam.webclient.beans.statistics;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import com.shuto.mam.webclient.beans.base.BaseAppBean;
import com.shuto.mam.webclient.beans.rqreport.RqGetProperty;

/**
 * @author Administrator
 *
 */
public class ZhtjzbwhAppBean extends AppBean {
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

	/**
	 * 生成报表SQL
	 */
	public void REPORTSQL() {
		try {
			MboSetRemote dailyreporttargets = getMbo().getMboSet(
					"DAILYREPORTTARGET");
			dailyreporttargets.setOrderBy("ordernum");
			StringBuffer sql = new StringBuffer("select reportdatanum" + "\r\n");
			if (!dailyreporttargets.isEmpty()) {
				MboRemote reporttarget = null;
				for (int i = 0; i < dailyreporttargets.count(); i++) {
					reporttarget = dailyreporttargets.getMbo(i);
					sql.append(",max(case when reporttargetnum='"
							+ reporttarget.getString("num")
							+ "' then value end) "
							+ reporttarget.getString("num") + "\r\n");
				}
				sql.append(" from dailyreporttargetdata where reportdatanum='' group by reportdatanum");
			}
			getMbo().setValue("reportsql", sql.toString());
			super.SAVE();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 调用润乾报表打印
	 *
	 * @return
	 */

	public int RQREPORT() {
		MboRemote mbo;
		try {
			mbo = this.app.getAppBean().getMbo();
			MboSetRemote reportSet = null;
			if (mbo == null) {
				String mboname = this.app.getAppBean().getMboName();
				MboSetRemote mboSet = MXServer.getMXServer().getMboSet(mboname,
						MXServer.getMXServer().getSystemUserInfo());
				mbo = mboSet.getMbo(0);
			}
			reportSet = mbo.getMboSet("RQREPORT");
			if (reportSet.count() == 1) {
				MboRemote reportMbo = reportSet.getMbo(0);
				String rqreportname = reportMbo.getString("RQREPORTNAME");
				String wherepara = reportMbo.getString("WHEREPARA");
				StringBuffer urlbf = new StringBuffer();
				RqGetProperty getrqurl = new RqGetProperty();
				String rqurl = getrqurl.getProperty("rq.url") + "";
				urlbf.append(rqurl);

				if (!"".equals(wherepara)) {
					String[] where = wherepara.split(",");
					urlbf.append(rqreportname);
					for (int j = 0; j < where.length; j++) {
						String[] whereson = where[j].split("=:");
						urlbf.append("&").append(whereson[0]).append("=")
								.append(mbo.getString(whereson[1]));
					}
				} else {
					urlbf.append(rqreportname);
				}
				this.app.openURL(urlbf.toString(), true);
			} else if (reportSet.count() > 1) {
				this.clientSession.loadDialog("RQREPORT");
			} else {
				throw new MXApplicationException("oplog", "该应用暂无可用报表");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return 1;
	}
}
