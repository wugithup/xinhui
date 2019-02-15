package com.shuto.mam.webclient.beans.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class AqcsSelectDataBean extends DataBean {

	public void DELETEINSTRU() throws MXException, RemoteException {
		MboRemote currentRowMbo = getMbo();
		currentRowMbo.delete();
		save();
		refreshTable();
		reset();
	}

}