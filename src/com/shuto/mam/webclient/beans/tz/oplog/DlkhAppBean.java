package com.shuto.mam.webclient.beans.tz.oplog;

/**  
 com.shuto.mam.webclient.beans.tz.oplog.DlkhAppBean 阜阳
 TZ_DLKH 电监会考核电量
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年6月15日 下午8:05:16
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

import com.shuto.mam.webclient.beans.base.CAppBean;

public class DlkhAppBean extends CAppBean {
	public void TJ() throws RemoteException, MXException {
		MboRemote Mbo = this.app.getAppBean().getMbo();
		String type = Mbo.getString("TYPE");
		if ("电监会考核".equals(type)) {
			getMbo().setValue("STATUS", "已关闭", 11L);
			super.SAVE();
		}
	}
}
