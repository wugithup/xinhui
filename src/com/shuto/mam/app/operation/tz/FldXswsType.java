package com.shuto.mam.app.operation.tz;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 小室测温、测湿记录       TZ_XSWS
 com.shuto.mam.app.operation.tz.FldXswsType 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:54:10
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldXswsType extends MboValueAdapter {

	public FldXswsType(MboValue mbv) {
		super(mbv);
	}

	public void action() throws MXException, RemoteException {
		super.action();

		MboRemote mbo = getMboValue().getMbo();
		String xswstype = getMboValue().getMbo().getString("type");// 类型
		String siteid = getMboValue().getMbo().getString("siteid");// 站点
		String num = getMboValue().getMbo().getString("XSWSNUM");
		MboSetRemote xswstempSet = mbo.getMboSet("#TZ_XSWS ", "TZ_XSWSCB",
				"siteid='" + siteid + "' and type='" + xswstype + "' and istemplate=1");// 得到模版数据

		int count = xswstempSet.count();
		MboSetRemote xsws_bynum = mbo.getMboSet("XSWS_BYNUM");// 得到子表结果集

		if (!xswstempSet.isEmpty()) {// 如果模版数据不为空
			xsws_bynum.deleteAll();

			MboRemote gykgdz_detail;
			for (int i = 0; i < count; i++) {

				gykgdz_detail = xsws_bynum.add();
				gykgdz_detail.setValue("XSWSNUM", num, 2L);// 主键
				gykgdz_detail.setValue("xsname",xswstempSet.getMbo(i).getString("xsname"), 2L);
				gykgdz_detail.setValue("bzws",xswstempSet.getMbo(i).getString("bzws"), 2L);
				gykgdz_detail.setValue("clwd",xswstempSet.getMbo(i).getString("clwd"), 2L);
				gykgdz_detail.setValue("clsd",xswstempSet.getMbo(i).getString("clsd"), 2L);
				gykgdz_detail.setValue("jlr",xswstempSet.getMbo(i).getString("jlr"), 2L);
				gykgdz_detail.setValue("jldate", xswstempSet.getMbo(i).getString("jldate"), 2L);
				gykgdz_detail.setValue("description", xswstempSet.getMbo(i).getString("description"), 2L);
				gykgdz_detail.setValue("type", xswstempSet.getMbo(i).getString("type"), 2L);

			}
		}
		xswstempSet.close();
		xsws_bynum.close();
	}

}
