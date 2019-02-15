package com.shuto.mam.webclient.beans.ticket;

import java.rmi.RemoteException;

import psdi.app.ticket.SRRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.RequestHandler;
import psdi.webclient.system.controller.Utility;

public class FailureBean extends DataBean {
	@SuppressWarnings("deprecation")
	public int listfailurecodes() throws MXException, RemoteException {
		DataBean appBean = Utility.getDataSource(this.sessionContext, this.app.getAppHandler());
		try {
			appBean.save();
			SRRemote sr = (SRRemote) appBean.getMbo();

			if ((sr.getBoolean("HistoryFlag")) && (!sr.isTicketInEditHist())) {
				String[] param = { sr.getString("ticketid"), sr.getString("class") };
				Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "workorder", "WOHistory", param);
				return 1;
			}

		} catch (MXException mxe) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), mxe);
			return 1;
		}

		RequestHandler.showDialog(this.sessionContext, "srlistfailurecodes");
		return 1;
	}
}