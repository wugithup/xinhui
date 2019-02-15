package com.shuto.mam.app.operation.workorder;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldOplogWork extends MboValueAdapter {
	public FldOplogWork(MboValue mbv) {
		super(mbv);
	}

	public void action() throws MXException, RemoteException {
		super.action();

		MboRemote oppmmbo = getMboValue().getMbo();
		MboRemote oplog = oppmmbo.getOwner();

		boolean finish = oppmmbo.getBoolean("OPLOG_FINISH");
		boolean delay = oppmmbo.getBoolean("OPLOG_DELAY");
		boolean exception = oppmmbo.getBoolean("OPLOG_EXCEPTION");
		boolean cancel = oppmmbo.getBoolean("OPLOG_CANCELYN");

		String[] finish_attr_rd = { "OPLOG_DELAYCAUSE", "OPLOG_EXCEPCAUSE", "OPLOG_QXPERSON", "OPLOG_CANCELCAUSE", "S_YQTODATE" };
		String[] delay_attr_rd = { "OPLOG_RXPERSON", "OPLOG_ZXDATE", "OPLOG_ZXSITUATION", "OPLOG_EXCEPCAUSE", "OPLOG_QXPERSON", "OPLOG_CANCELCAUSE" };
		String[] exception_attr_rd = { "OPLOG_ZXDATE", "OPLOG_ZXSITUATION", "OPLOG_DELAYCAUSE", "OPLOG_QXPERSON", "OPLOG_CANCELCAUSE", "S_YQTODATE" };
		String[] cancel_attr_rd = { "OPLOG_RXPERSON", "OPLOG_ZXDATE", "OPLOG_ZXSITUATION", "OPLOG_DELAYCAUSE", "OPLOG_EXCEPCAUSE", "S_YQTODATE" };

		String[] finish_attr_rq = { "OPLOG_RXPERSON", "OPLOG_ZXDATE", "OPLOG_ZXSITUATION" };
		String[] delay_attr_rq = { "OPLOG_DELAYCAUSE", "S_YQTODATE" };
		String[] exception_attr_rq = { "OPLOG_EXCEPCAUSE" };
		String[] cancel_attr_rq = { "OPLOG_QXPERSON", "OPLOG_CANCELCAUSE" };

		if (finish) {
			oppmmbo.setValue("OPLOG_DELAY", 0);
			oppmmbo.setValue("OPLOG_EXCEPTION", 0);
			oppmmbo.setValue("OPLOG_CANCELYN", 0);

			oppmmbo.setFieldFlag(finish_attr_rd, 7L, true);
			oppmmbo.setFieldFlag(finish_attr_rq, 128L, true);
			oppmmbo.setValue("OPLOG_STATUS", "完成");
		} else if (delay) {
			oppmmbo.setValue("OPLOG_FINISH", 0);
			oppmmbo.setValue("OPLOG_EXCEPTION", 0);
			oppmmbo.setValue("OPLOG_CANCELYN", 0);

			oppmmbo.setFlag(7L, false);
			oppmmbo.setFieldFlag(delay_attr_rd, 7L, true);
			oppmmbo.setFieldFlag(delay_attr_rq, 128L, true);
			oppmmbo.setValue("OPLOG_STATUS", "延期");
			oppmmbo.setValue("CWOZB", oplog.getString("ZHIBIE"));
		} else if (exception) {
			oppmmbo.setValue("OPLOG_DELAY", 0);
			oppmmbo.setValue("OPLOG_FINISH", 0);
			oppmmbo.setValue("OPLOG_CANCELYN", 0);
			oppmmbo.setFlag(7L, false);
			oppmmbo.setFieldFlag(exception_attr_rd, 7L, true);
			oppmmbo.setFieldFlag(exception_attr_rq, 128L, true);
			oppmmbo.setValue("OPLOG_STATUS", "异常");
			oppmmbo.setValue("CWOZB", oplog.getString("ZHIBIE"));
		} else if (cancel) {
			oppmmbo.setValue("OPLOG_DELAY", 0);
			oppmmbo.setValue("OPLOG_FINISH", 0);
			oppmmbo.setValue("OPLOG_EXCEPTION", 0);
			oppmmbo.setFlag(7L, false);
			oppmmbo.setFieldFlag(cancel_attr_rd, 7L, true);
			oppmmbo.setFieldFlag(cancel_attr_rq, 128L, true);
			oppmmbo.setValue("OPLOG_STATUS", "取消");
			oppmmbo.setValue("CWOZB", oplog.getString("ZHIBIE"));
		} else {
			oppmmbo.setFlag(7L, false);
			oppmmbo.setFieldFlag(finish_attr_rd, 7L, false);
			oppmmbo.setFieldFlag(finish_attr_rq, 128L, false);

			oppmmbo.setFieldFlag(delay_attr_rd, 7L, false);
			oppmmbo.setFieldFlag(delay_attr_rq, 128L, false);

			oppmmbo.setFieldFlag(exception_attr_rd, 7L, false);
			oppmmbo.setFieldFlag(exception_attr_rq, 128L, false);

			oppmmbo.setFieldFlag(cancel_attr_rd, 7L, false);
			oppmmbo.setFieldFlag(cancel_attr_rq, 128L, false);
			oppmmbo.setValue("OPLOG_STATUS", "生成");
			oppmmbo.setValue("CWOZB", "");
		}
	}
}