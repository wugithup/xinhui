package com.shuto.mam.app.duty;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.duty.FldDutynote 江苏
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月17日 上午9:22:05
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class FldDutynote extends MboValueAdapter {
	public FldDutynote(MboValue mbo) throws MXException {
		super(mbo);
	}

	public void action() throws MXException, RemoteException {
		super.action();

		 Date date = new Date();
		 SimpleDateFormat dateformat = new
		 SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String time = dateformat.format(date);
		MboRemote mainMbo = this.getMboValue().getMbo();

		String rectif = mainMbo.getString("ISQD");
		// RECTIFICATION

		if ("N".equals(rectif)) {
			mainMbo.setFieldFlag("ZPPERSON", 7L, false);// 将值班人设为不只读
			mainMbo.setValue("QDTIME", ""  ,
					11l);
		} else {// 已经签到
		//	mainMbo.setFieldFlag("ZPPERSON", 7L, true);// 将值班人设为只读
			mainMbo.setValue("QDTIME", "" + time ,
					11l);
		}
	}

}
