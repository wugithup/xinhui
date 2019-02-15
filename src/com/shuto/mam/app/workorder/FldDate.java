package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.controller.Utility;

/**
 * com.shuto.mam.app.workorder.FldDate 控股工作票时间判断
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年7月19日 下午9:53:27
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldDate extends MboValueAdapter {
	public FldDate(MboValue mbv) {
		super(mbv);
	}

	public void validate() throws MXException, RemoteException {
		if (getMboValue().isNull()) {
			return;
		}
		// 计划开始时间 要在策划时间之后
		MboValue schedStart = getMboValue("REPORTDATE");// 策划时间 只读不用绑定类
		MboValue schedFinish = getMboValue("SCHEDSTART");// 计划开始时间
		MboValue xksj = getMboValue("S_XKDATE");// 许可时间 只读 不用绑定类

		MboValue SJKGDATE = getMboValue("S_XKKSDATE");// 许可工作开始时间
		MboValue SJWCDATE = getMboValue("S_PZGZJSDATE");// 批准工作结束时间
		String app = getMboValue().getMbo().getThisMboSet().getApp();
		if ("HDWOTICKET".equals(app) || ("HZWOTICKET".equals(app))) {
			if ((!schedStart.isNull()) && (!schedFinish.isNull())
					&& (schedStart.getDate().after(schedFinish.getDate()))) {
				throw new MXApplicationException("提示", "计划开始时间要大于策划时间！");

			}

			if ((!xksj.isNull()) && (!SJKGDATE.isNull())
					&& (xksj.getDate().after(SJKGDATE.getDate()))) {
				throw new MXApplicationException("提示", "许可工作开始时间要大于许可时间！");
			}

			if ((!SJKGDATE.isNull()) && (!SJWCDATE.isNull())
					&& (SJKGDATE.getDate().after(SJWCDATE.getDate()))) {
				throw new MXApplicationException("提示", "批准工作结束时间要大于许可工作开始时间！");
			}
		}

	}

	public void action() throws MXException, RemoteException {

	}
}