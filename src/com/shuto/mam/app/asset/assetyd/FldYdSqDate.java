package com.shuto.mam.app.asset.assetyd;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.asset.assetyd.FldYdSqDate 设备异动时间校验
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年5月16日 下午1:55:32
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldYdSqDate extends MboValueAdapter {
	public FldYdSqDate(MboValue mbv) {
		super(mbv);
	}

	public void validate() throws MXException, RemoteException {
		if (getMboValue().isNull()) {
			return;
		}
		// 计划时间
		MboValue schedStart = getMboValue("STARTDATE");
		MboValue schedFinish = getMboValue("ENDDATE");
		// 实际时间
		MboValue SJKGDATE = getMboValue("SJKGDATE");
		MboValue SJWCDATE = getMboValue("SJWCDATE");

		if ((!schedStart.isNull()) && (!schedFinish.isNull())
				&& (schedStart.getDate().after(schedFinish.getDate()))) {
			throw new MXApplicationException("workorder", "StartAfterFinish");
		}

		if ((!SJKGDATE.isNull()) && (!SJWCDATE.isNull())
				&& (SJKGDATE.getDate().after(SJWCDATE.getDate()))) {
			throw new MXApplicationException("workorder", "StartAfterFinish");
		}

	}

	public void action() throws MXException, RemoteException {

	}
}