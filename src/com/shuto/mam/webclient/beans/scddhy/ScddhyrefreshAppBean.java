package com.shuto.mam.webclient.beans.scddhy;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import com.shuto.mam.webclient.beans.base.BaseReportAppBean;

 
/**  
com.shuto.mam.webclient.beans.scddhy.ScddhyrefreshAppBean 江苏大区 生产调度会议纪要 appname： Scddhyrefresh
* @author       shanbh  shanbh@shuoto.cn
* @date         2017年11月23日 上午11:05:52
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class ScddhyrefreshAppBean extends BaseReportAppBean {
	public int ALTESTATUS() throws RemoteException, MXException, ParseException {
		MboRemote localMboRemote1 = this.app.getAppBean().getMbo();
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date localDate1 = localSimpleDateFormat.parse(localSimpleDateFormat
				.format(new Date()));
		String str1 = null;
		String str2 = null;
		String str3 = null;
		String str4 = null;
		String str5 = null;
		Date localDate2 = null;
		MboRemote localMboRemote2 = null;
		if ((localMboRemote1.getString("siteid").equals("RDBQ000"))
				|| (localMboRemote1.getString("siteid").equals("NJHX000"))) {
			DataBean localDataBean = this.app.getDataBean("results_showlist");
			MboRemote localMboRemote3 = null;
			MboSetRemote localMboSetRemote = null;
			String str6 = null;
			for (int i = 0; i < localDataBean.count(); ++i) {
				localMboRemote3 = localDataBean.getMbo(i);
				localMboSetRemote = localMboRemote3.getMboSet("zrbm");
				for (int j = 0; j < localMboSetRemote.count(); ++j) {
					localMboRemote2 = localMboSetRemote.getMbo(j);
					str6 = localMboRemote2.getString("ZZSTATUS");
					str1 = localMboRemote2.getString("STATUS");
					str2 = localMboRemote2.getString("RWZB.PJDEPARTMENT");
					str3 = localMboRemote2.getString("HFZB2.PJTYPE");
					str4 = localMboRemote2.getString("HFZB1.HFTYPE");
					str5 = localMboRemote2.getString("RWZB.JHDATETIME");

					int k = 0;
					if (!str5.equals("")) {
						localDate2 = localSimpleDateFormat.parse(str5);
						if (localDate2.compareTo(localDate1) >= 0)
							k = 0;
						else {
							k = 1;
						}
					}
					if ((!"已完成".equals(str6))
							&& (((!"已完成".equals(str1)) || (!"已完成评价"
									.equals(str1))))) {
						if ((!str3.equals("已完成")) && (k != 0)) {
							str6 = "已超期";
						} else if ((str1.equals("待评价部门指定评价人"))
								|| (str1.equals("请指定完成时间"))
								|| (str1.equals("待评价部门审核"))) {
							str6 = "评价部门未流转";
						} else if ((((str1.equals("待责任部门指定责任人"))
								|| (str1.equals("待责任人回复")) || (str1
									.equals("待责任部门回复"))))
								&& (!str4.equals("进行中"))) {
							str6 = "责任部门未流转";
						} else if ((((str1.equals("责任人已回复")) || (str1
								.equals("责任部门已回复")))) && (str4.equals("进行中"))) {
							str6 = "进行中";
						} else if ((!str2.equals("")) && (!str4.equals("进行中"))) {
							str6 = str3;
						} else if (str2.equals("")) {
							str6 = str4;
						}
					}
					localMboRemote2.setValue("ZZSTATUS", str6);
				}
				localMboSetRemote.save();
			}
		}
		return 1;
	}
}