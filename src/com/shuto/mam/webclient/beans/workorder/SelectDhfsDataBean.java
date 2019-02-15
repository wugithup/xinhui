package com.shuto.mam.webclient.beans.workorder;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;
import java.util.Vector;

/**
 * com.shuto.mam.webclient.beans.workorder.SelectDhfsDataBean 工作票动火票动火方式选择
 *
 * @author shanbh shanbh@shuoto.cn
 * @version V1.0
 * @date 2017年8月28日 下午11:02:17
 * @Copyright: 2017 Shuto版权所有
 */
public class SelectDhfsDataBean extends DataBean {

	@Override
	public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
		super.getMboSetRemote();
		MboRemote Mbo = this.app.getAppBean().getMbo();
		String WONUM = this.app.getAppBean().getMbo().getString("WONUM");
		String S_WOTKNUM = Mbo.getString("S_WOTKNUM");
		MboSetRemote personset = Mbo.getMboSet("$alndomain", "alndomain", " domainid='DHFS'");
		return personset;
	}

	@Override
	public int execute() throws MXException, RemoteException {
//		super.execute();
		String dhfs = "";
		MboRemote mbo = app.getAppBean().getMbo();
		String yz = mbo.getString("dhfs");
		Vector selecteLines = getMboSet().getSelection();
		for (int i = 0; i < selecteLines.size(); i++) {
			MboRemote persongroupteam = (MboRemote) selecteLines.elementAt(i);
			dhfs = dhfs.concat(persongroupteam.getString("value") + ",");
		}
		mbo.setValue("dhfs", dhfs.substring(0, dhfs.length() - 1), 11L);
		app.getAppBean().reloadTable();
		app.getAppBean().refreshTable();
		return 1;
	}
}