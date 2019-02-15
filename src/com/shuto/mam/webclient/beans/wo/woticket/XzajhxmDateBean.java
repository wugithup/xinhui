package com.shuto.mam.webclient.beans.wo.woticket;

import java.rmi.RemoteException;
import java.util.ArrayList;

import psdi.mbo.MboData;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * com.shuto.mam.webclient.beans.wo.woticket.XzajhxmDateBean
 * 
 * @author shanbh 2016-5-27 工作票选择安健环技术交底卡项目
 */
public class XzajhxmDateBean extends DataBean {
	protected MboSetRemote getMboSetRemote() throws MXException,
			RemoteException {
		// 得到当前的对象的mbo
		MboRemote mainmbo = this.app.getDataBean().getMbo();
		MboSetRemote LICENCEMAIN5 = mainmbo.getMboSet("LICENCEMAIN5");
		MboRemote licenmbo = LICENCEMAIN5.getMbo(0);// 交底卡mbo
		String LICENCEMAINid = licenmbo.getString("LICENCEMAINid").replace(",",
				"");
		// System.out.println("交底卡id:  " + LICENCEMAINid);
		String siteid = mainmbo.getString("siteid");
		String wonum = mainmbo.getString("wonum");
		MboSetRemote alndomains = mainmbo
				.getMboSet(
						"$ajhjsjdk",
						"ajhjsjdk",
						" siteid = '"
								+ siteid
								+ "'  and   description not in  (select description from AQCSLINE  where parent ='"
								+ LICENCEMAINid + "')");

		// System.out
		// .println(" select * from  ajhjsjdk  where   siteid = '"
		// + siteid
		// +
		// "'  and   description not in  (select description from AQCSLINE  where parent ='"
		// + LICENCEMAINid + "')");
		return alndomains;
	}

	/**
	 * <p>
	 * 并将勾选工单带回，然后将数据保存。
	 * 
	 * @return int 返回int类型值
	 * @throws MXException
	 * @throws RemoteException
	 */

	public int execute() throws MXException, RemoteException {
		super.execute();

		// 当前mbo
		MboRemote mainmbo = this.app.getDataBean().getMbo();
		MboSetRemote LICENCEMAIN5 = mainmbo.getMboSet("LICENCEMAIN5");
		MboRemote licenmbo = LICENCEMAIN5.getMbo(0);// 交底卡mbo
		String LICENCEMAINid = licenmbo.getString("LICENCEMAINid").replace(",",
				"");
		// 获得当前dialog中的结果集
		MboSetRemote selectLines = getMboSet();
		// 获得dialog中选中的数据的结果集
		selectLines.resetWithSelection();

		// 得到选中集合
		MboRemote sqsonmbo = null;
		ArrayList<MboRemote> wonumlist = new ArrayList<MboRemote>();

		int selectnum = selectLines.count();
		for (int i = 0; i < selectnum; i++) {

			sqsonmbo = selectLines.getMbo(i);
			wonumlist.add(sqsonmbo);
		}
		MboRemote sqsonmbowo = null;
		if (wonumlist.size() > 0) {
			for (int j = 0; j < wonumlist.size(); j++) {
				sqsonmbowo = wonumlist.get(j);
				String sn = "";

				DataBean databean = this.app.getDataBean("1465872243635");
				// MboSetRemote projectplanset = licenmbo.getMboSet("WXYLINE");
//				if (databean.count() == 0) {
//					sn = j + 1 + "";
//					System.out.println(sn + "   初选安健环项目");
//
//				} else {
//					sn = databean.count() + 1 + "";
//					System.out.println(sn + "   选择安健环项目");
//
//				}
				CreatePlans(sqsonmbowo, sn);
			}
		}
		selectLines.save();
		// databean.reloadTable();
		// databean.refreshTable();
		app.getAppBean().save();
		return 1;
	}

	private void CreatePlans(MboRemote sqsonmbowo, String sn)
			throws RemoteException, MXException {

		MboRemote mainmbo = this.app.getDataBean().getMbo();
		MboSetRemote LICENCEMAIN5 = mainmbo.getMboSet("LICENCEMAIN5");
		MboRemote licenmbo = LICENCEMAIN5.getMbo(0);// 交底卡mbo
		String LICENCEMAINid = licenmbo.getString("LICENCEMAINid").replace(",",
				"");
		MboSetRemote projectplanset = licenmbo.getMboSet("WXYLINE");

		MboRemote projectplan = null;

		if (sqsonmbowo != null) {
			projectplan = projectplanset.add();
			// 插入描述
			projectplan.setValue("description",
					sqsonmbowo.getString("description"));
			projectplan.setValue("parent", LICENCEMAINid);
			projectplan.setValue("sn",  projectplanset.count() + "");
			projectplan.setValue("type", "危险源");

			projectplanset.save();

		}

	}

}
