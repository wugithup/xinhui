package com.shuto.mam.app.reports;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.app.reports.FldRDZBIsFormula 华东大区 阜阳项目
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月21日 下午3:20:48
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldRDZBIsFormula extends MboValueAdapter {

	public FldRDZBIsFormula(MboValue mbv) {
		super(mbv);
	}

	public void initValue() throws MXException, RemoteException {
		super.initValue();
		if (getMboValue().getMbo().isNull("REPORTSZBNAME.FORMULA")
				&& getMboValue().getMbo().isNull("REPORTSZBNAME.SQLSTR")) {
			getMboValue().setValue("N", DataBean.ATTR_READONLY);
		} else {
			getMboValue().setValue("Y", DataBean.ATTR_READONLY);
		}
	}
}
