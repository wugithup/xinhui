package com.shuto.mam.webclient.beans.ticket;

import java.rmi.RemoteException;

import psdi.app.ticket.SRRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class FailureCodesBean extends DataBean {
	protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		DataBean msb = this.app.getAppBean();

		SRRemote sr = null;
		MboSetRemote failListSet = null;
		try {
			sr = (SRRemote) msb.getMbo();
			return sr.getFailListForReport();
		} catch (MXException mxe) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), mxe);
		}
		return null;
	}

	public int selectrecord() throws MXException {
		try {
			return SELECT();
		} catch (Exception localException) {
		}
		return 1;
	}

	public int SELECT() throws MXException, RemoteException {
		WebClientEvent event = this.sessionContext.getCurrentEvent();
		int retValue = selectfail();
		structureChangedEvent(this);
		if (isEmpty())
			this.sessionContext.queueEvent(new WebClientEvent("dialogok", this.app.getCurrentPageId(), "", this.sessionContext));
		else {
			this.sessionContext.queueRefreshEvent();
		}
		return retValue;
	}

	public int SELECTRETURN() throws MXException, RemoteException {
		WebClientEvent event = this.sessionContext.getCurrentEvent();
		int retValue = selectfail();
		Utility.sendEvent(new WebClientEvent("dialogok", this.app.getCurrentPageId(), null, this.sessionContext));
		return retValue;
	}

	private int selectfail() throws MXException, RemoteException {
		int retValue = 1;
		DataBean appBean = Utility.getDataSource(this.sessionContext, this.app.getAppHandler());
		WebClientEvent event = this.sessionContext.getCurrentEvent();
		int row = getRowIndexFromEvent(event);
		if (appBean != null) {
			SRRemote sr = (SRRemote) appBean.getMbo();
			MboSetRemote faillistSet = getMboSet();
			MboSetRemote FailReport = sr.getMboSet("FAILUREREPORT");
			MboRemote faillist = faillistSet.getMbo(row);
			if (faillist != null)
				faillist.select();
			else
				retValue = 1;
			try {
				sr.copyFailListToReportSet(faillistSet);
			} catch (MXException mxe) {
				Utility.showMessageBox(this.sessionContext.getCurrentEvent(), mxe);
				appBean.save();
			}
			appBean.save();
		}
		return retValue;
	}
}