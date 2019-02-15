package com.shuto.mam.webclient.beans.om.pm;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * com.shuto.mam.webclient.beans.om.pm.OppmaqAppBean 华东大区 阜阳项目 安全定期工作维护页面类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月28日 下午3:05:21
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class OppmaqAppBean extends AppBean {
	public int INSERT() throws MXException, RemoteException {
		super.INSERT();
		getMbo().setValue("WORKTYPE", "AQPM", 11L);
		getMbo().setValue("LOCATION", "FYDC", 11L);
		return 1;
	}

	public void TZ() throws RemoteException, MXException {
		String DateStr = getMbo().getString("TZDATE");
		if (!"".equals(DateStr)) {
			super.SAVE();
			Date NewDate = getMbo().getDate("TZDATE");
			getMbo().setValue("NEXTDATE", NewDate, 11L);
			super.SAVE();
		}
	}
}