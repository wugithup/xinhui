package com.shuto.mam.webclient.beans.jntz;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 
 * @Title: JnyxlrAppBean.java
 * @Description: TODO
 * @author: lull lull@shuto.cn
 * @date: 2017年3月29日 下午3:26:22
 * @version: V1.0.0
 */

public class JnyxlrAppBean extends AppBean {
	// com.shuto.mam.webclient.beans.tz.jngl.jntz.JnyxlrAppBean
	/**
	 * 
	 * <p>
	 * 
	 * @Title: YXZB
	 * 
	 * @Description: TODO
	 * @return
	 * @throws MXException
	 * @throws RemoteException
	 */
	public int YXZB() throws MXException, RemoteException {
		MboRemote Mbo = getMbo();
		String applyid = Mbo.getString("JNYXZBNUM");
		MboSetRemote jnzbwhs = Mbo.getMboSet("&JN_ZBWH", "JN_ZBWH", "type='运行指标'");
		if (!jnzbwhs.isEmpty()) {
			MboRemote jnzbwh = jnzbwhs.getMbo(0);
			MboSetRemote jnzbwhlines = jnzbwh.getMboSet("JN_ZBWHLINE");
			jnzbwhlines.setOrderBy("SN");
			if (!jnzbwhlines.isEmpty()) {
				MboSetRemote jnyxzblines = Mbo.getMboSet("JN_YXZBLINE");
				if (jnyxzblines.isEmpty()) {
					for (int i = 0; i < jnzbwhlines.count(); i++) {
						MboRemote jnzbwhmbo = jnzbwhlines.getMbo(i);
						MboRemote jnyxzbmbo = jnyxzblines.add();
						jnyxzbmbo.setValue("jnyxzbnum", applyid, 11L);
						jnyxzbmbo.setValue("sn",
								new Double(((MboSet) jnyxzbmbo.getThisMboSet()).max("sn")).intValue() + 1, 11L);
						jnyxzbmbo.setValue("zbmc", jnzbwhmbo.getString("zbmc"), 11L);
						jnyxzbmbo.setValue("dw", jnzbwhmbo.getString("dw"), 11L);
					}

					super.save();
				}
			}

		}

		return 1;
	}
}