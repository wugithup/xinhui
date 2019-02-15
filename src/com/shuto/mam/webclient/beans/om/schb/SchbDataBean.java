package com.shuto.mam.webclient.beans.om.schb;

import java.rmi.RemoteException;
import java.sql.SQLException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

import com.shuto.mam.fycrp.PhoneMessage;

public class SchbDataBean extends DataBean {
	public int addrow() throws MXException {
		try {
			super.addrow();
			MboRemote Mbo = getMbo();
			Mbo.setValue("TYPE", "ZYSJ", 11L);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int toggledeleterow() throws MXException {
		try {
			MboRemote Mbo = getMbo();
			String issend = Mbo.getString("ISSEND");
			if ("N".equals(issend))
				super.toggledeleterow();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public void SEND() throws RemoteException, MXException {
		MboRemote Mbo = getMbo();
		String user = Mbo.getUserInfo().getPersonId();
		String createby = Mbo.getString("CREATEBY");
		if (user.equals(createby)) {
			WebClientEvent event = this.clientSession.getCurrentEvent();
			int er = event.getMessageReturn();
			if (er == 2) {
				this.app.getAppBean().save();
				Mbo.setValue("ISSEND", true, 11L);
				Mbo.getThisMboSet().save();

				String fromPersonId = Mbo.getString("CREATEBY");
				String fromUserName = Mbo.getString("CREATEBY.DISPLAYNAME");
				String fromDepartment = "";
				String Message = Mbo.getString("DESCRIPTION");
				MboSetRemote depmboSet = MXServer.getMXServer().getMboSet("PERSON", Mbo.getUserInfo());

				depmboSet.setWhere("PERSONID='" + fromPersonId + "'");
				if (depmboSet.count() == 1) {
					fromDepartment = depmboSet.getMbo(0).getString("DEPARTMENT.DESCRIPTION");
				}
				depmboSet.close();

				PhoneMessage pm = null;
				try {
					String Where = "persongroup='SCHB_ZYSJ' and RESPPARTYGROUP  in (select personid from person where MOBILEPHONE  is not null)";

					MboSetRemote PersongroupteamSet = Mbo.getMboSet("$PERSONGROUPTEAM", "PERSONGROUPTEAM", Where);
					MboRemote PersongroupteamMbo = null;
					for (int i = 0; i < PersongroupteamSet.count(); i++) {
						PersongroupteamMbo = PersongroupteamSet.getMbo(i);
						pm = new PhoneMessage();
						pm.addMessage(PersongroupteamMbo.getString("RESPPARTYGROUP_PERSONS.PERSONID"),
								PersongroupteamMbo.getString("RESPPARTYGROUP_PERSONS.DEPARTMENT.DESCRIPTION"),
								PersongroupteamMbo.getString("RESPPARTYGROUP_PERSONS.DISPLAYNAME"), fromPersonId,
								fromDepartment, fromUserName,
								PersongroupteamMbo.getString("RESPPARTYGROUP_PERSONS.mobilephone"),
								fromDepartment + " " + fromUserName + " " + Message);

						if (pm != null) {
							pm.commit();
						}
					}
					PersongroupteamSet.close();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					try {
						pm.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}

				return;
			}
			throw new MXApplicationYesNoCancelException(Mbo.getString("DESCRIPTION"), "ST_OPLOG", "ST_OPLOG");
		}
	}
}