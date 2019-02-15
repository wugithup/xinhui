package com.shuto.mam.app.wo.hse;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.wo.hse.FldGls 华东大区 隔离锁 不可重复校验
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年8月27日 上午12:17:05
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldGls extends MboValueAdapter {

	public FldGls(MboValue mbv) {
		super(mbv);
	}

	public void validate() throws MXException, RemoteException {
		if (getMboValue().isNull())
			return;
		MboRemote mainmbo = getMboValue().getMbo();
		String attName = getMboValue().getName();// 获取当前字段名
		String tableName = getMboValue().getMbo().getName();// 获取当前表名
		MboValue siteid = getMboValue("siteid");// 获取当前字段值

		MboValue Value = getMboValue(attName);// 获取当前字段值
		if (!Value.isNull()) {
			MboSetRemote mrset = mainmbo.getMboSet("$" + tableName, tableName,
					" siteid='" + siteid + "' and " + attName + " ='" + Value
							+ "'");
			if (mrset.count() > 0) {
				String paramwo = Value.getString();
				throw new MXApplicationException("GLS", "CFSJ",
						new String[] { paramwo });

			}
		}
	}

}
