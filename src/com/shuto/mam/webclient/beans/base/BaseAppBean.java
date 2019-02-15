/**
 * 自定义基础AppBean
 */
package com.shuto.mam.webclient.beans.base;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import com.shuto.mam.webclient.beans.rqreport.RqGetProperty;

/**
 * @author Administrator
 *
 */
/**
 * 
com.shuto.mam.webclient.beans.base.BaseAppBean 江苏
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月17日 上午9:25:44
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class BaseAppBean extends CAppBean {

	/**
	 * 调用润乾报表打印
	 * @return
	 */
	
	public int RQREPORT() {
		MboRemote mbo;
		try {
			mbo = this.app.getAppBean().getMbo();
			MboSetRemote reportSet = null;
			if (mbo==null) {
				String mboname = this.app.getAppBean().getMboName();
				MboSetRemote mboSet = MXServer.getMXServer().getMboSet(mboname, MXServer.getMXServer().getSystemUserInfo());
				mbo = mboSet.getMbo(0);
			} 
			reportSet = mbo.getMboSet("RQREPORT");
			if (reportSet.count()==1) {
				MboRemote reportMbo = reportSet.getMbo(0);
				String rqreportname = reportMbo.getString("RQREPORTNAME");
				String wherepara = reportMbo.getString("WHEREPARA");
				StringBuffer urlbf = new StringBuffer();
				RqGetProperty getrqurl = new RqGetProperty();
				String rqurl = getrqurl.getProperty("rq.url")+"";
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
			} else if (reportSet.count()>1) {
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
