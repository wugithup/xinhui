package com.shuto.mam.webclient.beans.sjsq;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * com.shuto.mam.webclient.beans.sjsq.ZyglAppBean 专业管理
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月14日 下午4:38:19
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class ZyglAppBean extends AppBean {
	@Override
	public int SAVE() throws MXException, RemoteException {
		MboRemote ThisMbo = app.getAppBean().getMbo();
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String time = dateformat.format(date);
		ThisMbo.setValue("CHANGEPERSON", ThisMbo.getUserName(), 11L);
		ThisMbo.setValue("CHANGEDATE", time, 11L);

		return super.SAVE();
	}
}