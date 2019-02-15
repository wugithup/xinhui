package com.shuto.mam.webclient.beans.tz.oplog;

/**  
 com.shuto.mam.webclient.beans.tz.oplog.Dlkh1AppBean 阜阳
 TZ_DLKH1 省经委考核电量
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年6月15日 下午8:10:02
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

import com.shuto.mam.webclient.beans.base.CAppBean;

public class Dlkh1AppBean extends CAppBean {
	public void TJ() throws RemoteException, MXException {
		MboRemote Mbo = this.app.getAppBean().getMbo();
		String type = Mbo.getString("TYPE");
		if ("省经委考核".equals(type)) {
			getMbo().setValue("STATUS", "已关闭", 11L);
			super.SAVE();
		}
	}
}