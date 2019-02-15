package com.shuto.mam.app.wo.workorder.action;

import java.rmi.RemoteException;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.wo.workorder.action.ActionCreateOplogFromWo 工作票许可写入运行日志
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年7月6日 下午10:56:23
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class ActionCreateOplogFromWo implements ActionCustomClass {
	public void applyCustomAction(MboRemote paramMboRemote,
			Object[] paramArrayOfObject) throws MXException, RemoteException {
		String str1 = paramMboRemote.getString("CWOZX.displayname");// 工作负责人
		String str2 = paramMboRemote.getString("S_ORDERTYPE");// 工作票类型
		String str3 = paramMboRemote.getString("S_WOTKNUM");// 工作票编号
		String str4 = paramMboRemote.getString("DESCRIPTION");// 工作票内容
		String str5 = paramMboRemote.getString("S_PZGZJSDATE");// 批准工作结束时间
		String str6 = paramMboRemote.getString("S_XKKSDATE");// 许可工作开始时间
		String str7 = paramMboRemote.getString("OPLOGTYPE");// 写入运行日志类别
		String str8 = paramMboRemote.getString("S_XKPERSON.displayname");// 工作许可人审批
		if (!str7.isEmpty()) {
			MboSetRemote localMboSetRemote1 = paramMboRemote.getMboSet(
					"$oplog", "oplog",
					"siteid = :siteid and STATUS = 'ONDUTY' and oplogtype = '"
							+ str7 + "'");// 对应的 当值 日志类型
			for (int i = 0; i < localMboSetRemote1.count(); i++) {
				MboRemote localMboRemote1 = localMboSetRemote1.getMbo(i);
				String str10 = localMboRemote1.getString("oplognum");
				MboSetRemote localMboSetRemote2 = localMboRemote1
						.getMboSet("OPLOGEVENTJS");
				MboRemote localMboRemote2 = localMboSetRemote2.add();
				localMboRemote2.setValue("oplognum", str10);// 日志编号
				localMboRemote2.setValue("HAPPENDATE", str6, 11L);// 许可工作开始时间 写入
																	// 发生时间
				localMboRemote2.setValue("TYPE", "工作票", 11L);
				localMboRemote2.setValue("CONTENT", "许可 " + str3 + " " + str2
						+ " " + str4 + " 工作负责人 : " + str1 + " 工作许可人：" + str8
						+ " 批准工作结束时间: " + str5, 11L);
				localMboSetRemote2.save();
			}
			localMboSetRemote1.close();
		}

	}
}