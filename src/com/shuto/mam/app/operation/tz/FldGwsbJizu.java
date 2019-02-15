package com.shuto.mam.app.operation.tz;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 高温设备跟踪记录            TZ_GWSBGZ
 com.shuto.mam.app.operation.tz.FldGwsbJizu 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:51:28
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldGwsbJizu extends MboValueAdapter {
	public FldGwsbJizu(MboValue mbv) {
		super(mbv);
	}

	public void action() throws MXException, RemoteException {
		super.action();

		MboRemote mbo = getMboValue().getMbo();
		String gwsbtype = getMboValue().getMbo().getString("type");// 类型
		String siteid = getMboValue().getMbo().getString("siteid");// 站点
		String gwsbjizu = getMboValue().getMbo().getString("jizu");// 机组
		String num = getMboValue().getMbo().getString("GWSBNUM");
		MboSetRemote gwsbtempSet = mbo.getMboSet("#TZ_GWSBGZ ", "TZ_GWSBGZCB",
				"siteid='" + siteid + "' and type='" + gwsbtype
						+ "' and jizu = '" + gwsbjizu + "' and istemplate=1");// 得到模版数据
		int count = gwsbtempSet.count();
		MboSetRemote gwsbgz_bynum = mbo.getMboSet("GWSBGZ_BYNUM");// 得到子表结果集

		if (!gwsbtempSet.isEmpty()) {// 如果模版数据不为空
			gwsbgz_bynum.deleteAll();

			MboRemote gykgdz_detail;
			for (int i = 0; i < count; i++) {

				gykgdz_detail = gwsbgz_bynum.add();
				gykgdz_detail.setValue("GWSBNUM", num, 2L);// 主键
				gykgdz_detail.setValue("sbname", gwsbtempSet.getMbo(i)
						.getString("sbname"), 2L);
				gykgdz_detail.setValue("MAXVALUE", gwsbtempSet.getMbo(i)
						.getString("MAXVALUE"), 2L);
				gykgdz_detail.setValue("sjwd",
						gwsbtempSet.getMbo(i).getString("sjwd"), 2L);
				gykgdz_detail.setValue("jcr",
						gwsbtempSet.getMbo(i).getString("jcr"), 2L);
				gykgdz_detail.setValue("IS_QLFS", gwsbtempSet.getMbo(i)
						.getString("IS_QLFS"), 2L);
				gykgdz_detail.setValue("description", gwsbtempSet.getMbo(i)
						.getString("description"), 2L);
				gykgdz_detail.setValue("type",
						gwsbtempSet.getMbo(i).getString("type"), 2L);
				gykgdz_detail.setValue("jizu",
						gwsbtempSet.getMbo(i).getString("jizu"), 2L);

			}
		}
		gwsbtempSet.close();
		gwsbgz_bynum.close();
	}

}
