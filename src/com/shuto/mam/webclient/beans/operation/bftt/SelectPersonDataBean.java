package com.shuto.mam.webclient.beans.operation.bftt;

/**  
 com.shuto.mam.webclient.beans.operation.bftt.SelectPersonDataBean 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月31日 上午10:56:21
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class SelectPersonDataBean extends DataBean {
	MboRemote currMbo = null;
	MboSetRemote lines = null;
	MboRemote currLine = null;

	public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
		MboSetRemote localMboSetRemote = super.getMboSetRemote();
		String siteid = this.app.getAppBean().getMbo().getString("siteid");
		String profession = this.app.getAppBean().getMbo()
				.getString("PROFESSION");
		MboRemote pro = this.app
				.getAppBean()
				.getMbo()
				.getMboSet(
						"$profession",
						"profession",
						"siteid='" + siteid + "' and professionnum='"
								+ profession + "'").getMbo(0);
		String zy=pro.getString("PARENTNUM");
		localMboSetRemote.setWhere(" where status='活动' and locationsite='"
				+ siteid + "' and department IN ('DEPT02','DEPT03') AND profession='"+zy+"' ");

		localMboSetRemote.reset();

		return localMboSetRemote;
	}

	public int execute() throws MXException, RemoteException {
		super.execute();

		MboSetRemote localMboSetRemote1 = getMboSet();

		localMboSetRemote1.resetWithSelection();

		String str = this.app.getAppBean().getMbo()
				.getString("OPMA_BHTTJDZXGDJNUM");

		for (int i = 0; i < localMboSetRemote1.count(); i++) {
			MboRemote localMboRemote1 = localMboSetRemote1.getMbo(i);

			MboSetRemote localMboSetRemote2 = this.app.getAppBean().getMbo()
					.getMboSet("ASSET_YDLINE");

			MboRemote localMboRemote2 = localMboSetRemote2.addAtEnd();

			localMboRemote2.setValue("personid",
					localMboRemote1.getString("personid"), 11L);

			localMboRemote2.setValue("departmentnum",
					localMboRemote1.getString("department"), 11L);

			localMboRemote2.setValue("OPMA_BHTTJDZXGDJNUM", str, 11L);
		}

		this.app.getAppBean().reloadTable();

		this.app.getAppBean().refreshTable();

		return 1;
	}
}