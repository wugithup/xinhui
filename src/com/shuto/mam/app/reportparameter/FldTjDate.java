package com.shuto.mam.app.reportparameter;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年7月18日 下午10:10:21
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldTjDate extends MboValueAdapter {
	public FldTjDate(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		MboValue StartDate = getMboValue("DATEPARAM1"); //开始时间
		MboValue EndDate = getMboValue("DATEPARAM2"); // 结束时间
		if (EndDate.isNull() || StartDate.isNull())
			return;
		Date ENDDATE = EndDate.getDate();
		Date STARTDATE = StartDate.getDate();
		if (STARTDATE.after(ENDDATE)) {
			throw new MXApplicationException("report", "StartDateBeforeEndDate");
		}
	}
}
