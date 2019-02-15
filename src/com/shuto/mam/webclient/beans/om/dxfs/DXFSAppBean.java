package com.shuto.mam.webclient.beans.om.dxfs;

import java.rmi.RemoteException;
import java.sql.SQLException;

import com.shuto.mam.fycrp.PhoneMessage;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * com.shuto.mam.webclient.beans.om.dxfs.DXFSAppBean.java
 * 
 * Email:xiamy@shuto.cn 2017年8月30日 下午9:34:26
 *
 */
public class DXFSAppBean extends AppBean {
	public int INSERT() throws MXException, RemoteException {
		super.INSERT();
		super.save();
		getMbo().setFieldFlag("DESCRIPTION", 128L, true);
		return 1;
	}

	public void SEND() throws RemoteException, MXException {
		MboRemote mbo = getMbo();

		WebClientEvent event = this.clientSession.getCurrentEvent();
		int er = event.getMessageReturn();
		if (er == 2) {
			String fromPersonId = mbo.getString("CREATEBY");
			String fromUserName = mbo.getString("CREATEBY.DISPLAYNAME");
			String fromDepartment = "";
			String Message = mbo.getString("DESCRIPTION");
			String num = mbo.getString("DXFSDETAILNUM");
			MboSetRemote depmboSet = MXServer.getMXServer().getMboSet("PERSON", mbo.getUserInfo());

			depmboSet.setWhere("PERSONID='" + fromPersonId + "'");
			if (depmboSet.count() == 1) {
				fromDepartment = depmboSet.getMbo(0).getString("DEPARTMENT.DESCRIPTION");
			}
			depmboSet.close();

			PhoneMessage pm = null;
			try {
				String Where = "DXFSDETAILNUM='" + num + "'" + "and personid  in"
						+ " (select personid from person where MOBILEPHONE  is not null)";
				MboSetRemote PersongroupteamSet = mbo.getMboSet("$DXFSRYB", "DXFSRYB", Where);
				if (PersongroupteamSet.count() == 0) {
					Utility.showMessageBox(this.clientSession.getCurrentEvent(), "", " 请选择发送人员！ ", 1);
				}
				MboRemote PersongroupteamMbo = null;
				for (int i = 0; i < PersongroupteamSet.count(); ++i) {
					PersongroupteamMbo = PersongroupteamSet.getMbo(i);
					pm = new PhoneMessage();
					pm.addMessage(PersongroupteamMbo.getString("PERSONID"),
							PersongroupteamMbo.getString("DEPNUM.DESCRIPTION"),
							PersongroupteamMbo.getString("PERSONID.DISPLAYNAME"), fromPersonId, fromDepartment,
							fromUserName, PersongroupteamMbo.getString("PERSONID.MOBILEPHONE"),
							Message + " 自 " + fromUserName);

					pm.commit();
				}

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

		throw new MXApplicationYesNoCancelException(mbo.getString("DESCRIPTION"), "ST_OPLOG", "ST_OPLOG");
	}
}