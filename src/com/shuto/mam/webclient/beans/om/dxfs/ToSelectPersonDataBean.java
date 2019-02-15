package com.shuto.mam.webclient.beans.om.dxfs;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 *
 * com.shuto.mam.webclient.beans.om.dxfs.ToSelectPersonDataBean.java
 * 
 * Email:xiamy@shuto.cn 2017年8月30日 下午9:35:51
 *
 */
public class ToSelectPersonDataBean extends DataBean {
	public MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		DataBean person = this.app.getDataBean("P");
		MboSetRemote personSet = person.getMboSet();
		String num = this.app.getAppBean().getMbo().getString("DXFSDETAILNUM");
		String siteid = this.app.getAppBean().getMbo().getString("SITEID");

		String where = "status ='活动' and locationsite = '" + siteid
				+ "' and personid not in (select personid from DXFSRYB where DXFSDETAILNUM = '" + num + "')";
		personSet.setWhere(where);
		return super.getMboSetRemote();
	}

	public int execute() throws RemoteException, MXException {
		DataBean ry = this.app.getDataBean("RY");
		MboSetRemote rySet = ry.getMboSet();
 
		DataBean person = this.app.getDataBean("P");
		MboSetRemote personSet = person.getMboSet();
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		String num = mainMbo.getString("DXFSDETAILNUM");

		MboRemote personMbo = null;
		String personid = "";
		String depName = "";
		MboRemote ryMbo = null;

		for (int i = 0; i < personSet.count(); ++i) {
			personMbo = personSet.getMbo(i);
			if (personMbo.isSelected()) {
				personid = personMbo.getString("personid");
				depName = personMbo.getString("DEPARTMENT");
				ryMbo = rySet.add();
				ryMbo.setValue("PERSONID", personid, 11L);
				ryMbo.setValue("DXFSDETAILNUM", num, 11L);
				ryMbo.setValue("depnum", depName, 11L);
			}
		}

		this.app.getDataBean("RY").save();
		return 1;
	}
}