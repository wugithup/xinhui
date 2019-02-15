package com.shuto.mam.webclient.beans.tz.opbill.doclinks;

import java.rmi.RemoteException;

import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author lzq
 * @date 创建时间：2017-3-24 上午9:31:27
 * @since 原华南台账相关类
 */
public class OPAddDocLinksBeanTable extends OPRegisterDocBeanTable {
	DataBean attachments = null;

	public OPAddDocLinksBeanTable() throws RemoteException, MXException {
	}

	@Override
	protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		MboSetRemote msr = super.getMboSetRemote();
		return msr;
	}

	@Override
	public synchronized void insert() throws MXException, RemoteException {
		super.insert();

		dataChangedEvent(this);
	}

	@Override
	public synchronized void close() {
		if (this.dialogReferences > 0) {
			this.dialogReferences -= 1;
			return;
		}

		resetDataBean();
		cleanup();
	}

	@Override
	public int execute() throws MXException, RemoteException {
		int retVal = super.execute();
		validate();
		try {
			if (this.app.getAppBean() != null)
				((AppBean) this.app.getAppBean()).saveattachment();
			else
				save();
		} catch (MXException e) {
			WebClientEvent currentEvent = this.sessionContext.getCurrentEvent();
			Utility.showMessageBox(currentEvent, e);
			getMboSet().reset();
			return 1;
		}
		if (this.parent != null)
			this.parent.fireStructureChangedEvent();
		return retVal;
	}

	@Override
	public int cancelDialog() throws MXException, RemoteException {
		getMboSet().reset();
		int ret = super.cancelDialog();
		try {
			validate();
		} catch (MXException e) {
			this.mboSetRemote.deleteAndRemove(getMbo(), 2L);
		}
		return ret;
	}

	public int passSuberExecute() throws MXException, RemoteException {
		int retVal = super.execute();
		return retVal;
	}
}