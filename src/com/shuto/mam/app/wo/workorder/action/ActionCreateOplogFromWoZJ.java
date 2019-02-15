package com.shuto.mam.app.wo.workorder.action;

import java.rmi.RemoteException;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.wo.workorder.action.ActionCreateOplogFromWoZJ工作票终结写入运行日志
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年7月6日 下午10:34:29
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class ActionCreateOplogFromWoZJ implements ActionCustomClass {
	public void applyCustomAction(MboRemote paramMboRemote,
			Object[] paramArrayOfObject) throws MXException, RemoteException {
		String str1 = paramMboRemote.getString("CWOZX.displayname");// 工作负责人
		String str2 = paramMboRemote.getString("S_ORDERTYPE");// 工作票类型
		String str3 = paramMboRemote.getString("S_WOTKNUM");// 工作票编号
		String str4 = paramMboRemote.getString("DESCRIPTION");// 工作票内容
		String str5 = paramMboRemote.getString("S_ZJDATE");// 工作票终结时间
		String str6 = paramMboRemote.getString("OPLOGTYPE");// 写入运行日志类别
		String str7 = paramMboRemote.getString("S_ZJXKPERSON.DISPLAYNAME");// 终结许可人
		String str8 = paramMboRemote.getString("S_ZJXKQRDATE");// 终结许可时间
		if (!str6.isEmpty()) {
			MboSetRemote localMboSetRemote1 = paramMboRemote.getMboSet(
					"$oplog", "oplog",
					"siteid = :siteid and STATUS = 'ONDUTY' and oplogtype = '"
							+ str6 + "'");
			for (int i = 0; i < localMboSetRemote1.count(); i++) {
				MboRemote localMboRemote1 = localMboSetRemote1.getMbo(i);
				String str9 = localMboRemote1.getString("oplognum");
				MboSetRemote localMboSetRemote2 = localMboRemote1
						.getMboSet("OPLOGEVENTJS");
				MboRemote localMboRemote2 = localMboSetRemote2.add();
				localMboRemote2.setValue("oplognum", str9);
				localMboRemote2.setValue("HAPPENDATE", str5, 11L);
				localMboRemote2.setValue("TYPE", "工作票", 11L);
				localMboRemote2.setValue("CONTENT", "终结 " + str3 + " " + str2
						+ " " + str4 + " 工作负责人 : " + str1 + " 工作终结人：" + str7
						+ " 工作终结时间：" + str8, 11L);
				localMboSetRemote2.save();
			}
			localMboSetRemote1.close();
		}

	}
}