package com.shuto.mam.webclient.beans.wo.woticket;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.ConnectionKey;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Vector;

/**
 * @author xiamy@shuto.cn
 * @create 2018-01-12 14:50
 * @Copyright 2018 Shuto版权所有
 * @desc
 **/

public class SelTagoutmethod extends DataBean {
	@Override
	public synchronized int execute() throws MXException, RemoteException {
		super.execute();
		this.app.getAppBean().save();
		DataBean persondataBean = this.app.getDataBean("SELTAGOUTMETHOD");
		MboSetRemote diaSet = persondataBean.getMboSet();
		Vector selSet = diaSet.getSelection();
		MboRemote tagoutmehodmbo = null;
		StringBuilder sb = new StringBuilder();
		for (Object aSelSet : selSet) {
			tagoutmehodmbo = (MboRemote) aSelSet;
			sb.append(tagoutmehodmbo.getString("TAGOUTMETHOD")).append(",");
		}
		long wosafetylinkid = (long) this.clientSession.getAttribute("WOSAFETYLINKID");
		try {
			ConnectionKey key = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
			Connection connection = MXServer.getMXServer().getDBManager().getConnection(key);
			String value = sb.substring(0, sb.length() - 1);
			connection.createStatement().execute("update WOSAFETYLINK set TAGMETHOD='" + value + "' where WOSAFETYLINKID=" + wosafetylinkid + "");
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		app.getAppBean().refreshTable();
		app.getAppBean().reloadTable();
		clientSession.showMessageBox(clientSession.getCurrentEvent(), "system", "saverecord", (Object[]) null);
		return 1;
	}
}
