/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.webclient.beans.operation.oplog;

import com.shuto.mam.app.operation.oplog.OpLog;
import psdi.app.signature.HiddenValueSet;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * @author wuqi
 * @version V1.0
 * @Title: OpLogAppBean.java
 * @Package com.shuto.mam.webclient.beans.operation.oplog
 * @Description: TODO(运行交接班)
 * @date 2017-5-9 上午10:08:18
 */

public class OpLogAppBean extends AppBean {

	private MboRemote person = null;
	HiddenValueSet hiddenValues = null;
	private boolean icanCarryon = true;

	public OpLogAppBean() throws RemoteException, MXException {
		setOrderBy("oplogid desc");
	}

	/**
	 * 接班
     *
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	public int CHANGE() throws RemoteException, MXException {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		System.out.println("===============开始接班===============");
		MboRemote oplog = this.app.getAppBean().getMbo();
		// 当班人
		String loginid1 = oplog.getString("dbrperson");
		Date sysdate = new Date();

		String loginID = getMbo().getUserInfo().getLoginID();

		MboSetRemote maxuserSet = getMbo().getMboSet("$maxuser", "maxuser",
				"loginid='" + loginID + "'");
		this.person = maxuserSet.getMbo(0).getMboSet("person").getMbo(0);

		try {
			// 判断能否接班
			this.icanCarryon = ((OpLog) oplog).canCarryon(this.person);
			if (loginid1.equalsIgnoreCase(this.person.getString("personid"))) {
				// 不能自己接自己的班
				throw new MXApplicationException("系统提示", "接班人不能接自己的班!");
			}
			/*
			 * if (!oplogchack(loginid1)) { return 0; }
			 */

		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}

		WebClientEvent event = clientSession.getCurrentEvent();
		int msgRet = event.getMessageReturn();
		String message = " 确认进行接班操作？";

		if (msgRet < 0) {
			// 弹出提示窗口问是否继续
			throw new MXApplicationYesNoCancelException("BMXAA9896E", "system",
					"taskxf", new String[] { message });
		}
		if (msgRet == 8) {

			// 如果可以
			if (this.icanCarryon) {
				// HashMap<String, String> nextperiodMap = ((OpLog)
				// oplog).nextPeriodMap();
				// if (nextperiodMap.isEmpty()) {
				// // 运行日志配置有误，请联系管理员！（检查倒班表）
				// throw new MXApplicationException("oplog", "oplogcfgerror");
				// }
				// MboRemote nextshift = ((OpLog)
				// oplog).getShift(nextperiodMap);

				String JIEBPERSON = this.person.getString("personid");// 登陆人.接班人

				MboSetRemote oplogPersonMboSet = oplog.getMboSet(
						"$oplogperson", "oplogperson",
						"oplogtype='" + oplog.getString("oplogtype")
								+ "' and personid='" + JIEBPERSON + "'");
				MboRemote oplogPersonMbo = oplogPersonMboSet.getMbo(0);
                MboRemote newoplog = oplog.getThisMboSet().addAtIndex(0);

				newoplog.setValue("oplogtype", oplog.getString("oplogtype"));
				// DateFormat df = DateFormat.getInstance();
				// Date workdate = new Date();
				// try {
				// workdate = df.parse(nextperiodMap.get("workdate"));
				// //
				// System.out.println("-------workdate--------================="
				// // + new SimpleDateFormat("yyyy-mm-dd").format(workdate));
				// } catch (ParseException e1) {
				// e1.printStackTrace();
				// }

				// newoplog.setValue("zqdate", nextperiodMap.get("workdate"));
				newoplog.setValue("zqdate", sysdate);

				// newoplog.setValue("zbstartdate",
				// nextperiodMap.get("starttime"));
				// newoplog.setValue("zbenddate", nextperiodMap.get("endtime"));
				//
				newoplog.setValue("dbrperson",
						this.person.getString("personid"));
				//
				// newoplog.setValue("zhibie", nextperiodMap.get("shiftnum"));
				newoplog.setValue("zhibie", oplogPersonMbo.getString("zhibie"));
				System.out
						.println("----------------------------------------------当前值别-----------"
								+ oplogPersonMbo.getString("zhibie"));
				//
				newoplog.setValue("status", "ONDUTY");

				newoplog.setValue("CURRENTUSERID", loginid1, 11L);

				// 接班时间赋值
				newoplog.setValue("JIEBDATE", sysdate, 11L);

				//
				if ("早班".equals(oplog.getString("banci"))) {
					newoplog.setValue("banci", "晚班", 11L);
					// } else if ("中班".equals(oplog.getString("banci"))) {
					// newoplog.setValue("banci", "晚班", 11L);
				} else if ("晚班".equals(oplog.getString("banci"))) {
					newoplog.setValue("banci", "早班", 11L);
				} else {
					newoplog.setValue("banci", "早班", 11L);
				}
				try {
                    // ((OpLog) oplog).assetAndPoint(oplog, newoplog);
					// //设备工况，重要参数
					// System.out.println("===运行日志接班+开始写入设备工况、重要参数、运行记事");
					((OpLog) oplog)
                            .copyAssetAndPointToNewOplog(oplog, newoplog); // 设备工况，重要参数、交待事项带入下一班
					// System.out.println("===运行日志接班+设备工况、重要参数、运行记事写入完毕，开始写入出勤人员");
					// ((OpLog) oplog).getNewPersonGroup(newoplog);// 出勤人员带入下一班
					// System.out.println("===运行日志接班+出勤人员写入完毕===" +
					// oplog.getDate("JBDATE"));
					oplog.getThisMboSet().save();

					oplog = this.app.getAppBean().getMbo();

					oplog.setFieldFlag("JIEBPERSON", 7L, false);
					oplog.setValue("JIEBPERSON", JIEBPERSON);// 接班人赋值

					System.out
							.println("===========设置原日志为关闭===================");
					oplog.setValue("STATUS", "CLOSE", 11L);
					oplog.getThisMboSet().save();
					this.app.getAppBean().save();
					DataBean appBean = this.app.getAppBean();
					Utility.showMessageBox(
							this.clientSession.getCurrentEvent(), "",
							"     接班完成!。", 0);
					this.sessionContext.queueRefreshEvent();
					((AppBean) appBean).moveToUniqueId(newoplog
							.getLong("oplogid"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			maxuserSet.clear();
			maxuserSet.close();
			this.app.getAppBean().save();
			this.app.getAppBean().refreshTable();

			long endTime = System.currentTimeMillis(); // 获取结束时间
			System.out.println("接班程序运行时间： " + (endTime - startTime) + "ms");
			System.out.println("===============接班结束===============");
			return 1;
		}
		return 0;
	}

	/**
	 * 接班提示
     *
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	private boolean oplogchack(String loginid1) throws RemoteException,
			MXException {
		String message = " 接班人和当班人相同，是否替班或代班？ ";
		WebClientEvent event = clientSession.getCurrentEvent();
		int msgRet = event.getMessageReturn();
		// 如果接班人和当班人相同，是否为代班？如果是，继续，并且标记；如果否，则撤销。
		if (loginid1.equalsIgnoreCase(this.person.getString("personid"))) {
			if (msgRet < 0) {
				// 弹出提示窗口问是否继续
				throw new MXApplicationYesNoCancelException("BMXAA9896E",
						"system", "taskxf", new String[] { message });
			}
            return msgRet == 2;
        }
		return true;
	}

	/**
	 * 交班
     *
	 * @return
	 * @throws
	 */
	public int HANDOVER() throws Exception {
		MboRemote oplog = this.app.getAppBean().getMbo();
		String status = oplog.getString("STATUS");
		String oplogtype = getString("oplogtype");
		String siteid = getString("siteid");
		String zqdate = getString("ZQDATE");
		String banci = getString("BANCI");

		// 判断交班人是否为日志值班人
		String loginID = getMbo().getUserInfo().getLoginID();
		MboSetRemote maxuserSet = getMbo().getMboSet("$MAXUSER", "MAXUSER",
				"LOGINID = '" + loginID + "'");
		String jiebanPerson = maxuserSet.getMbo(0).getString("personid");
		maxuserSet.close();
		if (!jiebanPerson.equalsIgnoreCase(oplog.getString("dbrperson"))) {
			// 交班人必须为当班人,请重新输入
			throw new MXApplicationException("oplog", "HIsNotC");
		}

		// 判断当前日志是否当值
		if (!"ONDUTY".equalsIgnoreCase(status)) {
			// 操作失败！只有当班状态下，才能进行交班操作。
			throw new MXApplicationException("oplog", "oplogdbstatuscnjiaob");
		}

		MboSetRemote wos = getMbo().getMboSet(
				"$OPLOGWORK",
				"OPLOGWORK",
				"OPLOGTYPE ='" + oplogtype + "' AND OPLOG_BANCI ='" + banci
						+ "' AND OPLOG_STATUS in('生成') AND SITEID ='" + siteid
						+ "'"
						+ " AND TO_CHAR(OPLOG_CREATEDATE,'yyyy-MM-dd')= '"
						+ zqdate + "'");
		String message;
		if (wos.count() != 0) {
			throw new MXApplicationException("系统提示", " 当前存在未处理(生成)的定期工作，不予交班！");
		} else {
			message = "是否确定交班？";
		}

		// 是否确认交班
		if (!oplogCheck(message)) {
			return 0;
		}

		// 设置日志状态为已交班
		oplog.setValue("STATUS", "HASSHIFT", 11L);
		// 交班时间
		oplog.setValue("JBDATE", MXServer.getMXServer().getDate());
		// 交班人
		oplog.setValue("JBPERSON", oplog.getString("dbrperson"));
		Utility.showMessageBox(this.clientSession.getCurrentEvent(), "",
				"      交班完成!。", 0);
		this.app.getAppBean().save();
		return 1;

	}

	// 点击交班时，自动将本班次开具的工作票信息带入运行日志中
	private void insertToOplognote() throws RemoteException, MXException {
		MboRemote oplog = this.app.getAppBean().getMbo();
		String oplogtype = oplog.getString("oplogtype");// 日志类型
		String siteid = oplog.getString("siteid");// 地点
		String jbdate = oplog.getString("jbdate");// 交班时间
		String jiebdate = oplog.getString("jiebdate");// 接班时间
		MboSetRemote msr = oplog.getMboSet("$WORKORDER", "WORKORDER",
				"worktype='工作票' and to_char(S_ZZPZDATE)>='" + jiebdate
						+ "' and to_char(S_ZZPZDATE) <='" + jbdate + "'");
	}

	/**
	 * 确认交班提示
     *
	 * @return
	 * @throws MXException
	 */
	private boolean oplogCheck(String msg) throws MXException {
		WebClientEvent event = this.clientSession.getCurrentEvent();
		int msgRet = event.getMessageReturn();
		if (msgRet < 0) {
			throw new MXApplicationYesNoCancelException("savecontinueid",
					"system", "taskxf", new String[] { msg });
		}
		// 是
        return msgRet == 8;
    }

	/**
	 * 打开润乾报表
     *
	 * @return
	 */
	public int OPENREPORT() {
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			MboSetRemote reportSet = null;
			if (mbo == null) {
				String mboname = this.app.getAppBean().getMboName();
				MboSetRemote mboSet = MXServer.getMXServer().getMboSet(mboname,
						MXServer.getMXServer().getSystemUserInfo());
				mbo = mboSet.getMbo(0);
			}
			reportSet = mbo.getMboSet("RQREPORT");
			if (reportSet.getMbo(0) != null && reportSet.getMbo(1) == null) {
				MboRemote reportMbo = reportSet.getMbo(0);
				String dialogID = reportMbo.getString("DIALOGID");
				String rqreportname = reportMbo.getString("RQREPORTNAME");// 报表名
				String rqreportnum = reportMbo.getString("rqreportnum");// 报表编号
				if (!"".equals(dialogID)) {
					this.clientSession.setAttribute("rqreportname",
							rqreportname);
					this.clientSession.setAttribute("rqreportnum", rqreportnum);
					this.clientSession.loadDialog(dialogID);
					return 0;
				}
				StringBuffer url = new StringBuffer(MXServer.getMXServer()
						.getProperty("mxe.runqian.url"));
				url.append("/" + rqreportname.toLowerCase() + ".rpx");
				MboSetRemote rqreportset = mbo.getMboSet("$RQREPORTPARAM",
						"RQREPORTPARAM", "rqreportnum = '" + rqreportnum + "'");
				if (!rqreportset.isEmpty()) {
					for (int i = 0; i < rqreportset.count(); i++) {
						url.append("&");
						url.append(rqreportset.getMbo(i).getString("PARAMNAME")
								.toLowerCase());
						url.append("=");
						url.append(mbo.getString(rqreportset.getMbo(i)
								.getString("PARAMVALUE")));
					}

				}
				rqreportset.close();
				this.app.openURL(url.toString(), true);
			} else if (reportSet.count() > 1) {
				this.clientSession.loadDialog("RQREPORT1");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return 1;
	}

	// @Override
	// protected void setCurrentRecordData(MboRemote mbo) throws MXException,
	// RemoteException {
	// MboSetRemote notemsr = mbo.getMboSet("OPLOGNUM_OPLOGNOTE");
	// for (int i = 0; i < notemsr.count(); i++) {
	// MboRemote notembo = notemsr.getMbo(i);
	// String style = notembo.getString("STYLE");
	// if ("运行记事".equalsIgnoreCase(style)) {
	// //带入下一班清空
	// notembo.setValue("COMMITNEXT", false, 11L);
	// //专业清空
	// notembo.setValue("PROFESSION", "", 11L);
	//
	// //记事类型不知读
	// notembo.setFieldFlag("TYPE", 7L, false);
	// //记事类型必填
	// notembo.setFieldFlag("TYPE", 128L, true);
	// //专业和带入下一个班次只读
	// notembo.setFieldFlag("PROFESSION", 7L, true);
	// notembo.setFieldFlag("COMMITNEXT", 7L, true);
	// //设置记事类型默认值
	// notembo.setValue("TYPE", "运行记事", 11L);
	// } else if ("专业提示".equalsIgnoreCase(style)) {
	// notembo.setFieldFlag("PROFESSION", 7L, false);
	// //记事类型设为不必填
	// notembo.setFieldFlag("TYPE", 128L, false);
	// //记事类型清空
	// notembo.setValue("TYPE", "", 11L);
	// //带入下一班清空
	// notembo.setValue("COMMITNEXT", false, 11L);
	// //记事类型和带入下一个班次只读
	// notembo.setFieldFlag("TYPE", 7L, true);
	// notembo.setFieldFlag("COMMITNEXT", 7L, true);
	// } else if ("交代事项".equalsIgnoreCase(style)) {
	// notembo.setFieldFlag("COMMITNEXT", 7L, false);
	// //记事类型设为不必填
	// notembo.setFieldFlag("TYPE", 128L, false);
	// //记事类型清空
	// notembo.setValue("TYPE", "", 11L);
	// //专业清空
	// notembo.setValue("PROFESSION", "", 11L);
	// //记事类型和专业只读
	// notembo.setFieldFlag("PROFESSION", 7L, true);
	// notembo.setFieldFlag("TYPE", 7L, true);
	// }
	// }
	// super.setCurrentRecordData(mbo);
	// }

}