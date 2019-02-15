/**  
 * <p> 运行定期是否完成定期工作（OPLOG_DELAY）字段的fld类
 * @author       Haox  haoxing@shuto.cn
 * @date         2012-11-12下午06:17:49
 * 
 * @Copyright:   2012 Shuto版权所有
 * @version      V1.0  
 */
package com.shuto.mam.app.operation.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * <p>
 * 运行定期是否完成定期工作（OPLOG_DELAY）字段的fld类，主要用于控制页面字段只读必填
 * 
 * @author Haox haoxing@shuto.cn
 * @date 2012-11-12下午06:17:49
 * 
 * @Copyright: 2012 Shuto版权所有
 * @version V1.0
 */
public class FldOplog_Delay extends MboValueAdapter {
	/**
	 * <p>
	 * 此构造方法必须添加，在此构造方法中实现页面中的放大镜选择某张表数据的功能
	 * 
	 * @param mbovalue
	 *            maximo封装好的获得属性值的类
	 */
	public FldOplog_Delay(MboValue mbv) {
		super(mbv);
	}

	/**
	 * <p>
	 * action方法当有数据更变时触发，控制页面字段的只读必填
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 */
	public void action() throws MXException, RemoteException {
		super.action();

		MboRemote oppmmbo = getMboValue().getMbo();
		MboRemote oplog = oppmmbo.getOwner();

		boolean delay = oppmmbo.getBoolean("OPLOG_DELAY");
		String[] delay_attr_rd = { "OPLOG_RXPERSON", "OPLOG_ZXDATE", "OPLOG_ZXSITUATION", "OPLOG_EXCEPCAUSE", "OPLOG_QXPERSON", "OPLOG_CANCELCAUSE" };

		String[] delay_attr_rq = { "OPLOG_DELAYCAUSE", "S_YQTODATE" };

		if (delay) {
			oppmmbo.setValue(("OPLOG_FINISH"), "0", 11l);
			oppmmbo.setValue(("OPLOG_EXCEPTION"), "0", 11l);
			oppmmbo.setValue(("OPLOG_CANCELYN"), "0", 11l);
			System.out.println("延期");
			oppmmbo.setFlag(7L, false);
			oppmmbo.setFieldFlag(delay_attr_rd, 7L, true);
			oppmmbo.setFieldFlag(delay_attr_rq, 128L, true);
			oppmmbo.setValue("OPLOG_STATUS", "延期", 11l);
			oppmmbo.setValue("CWOZB", oplog.getString("ZHIBIE"), 11l);
		}
	}
}
