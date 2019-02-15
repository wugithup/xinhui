package com.shuto.mam.webclient.beans.om.dxfs;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 *
 * com.shuto.mam.webclient.beans.om.dxfs.SelectedPersonDataBean.java
 * 
 * Email:xiamy@shuto.cn 2017年8月30日 下午9:35:30
 *
 */
public class SelectedPersonDataBean extends DataBean {
	public void selectPerson() throws RemoteException, MXException {
		save();
		this.app.getDataBean("RY").save();
	}
}
