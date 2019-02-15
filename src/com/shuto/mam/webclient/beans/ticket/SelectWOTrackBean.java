package com.shuto.mam.webclient.beans.ticket;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.beans.workorder.SelectWOBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.controller.SessionContext;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class SelectWOTrackBean extends SelectWOBean {
	public void initialize() throws MXException, RemoteException {
		super.initialize();
		//
		// SRAppBean srAB = (SRAppBean)this.app.getAppBean();
		// String status = srAB.getString("STATUS");
		// srAB.canCREATEWO(status, "关联工单");
		String siteid = this.app.getAppBean().getMbo().getString("siteid");
		MboSetRemote relatedWoSet = this.app.getAppBean().getMbo().getMboSet("RELATEDWO");
		StringBuffer where = null;
		for (int i = 0; i < relatedWoSet.count(); i++) {
			MboRemote relatedWo = relatedWoSet.getMbo(i);
			if (!relatedWo.toBeDeleted()) {
				if (where == null)
					where = new StringBuffer("wonum not in('").append(relatedWo.getString("relatedrecwonum"));
				else {
					where.append("','").append(relatedWo.getString("relatedrecwonum"));
				}
			}
		}
		if (where == null)
			where = new StringBuffer("workType in ('检修工单','缺陷工单')");
		else {
			where.append("') and workType in ('检修工单','缺陷工单')");
		}
		where.append(" and siteid = '" + siteid + "'");
		getMboSet().setWhere(where.toString());
	}

	public void COPYWTTORELATEDRECSET() throws MXException, RemoteException {
		MboSetRemote relatedWoSet = this.app.getAppBean().getMbo().getMboSet("RELATEDWO");
		String[] srcAttributes = { "siteid", "orgid", "woclass", "wonum" };

		String[] destAttributes = { "relatedrecsiteid", "relatedrecorgid", "relatedrecwoclass", "relatedrecwonum" };

		relatedWoSet.copy(getMboSet(), srcAttributes, destAttributes);
		Utility.sendEvent(new WebClientEvent("dialogclose", this.app.getCurrentPageId(), null, this.sessionContext));
		this.app.getDataBean("relatedrec_related_wo").refreshTable();
		this.sessionContext.queueRefreshEvent();
		this.app.getAppBean().save();
	}
}