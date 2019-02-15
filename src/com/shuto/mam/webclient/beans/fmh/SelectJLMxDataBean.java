package com.shuto.mam.webclient.beans.fmh;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;

/**
 * com.shuto.mam.webclient.beans.fmh.SelectJLMxDataBean 华东大区 阜阳项目 副产品销售 选择计量明细 类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月17日 下午3:05:14
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SelectJLMxDataBean extends DataBean {
	public int MboSetRemote() throws MXException, RemoteException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String ch = mainMbo.getString("ch");

		String sql = "RLJLMXWHID not in (select RLJLMXWHID from FMHXSJLMX) and rlpz='粉煤灰' and isselect = '0' and jzjhk is not null and cnum='"
				+ ch + "'";
		DataBean mx = this.app.getDataBean("MX");
		MboSetRemote dialogSet = mx.getMboSet();
		dialogSet.setWhere(sql);
		return 1;
	}

	public int execute() throws MXException, RemoteException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String mainnum = mainMbo.getString("FMHXSNUM");
		String type = mainMbo.getString("TYPE");
		String company = mainMbo.getString("COMPANY");
		String status = mainMbo.getString("STATUS");
		MboSetRemote FMHXSJLMXset = mainMbo.getMboSet("FMHXSJLMX");
		String tableId = mainMbo.getName() + "id";
		long codeid = mainMbo.getLong(tableId);
		String personid = mainMbo.getUserInfo().getPersonId();
		String sql = "ownerid='" + codeid + "' and ownertable='"
				+ mainMbo.getName()
				+ "' and assignstatus='活动' and assigncode='" + personid + "'";
		MboSetRemote mbosetremote = mainMbo.getMboSet("assigncode",
				"WFASSIGNMENT", sql);
		try {
			if ((("计量重车重量".equals(status)) && (!mbosetremote.isEmpty()))
					|| ((personid.equalsIgnoreCase("maxadmin")) && ("计量重车重量"
							.equals(status)))) {
				DataBean mx = this.app.getDataBean("MX");
				MboSetRemote dialogSet1 = mx.getMboSet();
				dialogSet1.resetWithSelection();
				MboRemote dialogMbo1 = null;
				MboRemote lineMbo = null;

				String RLJLMXWHID = null;

				if (!dialogSet1.isEmpty())
					for (int a = 0; a < dialogSet1.count(); ++a) {
						dialogMbo1 = dialogSet1.getMbo(a);
						RLJLMXWHID = dialogMbo1.getString("RLJLMXWHID");
						lineMbo = FMHXSJLMXset.add();

						lineMbo.setValue("cnum", dialogMbo1.getString("cnum"),
								2L);
						lineMbo.setValue("JZJHK",
								dialogMbo1.getString("JZJHK"), 2L);
						lineMbo.setValue("pz", dialogMbo1.getString("pz"), 2L);
						lineMbo.setValue("MZ", dialogMbo1.getString("MZ"), 2L);
						lineMbo.setValue("piaoz",
								dialogMbo1.getString("piaoz"), 2L);
						lineMbo.setValue("KCCZSJ",
								dialogMbo1.getString("KCCZSJ"), 2L);
						lineMbo.setValue("ZCCZSJ",
								dialogMbo1.getString("ZCCZSJ"), 2L);
						lineMbo.setValue("TYPE", type, 2L);
						lineMbo.setValue("COMPANY", company, 2L);

						mainMbo.setValue("kczl", dialogMbo1.getString("pz"), 2L);
						mainMbo.setValue("ZCZL", dialogMbo1.getString("MZ"), 2L);
						mainMbo.setValue("JZ", dialogMbo1.getString("JZJHK"),
								2L);

						lineMbo.setValue("FMHXSNUM", mainnum, 2L);
						lineMbo.setValue("RLJLMXWHID", RLJLMXWHID, 2L);
					}
			} else {
				Utility.showMessageBox(this.clientSession.getCurrentEvent(),
						"提示", " 您没有选择权限，禁止操作！ ", 1);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.app.getAppBean().save();
		return 1;
	}
}