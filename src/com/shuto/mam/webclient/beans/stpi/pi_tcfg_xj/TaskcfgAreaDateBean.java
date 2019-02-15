package com.shuto.mam.webclient.beans.stpi.pi_tcfg_xj;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 任务配置中删除区域时删除项目
 * @author mabin
 *
 */
public class TaskcfgAreaDateBean extends DataBean{
	
	public TaskcfgAreaDateBean() {
	}
	public int toggledeleterow() throws MXException {
		try {
			MboRemote mbo = app.getAppBean().getMbo();
			MboSetRemote taskcfgareaSet = mbo.getMboSet("ST_PI_TASKCFG_AREA");
			int row = this.clientSession.getCurrentEvent().getRow();
			MboRemote taskcfgareaMbo = taskcfgareaSet.getMbo(row);
			MboSetRemote taskcfgitemSet = taskcfgareaMbo.getMboSet("ST_PI_TASKCFG_ITEM");
			taskcfgitemSet.deleteAll(11L);
			super.toggledeleterow();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 1;
	}
	


}