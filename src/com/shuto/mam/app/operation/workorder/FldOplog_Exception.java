package com.shuto.mam.app.operation.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年8月3日 下午4:43:04
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldOplog_Exception extends MboValueAdapter {
	public FldOplog_Exception(MboValue mbv) {
		super(mbv);
	}

	public void action() throws MXException, RemoteException {
		super.action();

		MboRemote oppmmbo = getMboValue().getMbo();
		MboRemote oplog = oppmmbo.getOwner();

		boolean exception = oppmmbo.getBoolean("OPLOG_EXCEPTION");
		String[] exception_attr_rd = { "OPLOG_ZXDATE", "OPLOG_ZXSITUATION", "OPLOG_DELAYCAUSE", "OPLOG_QXPERSON", "OPLOG_CANCELCAUSE", "S_YQTODATE" };

		String[] exception_attr_rq = { "OPLOG_EXCEPCAUSE" };
		if (exception) {
			System.out.println("异常");
			oppmmbo.setValue(("OPLOG_DELAY"), "0", 11l);
			oppmmbo.setValue(("OPLOG_FINISH"), "0", 11l);
			oppmmbo.setValue(("OPLOG_CANCELYN"), "0", 11l);
			oppmmbo.setFlag(7L, false);
			oppmmbo.setFieldFlag(exception_attr_rd, 7L, true);
			oppmmbo.setFieldFlag(exception_attr_rq, 128L, true);
			oppmmbo.setValue("OPLOG_STATUS", "异常", 11l);
			oppmmbo.setValue("CWOZB", oplog.getString("ZHIBIE"), 11l);
		}
	}
}
