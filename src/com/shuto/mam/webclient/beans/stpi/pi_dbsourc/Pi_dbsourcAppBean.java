package com.shuto.mam.webclient.beans.stpi.pi_dbsourc;

import java.rmi.RemoteException;
import java.text.DecimalFormat;

import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class Pi_dbsourcAppBean extends AppBean {

	public Pi_dbsourcAppBean() {
		super();
	}

	@Override
	public int INSERT() throws MXException, RemoteException {
		int insert = super.INSERT();
		int number = 1;
		MboSetRemote resultSet = getMbo().getMboSet("$resultSet", "ST_PI_DBSOURCE",
				"DBSOURCENO = (select max(DBSOURCENO) from ST_PI_DBSOURCE)");
		if (!resultSet.isEmpty()) {
			number = Integer.valueOf(resultSet.getMbo(0).getString("DBSOURCENO").substring(2));
			++number;
		}
		DecimalFormat decimalFormat = new DecimalFormat("000");
		String tmp = decimalFormat.format(number);
		String strNo = "DB" + tmp;
		getMbo().setValue("DBSOURCENO", strNo, 11L);
		resultSet.close();
		return insert;
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		if (!(hasAuth())) {
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");
		}
		return super.SAVE();
	}

	public boolean hasAuth() throws MXException, RemoteException {
		String createby = getMbo().getString("CREATEBY");
		String s1 = getMbo().getUserInfo().getPersonId();
		if (s1.equalsIgnoreCase("maxadmin"))
			return true;
		if (s1.equalsIgnoreCase(createby))
			return true;
		return false;
	}
}
