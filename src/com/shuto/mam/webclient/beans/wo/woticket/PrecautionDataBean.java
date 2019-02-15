package com.shuto.mam.webclient.beans.wo.woticket;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import psdi.mbo.MboSetRemote;
import psdi.server.DBManager;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class PrecautionDataBean extends DataBean {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
		return super.getMboSetRemote();
	}

	public int addrow() throws MXException {
		super.addrow();
		try {
			String PRECAUTIONID = "";
			try {
				this.conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
				String sql = "select PRECAUTIONSEQ.nextval as PRECAUTIONID from dual ";
				this.ps = this.conn.prepareStatement(sql);
				this.rs = this.ps.executeQuery();
				this.rs.next();
				PRECAUTIONID = this.rs.getString("PRECAUTIONID");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			setValue("PRECAUTIONID", "CS" + PRECAUTIONID);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return 1;
	}

	public synchronized void insertAtEnd() throws MXException, RemoteException {
		super.insertAtEnd();
	}
}