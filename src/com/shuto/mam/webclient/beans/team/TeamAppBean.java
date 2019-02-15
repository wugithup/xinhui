package com.shuto.mam.webclient.beans.team;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 
 * @ClassName: TeamAppBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年6月19日 下午11:15:00
 *
 */

public class TeamAppBean extends AppBean {
	public int DELETE() throws MXException, RemoteException {
		MboRemote thismbo = this.app.getAppBean().getMbo();

		MboSetRemote person = thismbo.getMboSet("teamnum_person");

		MboRemote personmbo = null;

		for (int i = 0; i < person.count(); i++) {
			personmbo = person.getMbo(i);

			personmbo.setValue("s_teamnum", "");
		}
		this.app.getAppBean().reloadTable();
		this.app.getAppBean().refreshTable();
		return super.DELETE();
	}
}