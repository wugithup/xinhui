package com.shuto.mam.webclient.beans.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.beans.workorder.SelectWOBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class SelectSrBean extends SelectWOBean {
	public void initialize() throws MXException, RemoteException {
		super.initialize();
		String siteid = this.app.getAppBean().getMbo().getString("siteid");
		MboSetRemote relatedWoSet = this.app.getAppBean().getMbo().getMboSet("RELATEDTICKET");
		StringBuffer where = null;
		for (int i = 0; i < relatedWoSet.count(); i++) {
			MboRemote relatedWo = relatedWoSet.getMbo(i);
			if (!relatedWo.toBeDeleted()) {
				if (where == null)
					where = new StringBuffer("ticketid not in('").append(relatedWo.getString("RELATEDRECKEY"));
				else {
					where.append("','").append(relatedWo.getString("RELATEDRECKEY"));
				}
			}
		}
		if (where == null)
			where = new StringBuffer(" siteid = '" + siteid + "'");
		else {
			where.append("') and siteid = '" + siteid + "'");
		}
		System.out.println("地点："+where);
		getMboSet().setWhere(where.toString());
	}

	public void COPYWTTORELATEDRECSET() throws MXException, RemoteException {
		MboSetRemote relatedWoSet = this.app.getAppBean().getMbo().getMboSet("RELATEDWO");
		String[] srcAttributes = { "siteid", "orgid", "woclass", "wonum" };

		String[] destAttributes = { "relatedrecsiteid", "relatedrecorgid", "relatedrecwoclass", "relatedrecwonum" };

		relatedWoSet.copy(getMboSet(), srcAttributes, destAttributes);
		Utility.sendEvent(new WebClientEvent("dialogclose", this.app.getCurrentPageId(), null, this.sessionContext));
		this.app.getDataBean("relatedrec_related_ticket").refreshTable();
		this.sessionContext.queueRefreshEvent();
		this.app.getAppBean().save();
	}
}