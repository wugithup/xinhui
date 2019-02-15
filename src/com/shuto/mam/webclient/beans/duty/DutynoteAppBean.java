package com.shuto.mam.webclient.beans.duty;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 *
com.shuto.mam.webclient.beans.duty.DutynoteAppBean 江苏
DUTYNOTE 公司值班记录
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月17日 下午5:00:19
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */

public class DutynoteAppBean extends AppBean {

	public int INSERT() throws MXException, RemoteException {
		super.INSERT();

		MboRemote mbo = app.getAppBean().getMbo();
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		String time = dateformat.format(date);
		mbo.setValue("glzd", time, 11l);
		String siteid = mbo.getString("siteid");

		String COALTJZBNUM = mbo.getString("GLZD");
		MboSetRemote gykgdztempSet = mbo.getMboSet("#DUTYNOTE", "DUTYNOTE",
				"siteid='" + siteid + "' and istemplate=1");// 得到模版数据
		// istemplate是否模板

		gykgdztempSet.setOrderBy("SORTNUM desc");// 子表的 排序字段

		int count = gykgdztempSet.count();
		MboSetRemote gykgdz_detail_bynumset_1 = mbo.getMboSet("DUTYNOTE");// 得到子表结果集

		if (!gykgdztempSet.isEmpty()) {// 如果模版数据不为空

			MboRemote gykgdz_detail;
			for (int i = 0; i < count; i++) {

				gykgdz_detail = gykgdz_detail_bynumset_1.add();

				gykgdz_detail.setValue("GLZD", COALTJZBNUM, 2L);// 主键
				gykgdz_detail.setValue("BM",
						gykgdztempSet.getMbo(i).getString("BM"),// 参数名称
						2L);
				gykgdz_detail.setValue("ZY",
						gykgdztempSet.getMbo(i).getString("ZY"),// 参数名称
						2L);
				gykgdz_detail.setValue("TEL",
						gykgdztempSet.getMbo(i).getString("TEL"),// 参数名称
						2L);
				gykgdz_detail.setValue("sortnum", gykgdztempSet.getMbo(i)// 排序字段
						.getString("sortnum"), 2L);

			}}


		gykgdztempSet.close();
		gykgdz_detail_bynumset_1.close();

		return 1;


	}


	public int SAVE() throws MXException, RemoteException {
		String siteid = this.getMbo().getString("siteid");
		//值班记录创建1天后不允许再修改
		if ("RDBQ000".equals(siteid)) {
			Date sysdate = new Date();
			Date createdate = this.getMbo().getDate("createdate");
			if(daysBetween(createdate,sysdate)>1) {
				throw new MXApplicationException("", "您没有权限修改一天之前的记录");
			}
		}
		return super.SAVE();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate
	 * @param edate
	 * @return
	 */
	 public static int daysBetween(Date smdate,Date edate) {
	       Calendar cal = Calendar.getInstance();
	       cal.setTime(smdate);
	       long time1 = cal.getTimeInMillis();
	       cal.setTime(edate);
	       long time2 = cal.getTimeInMillis();
	       long between_days=(time2-time1)/(1000*3600*24);
	       return Integer.parseInt(String.valueOf(between_days));
	 }


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
					String remark = reportMbo.getString("REMARK");			//备注
					System.out.println(remark);
					if (!"".equals(dialogID)) {
						this.clientSession.setAttribute("rqreportname", rqreportname);
						this.clientSession.setAttribute("rqreportnum", rqreportnum);
						this.clientSession.setAttribute("remark", remark);
						this.clientSession.loadDialog(dialogID);
						return 0;
					}
					StringBuffer url = new StringBuffer(MXServer.getMXServer().getProperty("mxe.runqian.url"));
					url.append("/" + rqreportname.toLowerCase() + ".rpx");
					MboSetRemote rqreportset = mbo.getMboSet("$RQREPORTPARAM", "RQREPORTPARAM",
							"rqreportnum = '" + rqreportnum + "'");
					if (!rqreportset.isEmpty()) {
						for (int i = 0; i < rqreportset.count(); i++) {
							url.append("&");
							url.append(rqreportset.getMbo(i).getString("PARAMNAME").toLowerCase());
							url.append("=");
							url.append(mbo.getString(rqreportset.getMbo(i).getString("PARAMVALUE")));
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
}