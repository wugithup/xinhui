
package com.shuto.mam.webclient.beans.jsjgl;

import java.rmi.RemoteException;


import com.shuto.mam.webclient.beans.base.BaseAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.ResultsBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
/**
 * 脚手架管理     JSJGL
 com.shuto.mam.webclient.beans.jsjgl.JsjglAppBean 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月16日 下午10:40:46
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
// 权限设置Bean；
public class JsjglAppBean extends BaseAppBean {
	
	/* 保存 */
	@Override
	public int SAVE() throws MXException, RemoteException {
		if (!(hasAuth())) {
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");
		}
		
		return super.SAVE();
	}

	/* 发送工作流 */
	public int ROUTEWF() throws MXException, RemoteException {
		if (!(hasAuth())) {
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");
		}
		MboRemote mbo = app.getAppBean().getMbo();
		String status = mbo.getString("status");

		if (status.equals("已关闭")) {// 工作流关闭不能再次发送
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");

		}
		return super.ROUTEWF();

	}

	public boolean hasAuth() throws MXException, RemoteException {
		long l = getMbo().getLong("JSJGLID"); 
		String s = getMbo().getString("STATUS");
		String s1 = getMbo().getUserInfo().getPersonId();
		if (s1.equalsIgnoreCase("maxadmin"))
			return true;
		if ("新建".equals(s))
			return true;
		MboSetRemote mbosetremote = getMbo()
				.getMboSet(
						"$instance",
						"WFINSTANCE",
						"ownertable='JSJGL' and ownerid='" + l
								+ "' and active = 1");
		if (!(mbosetremote.isEmpty())) {
			String s2 = "ownerid='"
					+ l
					+ "' and ownertable='JSJGL' and assignstatus='活动' and assigncode='"
					+ s1 + "'";
			MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode",
					"WFASSIGNMENT", s2);
			return (!(mbosetremote1.isEmpty()));
		}
		return false;
	}
}