package com.shuto.mam.webclient.beans.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class AqcsSingeDataBean extends DataBean {
	public int selectrecord() throws MXException, RemoteException {
		MboRemote selectedMbo = getMboSet().getMbo();

		String woNum = this.app.getAppBean().getString("WONUM");
		MboSetRemote woMboSet = selectedMbo.getMboSet("$WOSAFETYLINK",
				"WOSAFETYLINK", "WONUM='" + woNum + "' AND HAZARDID='YXBXAC'");
		DataBean selectdBean = this.app.getDataBean("multiselection_table2");
		MboSetRemote midtableSet = MXServer.getMXServer().getMboSet(
				"MIDTABLEFORAQCS", getMXSession().getUserInfo());
		MboRemote addmbo = midtableSet.addAtEnd();
		if (woMboSet == null) {
			addmbo.setValue("APPLYSEQ", midtableSet.count());
		} else if (woMboSet != null) {
			addmbo.setValue("APPLYSEQ", woMboSet.count() + midtableSet.count());
		}

		addmbo.setValue("LOCATION", selectedMbo.getString("LOCATION"));
		addmbo.setValue("DESCRIPTION", selectedMbo.getString("DESCRIPTION"));
		addmbo.getThisMboSet().save();

		selectdBean.reloadTable();
		selectdBean.refreshTable();
		selectdBean.reset();

		return 1;
	}
}