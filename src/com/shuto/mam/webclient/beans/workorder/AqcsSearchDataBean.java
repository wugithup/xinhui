package com.shuto.mam.webclient.beans.workorder;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class AqcsSearchDataBean extends DataBean {

	protected void initialize() throws MXException, RemoteException {

		super.initialize();
	}

	public int selectrecord() throws MXException, RemoteException {
		return super.selectrecord();
	}

	public void DIALOGSELECT() throws MXException, RemoteException {
		DataBean searchBean = this.app.getDataBean("multiselection_table1");

		DataBean selectdBean = this.app.getDataBean("multiselection_table2");
		MboSetRemote exprepinstruset = MXServer.getMXServer().getMboSet(
				"MIDTABLEFORAQCS", getMXSession().getUserInfo());
		Vector vector = searchBean.getSelection();

		for (Iterator iter = vector.iterator(); iter.hasNext();) {
			MboRemote element = (MboRemote) iter.next();
			MboRemote selectmbo = exprepinstruset.addAtEnd();
			selectmbo.setValue("LOCATION", element.getString("LOCATION"));
			selectmbo.setValue("DESCRIPTION", element.getString("DESCRIPTION"));
			selectmbo.getThisMboSet().save();
		}

		selectdBean.reloadTable();
		selectdBean.refreshTable();
		selectdBean.reset();
	}

	public void DIALOGCOMP() throws MXException, RemoteException {
		MboRemote mbo = app.getAppBean().getMbo();
		DataBean selectdBean = this.app.getDataBean("multiselection_table2");
		selectdBean.save();
		MboSetRemote midMboSet = mbo.getMboSet("$MIDTABLEFORAQCS",
				"MIDTABLEFORAQCS", "1=1");
		MboSetRemote wosMboSet = mbo.getMboSet("WOSAFETYLINK001");

		for (int i = 0; i < midMboSet.count(); i++) {
			MboRemote wosMbo = wosMboSet.addAtEnd();
			wosMbo.setValue("APPLYSEQ",
					midMboSet.getMbo(i).getString("APPLYSEQ"), 11L);
			wosMbo.setValue("TAGOUTID",
					midMboSet.getMbo(i).getString("LOCATION"), 11L);
			wosMbo.setValue("TAGOUTDESCRIPTION2", midMboSet.getMbo(i)
					.getString("DESCRIPTION"), 11L);
			wosMbo.setValue("TAGMETHOD",
					midMboSet.getMbo(i).getString("TAGOUTMETHOD"), 11L);
		}

		app.getAppBean().save();
		// app.getAppBean().reloadTable();
		// app.getAppBean().refreshTable();
	}

	public void del() throws MXException, RemoteException {
		MboRemote currentRowMbo = getMbo();
		MboSetRemote expMboSet = currentRowMbo.getMboSet("$MIDTABLEFORAQCS",
				"MIDTABLEFORAQCS", "1=1");
		for (int i = 0; i < expMboSet.count(); i++) {
			MboRemote expMbo = expMboSet.getMbo(i);
			expMbo.delete();
		}
		save();
		refreshTable();
		reset();
	}

	@Override
	public int cancelDialog() throws MXException, RemoteException {
		del();
		return 1;
	};

	@Override
	public synchronized int execute() throws MXException, RemoteException {
		DIALOGCOMP();
		del();
		return 1;
	}
}