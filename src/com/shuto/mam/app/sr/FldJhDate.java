package com.shuto.mam.app.sr;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年7月5日 下午4:24:35
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldJhDate extends MboValueAdapter {
	public FldJhDate(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		MboValue EndDate = getMboValue("S_ENDTIME");
		MboValue StartDate = getMboValue("S_ZBNXCSJ");
		if (EndDate.isNull() || StartDate.isNull())
			return;
		Date ENDDATE = EndDate.getDate();
		Date STARTDATE = StartDate.getDate();
		if (STARTDATE.after(ENDDATE))
			throw new MXApplicationException("contract", "endDateBeforeToday");
		else
			return;
	}

}
