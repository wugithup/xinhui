package com.shuto.mam.webclient.beans.wo.woticket;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class HazardhDataBean extends DataBean {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
		return super.getMboSetRemote();
	}

	public int addrow() throws MXException {
		super.addrow();
		try {
			String HAZARDID = "";
			try {
				this.conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
				String sql = "select HAZARDseq.nextval as HAZARDID from dual ";
				this.ps = this.conn.prepareStatement(sql);
				this.rs = this.ps.executeQuery();
				this.rs.next();
				HAZARDID = this.rs.getString("HAZARDID");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			setValue("HAZARDID", "WX" + HAZARDID);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return 1;
	}

	public synchronized void insertAtEnd() throws MXException, RemoteException {
		super.insertAtEnd();
	}
}