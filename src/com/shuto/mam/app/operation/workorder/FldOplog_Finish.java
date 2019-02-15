/**  
 * <p> 运行定期是否完成定期工作（OPLOG_FINISH）字段的fld类
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
 * 运行定期是否完成定期工作（OPLOG_FINISH）字段的fld类，主要用于控制页面字段只读必填
 * 
 * @author Haox haoxing@shuto.cn
 * @date 2012-11-12下午06:17:49
 * 
 * @Copyright: 2012 Shuto版权所有
 * @version V1.0
 */
public class FldOplog_Finish extends MboValueAdapter {
	/**
	 * <p>
	 * 此构造方法必须添加，在此构造方法中实现页面中的放大镜选择某张表数据的功能
	 * 
	 * @param mbovalue
	 *            maximo封装好的获得属性值的类
	 */
	public FldOplog_Finish(MboValue mbv) {
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

		boolean finish = oppmmbo.getBoolean("OPLOG_FINISH");

		String[] finish_attr_rd = { "OPLOG_DELAYCAUSE", "OPLOG_EXCEPCAUSE", "OPLOG_QXPERSON", "OPLOG_CANCELCAUSE", "S_YQTODATE" };

		String[] finish_attr_rq = { "OPLOG_RXPERSON", "OPLOG_ZXDATE", "OPLOG_ZXSITUATION" };

		if (finish) {
			oppmmbo.setValue(("OPLOG_DELAY"), "0", 11l);
			oppmmbo.setValue(("OPLOG_EXCEPTION"), "0", 11l);
			oppmmbo.setValue(("OPLOG_CANCELYN"), "0", 11l);
			System.out.println("完成");
			oppmmbo.setFieldFlag(finish_attr_rd, 7L, true);
			oppmmbo.setFieldFlag(finish_attr_rq, 128L, true);
			oppmmbo.setValue("OPLOG_STATUS", "完成", 11l);
		}
	}
}
