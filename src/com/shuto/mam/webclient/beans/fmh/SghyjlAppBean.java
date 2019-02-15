package com.shuto.mam.webclient.beans.fmh;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * com.shuto.mam.webclient.beans.fmh.SghyjlAppBean 华东大区 阜阳项目 石膏化验记录页面类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月7日 下午3:03:01
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */

public class SghyjlAppBean extends AppBean {

	@Override
	public int INSERT() throws MXException, RemoteException {
		super.INSERT();
		MboRemote mainmbo = this.app.getAppBean().getMbo();
		Date date = MXServer.getMXServer().getDate();
		SimpleDateFormat format1 = new SimpleDateFormat("YYYY");
		SimpleDateFormat format2 = new SimpleDateFormat("MM");
		String year = format1.format(date);
		String month = format2.format(date);
		mainmbo.setValue("YEAR", year);
		mainmbo.setValue("MONTH", month);
		super.SAVE();
		return 1;
	}

	@Override
	public int SAVE() throws MXException, RemoteException {

		MboRemote mainmbo = this.app.getAppBean().getMbo();
		MboSetRemote msr = mainmbo.getMboSet("SGHYJLLINE");
		double sumsf = 0;
		for (int i = 0; i < msr.count(); i++) {
			double sf = msr.getMbo(i).getDouble("SF");
			sumsf = sumsf + sf;
		}
		mainmbo.setValue("AVGSF", sumsf / msr.count(), 11L);
		// mainmbo.setValue("BCZKSF",
		// sumsf / msr.count() - mainmbo.getDouble("ZKSF"), 11L);
		if (mainmbo.getDouble("BCZKSF") < 0) {// 本次折扣
			mainmbo.setValue("BCZKSF", 0, 11L);
		}
		return super.SAVE();

	}
}