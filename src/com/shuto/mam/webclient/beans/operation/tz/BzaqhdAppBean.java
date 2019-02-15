package com.shuto.mam.webclient.beans.operation.tz;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
/**
 *   班组安全活动记录         TZ_BZAQHD
 com.shuto.mam.webclient.beans.operation.tz.BzaqhdAppBean 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:24:33
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class BzaqhdAppBean extends CAppBean {

	public BzaqhdAppBean() {
	}

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
		long l = getMbo().getLong("TZ_PXWDID"); 
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
						"ownertable='TZ_PXWD' and ownerid='" + l
								+ "' and active = 1");
		if (!(mbosetremote.isEmpty())) {
			String s2 = "ownerid='"
					+ l
					+ "' and ownertable='TZ_PXWD' and assignstatus='活动' and assigncode='"
					+ s1 + "'";
			MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode",
					"WFASSIGNMENT", s2);
			return (!(mbosetremote1.isEmpty()));
		}
		return false;
	}
}
