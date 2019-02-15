package com.shuto.mam.webclient.beans.workorder;

import java.rmi.RemoteException;

import psdi.app.workorder.WORemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class DelectWoDataBean extends DataBean {
	public int toggledeleterow() throws MXException {
		DataBean appBean = this.app.getAppBean();
		try {
			WORemote woremote = (WORemote) appBean.getMbo();
			MboRemote mbo = getMbo();
			String wonum = woremote.getString("WONUM");
			String glWonum = mbo.getString("RELATEDRECWONUM");

			MboSetRemote mbosetremote1 = woremote.getMboSet("$RELATEDRECORD1", "RELATEDRECORD", "(recordkey = '" + wonum
					+ "' and class ='工单' and relatedreckey='" + glWonum + "' and relatedrecclass='工单')");
			MboSetRemote mbosetremote2 = woremote.getMboSet("$RELATEDRECORD2", "RELATEDRECORD", "(recordkey = '"
					+ glWonum + "' and class ='工单' and relatedreckey='" + wonum + "' and relatedrecclass='工单')");
			if ((mbosetremote1.count() > 0) && (mbosetremote1.count() > 0)) {
				mbosetremote2.getMbo(0).setFieldFlag("relatetype", 7L, false);
				mbosetremote2.getMbo(0).setValue("relatetype", "相关");
				mbosetremote2.getMbo(0).setFieldFlag("relatetype", 7L, true);

				mbosetremote1.getMbo(0).setFieldFlag("relatetype", 7L, false);
				mbosetremote1.getMbo(0).setValue("relatetype", "相关");
				mbosetremote1.getMbo(0).setFieldFlag("relatetype", 7L, true);
				appBean.save();
				fireStructureChangedEvent();
				this.sessionContext.queueRefreshEvent();
				fireChildChangedEvent();
				this.sessionContext.queueRefreshEvent();
				fireDataChangedEvent();
				this.sessionContext.queueRefreshEvent();
				MboSetRemote woSetMbo = woremote.getMboSet("$TMP_WORKORDER", "WORKORDER",
						"(wonum = '" + glWonum + "')");
				if (woSetMbo.count() > 0) {
					if (woSetMbo.getMbo(0).getString("STATUS").equals("等待批准")) {
						super.toggledeleterow();
						woSetMbo.getMbo(0).delete(2L);
						woSetMbo.save();
					} else {
						throw new MXApplicationException("WORKORDER", "DELECTPIAO");
					}
				}
				return 1;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 1;
	}
}