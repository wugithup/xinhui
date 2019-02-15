package com.shuto.mam.app.operation.workorder;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年8月3日 下午5:56:05
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldOplog_Cpmstatus extends MboValueAdapter {

	String[] finish_attr_rd = { "S_YQTODATE", "OPLOG_QXPERSON", "OPLOG_CANCELCAUSE", "OPLOG_DELAYCAUSE","S_YQPERSON",
			"OPLOG_EXCEPCAUSE" };
	String[] finish_attr_rq = { "OPLOG_RXPERSON", "OPLOG_ZXDATE", "OPLOG_ZXSITUATION" };

	String[] cancel_attr_rd = { "S_YQTODATE", "OPLOG_RXPERSON", "OPLOG_ZXDATE", "OPLOG_ZXSITUATION", "OPLOG_DELAYCAUSE","S_YQPERSON",
			"OPLOG_EXCEPCAUSE" };
	String[] cancel_attr_rq = { "OPLOG_QXPERSON", "OPLOG_CANCELCAUSE" };

	String[] delay_attr_rd = { "OPLOG_RXPERSON", "OPLOG_ZXDATE", "OPLOG_ZXSITUATION", "OPLOG_QXPERSON",
			"OPLOG_CANCELCAUSE", "OPLOG_EXCEPCAUSE" };
	// String[] delay_attr_rq = { "OPLOG_DELAYCAUSE", "S_YQTODATE" };
	String[] delay_attr_rq = { "OPLOG_DELAYCAUSE"  , "S_YQTODATE" ,"S_YQPERSON"};

	String[] exception_attr_rd = { "OPLOG_RXPERSON", "OPLOG_ZXDATE", "OPLOG_ZXSITUATION", "OPLOG_QXPERSON","S_YQPERSON",
			"OPLOG_CANCELCAUSE", "S_YQTODATE", "OPLOG_DELAYCAUSE" };
	String[] exception_attr_rq = { "OPLOG_EXCEPCAUSE" };

	public FldOplog_Cpmstatus(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void init() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.init();
		MboRemote oppmmbo = getMboValue().getMbo();
		String cpmstatus = oppmmbo.getString("CPMSTATUS");
		if ("完成".equals(cpmstatus)) {
			// 设置取消字段为非必填
			oppmmbo.setFieldFlag(cancel_attr_rq, 128L, false);
			// 设置延期字段为非必填
			oppmmbo.setFieldFlag(delay_attr_rq, 128L, false);
			// 设置异常字段为非必填
			oppmmbo.setFieldFlag(exception_attr_rq, 128L, false);
			// 设置完成字段为非只读
			oppmmbo.setFieldFlag(finish_attr_rq, 7L, false);

			// 设置非完成字段为只读
			oppmmbo.setFieldFlag(finish_attr_rd, 7L, true);
			// 设置完成字段为必填
			oppmmbo.setFieldFlag(finish_attr_rq, 128L, true);
		} else if ("取消".equals(cpmstatus)) {
			// 设置完成字段为非必填
			oppmmbo.setFieldFlag(finish_attr_rq, 128L, false);
			// 设置延期字段为非必填
			oppmmbo.setFieldFlag(delay_attr_rq, 128L, false);
			// 设置异常字段为非必填
			oppmmbo.setFieldFlag(exception_attr_rq, 128L, false);
			// 设置取消字段为非只读
			oppmmbo.setFieldFlag(cancel_attr_rq, 7L, false);

			// 设置非取消字段为只读
			oppmmbo.setFieldFlag(cancel_attr_rd, 7L, true);
			// 设置取消字段为必填
			oppmmbo.setFieldFlag(cancel_attr_rq, 128L, true);
		} else if ("延期".equals(cpmstatus)) {
			// 设置取消字段为非必填
			oppmmbo.setFieldFlag(cancel_attr_rq, 128L, false);
			// 设置完成字段为非必填
			oppmmbo.setFieldFlag(finish_attr_rq, 128L, false);
			// 设置异常字段为非必填
			oppmmbo.setFieldFlag(exception_attr_rq, 128L, false);
			// 设置延期字段为非只读
			oppmmbo.setFieldFlag(delay_attr_rq, 7L, false);

			// 设置非延期字段为只读
			oppmmbo.setFieldFlag(delay_attr_rd, 7L, true);
			// 设置延期字段为必填
			oppmmbo.setFieldFlag(delay_attr_rq, 128L, true);
		} else if ("异常".equals(cpmstatus)) {
			// 设置取消字段为非必填
			oppmmbo.setFieldFlag(cancel_attr_rq, 128L, false);
			// 设置延期字段为非必填
			oppmmbo.setFieldFlag(delay_attr_rq, 128L, false);
			// 设置完成字段为非必填
			oppmmbo.setFieldFlag(finish_attr_rq, 128L, false);
			// 设置异常字段为非只读
			oppmmbo.setFieldFlag(exception_attr_rq, 7, false);

			// 设置非异常字段为只读
			oppmmbo.setFieldFlag(exception_attr_rd, 7L, true);
			// 设置异常字段为必填
			oppmmbo.setFieldFlag(exception_attr_rq, 128L, true);
		}
	}

	@Override
    public void action() throws MXException, RemoteException {
		super.action();
		MboRemote oppmmbo = getMboValue().getMbo();
		String appname = getMboValue().getMbo().getThisMboSet().getApp();
	    //当前登录人
		String login2 = oppmmbo.getUserInfo().getPersonId();
		Date  newDate = new Date();
		//变更状态修改执行人和执行时间
		oppmmbo.setValue("OPLOG_RXPERSON", login2, 11L);
		oppmmbo.setValue("OPLOG_ZXDATE", newDate, 11L);
		// 如果应用为oplog就赋值值别
		if ("OPLOG".equals(appname)) {
			MboRemote oplog = oppmmbo.getOwner();
			oppmmbo.setValue("CWOZB", oplog.getString("ZHIBIE"));
		}
		String cpmstatus = oppmmbo.getString("CPMSTATUS");
		oppmmbo.setFlag(7L, false);
		oppmmbo.setFlag(128L, false);
		oppmmbo.setValue("OPLOG_STATUS", cpmstatus, 11L);
		//oppmmbo.setValueNull("OPLOG_RXPERSON", 11L);
		//oppmmbo.setValueNull("OPLOG_ZXDATE", 11L);
		oppmmbo.setValueNull("OPLOG_ZXSITUATION", 11L);
		oppmmbo.setValueNull("OPLOG_QXPERSON", 11L);
		oppmmbo.setValueNull("OPLOG_CANCELCAUSE", 11L);
		oppmmbo.setValueNull("OPLOG_DELAYCAUSE", 11L);
		oppmmbo.setValueNull("OPLOG_EXCEPCAUSE", 11L);
		if ("完成".equals(cpmstatus)) {
			// 设置取消字段为非必填
			oppmmbo.setFieldFlag(cancel_attr_rq, 128L, false);
			// 设置延期字段为非必填
			oppmmbo.setFieldFlag(delay_attr_rq, 128L, false);
			// 设置异常字段为非必填
			oppmmbo.setFieldFlag(exception_attr_rq, 128L, false);
			// 设置完成字段为非只读
			oppmmbo.setFieldFlag(finish_attr_rq, 7L, false);

			// 设置非完成字段为只读
			oppmmbo.setFieldFlag(finish_attr_rd, 7L, true);
			// 设置完成字段为必填
			oppmmbo.setFieldFlag(finish_attr_rq, 128L, true);
		} else if ("取消".equals(cpmstatus)) {
			//变更状态修改执行人和执行时间
//			oppmmbo.setValue("OPLOG_RXPERSON", login2, 11L);
//			oppmmbo.setValue("OPLOG_ZXDATE", newDate, 11L);
			// 设置完成字段为非必填
			oppmmbo.setFieldFlag(finish_attr_rq, 128L, false);
			// 设置延期字段为非必填
			oppmmbo.setFieldFlag(delay_attr_rq, 128L, false);
			// 设置异常字段为非必填
			oppmmbo.setFieldFlag(exception_attr_rq, 128L, false);
			// 设置取消字段为非只读
			oppmmbo.setFieldFlag(cancel_attr_rq, 7L, false);

			// 设置非取消字段为只读
			oppmmbo.setFieldFlag(cancel_attr_rd, 7L, true);
			// 设置取消字段为必填
			oppmmbo.setFieldFlag(cancel_attr_rq, 128L, true);
		} else if ("延期".equals(cpmstatus)) {
//			//变更状态修改执行人和执行时间
//			oppmmbo.setValue("OPLOG_RXPERSON", login2, 11L);
//			oppmmbo.setValue("OPLOG_ZXDATE", newDate, 11L);
			// 设置取消字段为非必填
			oppmmbo.setFieldFlag(cancel_attr_rq, 128L, false);
			// 设置完成字段为非必填
			oppmmbo.setFieldFlag(finish_attr_rq, 128L, false);
			// 设置异常字段为非必填
			oppmmbo.setFieldFlag(exception_attr_rq, 128L, false);
			// 设置延期字段为非只读
			oppmmbo.setFieldFlag(delay_attr_rq, 7L, false);

			// 设置非延期字段为只读
			oppmmbo.setFieldFlag(delay_attr_rd, 7L, true);
			// 设置延期字段为必填
			oppmmbo.setFieldFlag(delay_attr_rq, 128L, true);
			//修改延期申请人和延期时间必填
			oppmmbo.setValue("S_YQPERSON", login2, 11L);
			
		} else if ("异常".equals(cpmstatus)) {
//			//变更状态修改执行人和执行时间
//			oppmmbo.setValue("OPLOG_RXPERSON", login2, 11L);
//			oppmmbo.setValue("OPLOG_ZXDATE", newDate, 11L);
			// 设置取消字段为非必填
			oppmmbo.setFieldFlag(cancel_attr_rq, 128L, false);
			// 设置延期字段为非必填
			oppmmbo.setFieldFlag(delay_attr_rq, 128L, false);
			// 设置完成字段为非必填
			oppmmbo.setFieldFlag(finish_attr_rq, 128L, false);
			// 设置异常字段为非只读
			oppmmbo.setFieldFlag(exception_attr_rq, 7L, false);

			// 设置非异常字段为只读
			oppmmbo.setFieldFlag(exception_attr_rd, 7L, true);
			// 设置异常字段为必填
			oppmmbo.setFieldFlag(exception_attr_rq, 128L, true);
		}
	}
}
