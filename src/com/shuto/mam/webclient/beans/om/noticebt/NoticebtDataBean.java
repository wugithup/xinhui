package com.shuto.mam.webclient.beans.om.noticebt;

/**  
com.shuto.mam.webclient.beans.om.noticebt.NoticebtDataBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月15日 上午10:37:46
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class NoticebtDataBean extends DataBean {
	public int addrow() throws MXException {
		try {
			MboRemote Mbo = this.app.getAppBean().getMbo();
			String status = Mbo.getString("STATUS");
			if ("新建".equals(status)) {
				super.addrow();
				String noticebillnum = Mbo.getString("NOTICEBILLNUM");
				getMbo().setValue("NOTICEBILLNUM", noticebillnum, 11L);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int toggledeleterow() throws MXException {
		try {
			MboRemote Mbo = this.app.getAppBean().getMbo();
			String status = Mbo.getString("STATUS");
			if ("新建".equals(status))
				super.toggledeleterow();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public void all() throws RemoteException, MXException {
		MboRemote Owner = this.app.getAppBean().getMbo();
		String status = Owner.getString("STATUS");
		String siteid = Owner.getString("siteid");
		if ("新建".equals(status)) {
			String noticebillnum = Owner.getString("NOTICEBILLNUM");
			MboSetRemote Set = Owner.getMboSet("NOTICEBILLPERSON");
			MboRemote Mbo = null;
			if (Set.isEmpty()) {
				MboSetRemote PersonSet = Owner.getMboSet("$person", "PERSON",
						"status = '活动' and locationsite='" + siteid + "' and department is not null");
				MboRemote PersonMbo = null;
				String personid = "";
				if (!PersonSet.isEmpty()) {
					for (int i = 0; i < PersonSet.count(); i++) {
						PersonMbo = PersonSet.getMbo(i);
						personid = PersonMbo.getString("PERSONID");
						Mbo = Set.add();
						Mbo.setValue("PERSONID", personid, 11L);
						Mbo.setValue("NOTICEBILLNUM", noticebillnum, 11L);
					}
				}
				PersonSet.close();
				this.app.getAppBean().reloadTable();
			}
		}
	}
}
