package com.shuto.mam.app.sr;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年7月18日 下午4:38:22
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldYqDate extends MboValueAdapter {
	public FldYqDate(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		MboValue EndDate = getMboValue("S_ENDTIME"); // 计划结束时间
		MboValue YqDate = getMboValue("S_YQTODATE"); // 延期至时间
		if (EndDate.isNull() || YqDate.isNull())
			return;
		Date ENDDATE = EndDate.getDate();
		Date YQDATE = YqDate.getDate();
		if (ENDDATE.after(YQDATE)) {
			throw new MXApplicationException("ticket", "yqDateBeforeEndDate");
		}
		Date currentDT = MXServer.getMXServer().getDate(); // 当前系统时间
		if (currentDT.after(YQDATE)) {
			throw new MXApplicationException("ticket", "yqDateBeforeSysDate");
		}
	}
}
