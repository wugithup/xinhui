package com.shuto.mam.webclient.beans.stpi.pi_post;

import java.rmi.RemoteException;
import java.text.DecimalFormat;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class Pi_postAppBean extends AppBean {

	public Pi_postAppBean() {
		super();
	}

	@Override
	public int INSERT() throws MXException, RemoteException {
		int insert = super.INSERT();
		int number = 1;
		String siteid = getMbo().getString("siteid");
		MboSetRemote resultSet = getMbo().getMboSet("$resultSet", "ST_PI_POST",
				"no = (select max(no) from ST_PI_POST where type = '点检'  and siteid = '" + siteid + "')");
		if (!resultSet.isEmpty()) {
			number = Integer.valueOf(resultSet.getMbo(0).getString("no").substring(3));
			++number;
		}
		DecimalFormat decimalFormat = new DecimalFormat("000");
		String tmp = decimalFormat.format(number);
		String strNo = "DGW" + tmp;
		getMbo().setValue("no", strNo, 11L);
		resultSet.close();
		return insert;
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		/*if (!(hasAuth())) {
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");
		}*/
		// 判断岗位人员是否为空
		if (getMbo().getMboSet("ST_PI_USER").isEmpty()) {
			throw new MXApplicationException("PI_POST", "NOPIUSER");
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

	@Override
	public int DELETE() throws MXException, RemoteException {
		// 岗位删除时判断是否被引用
		MboRemote mainMbo = app.getAppBean().getMbo();
		MboSetRemote cfgSet = mainMbo.getMboSet("ST_PI_TASKCFG");
		if (!cfgSet.isEmpty()) {
			throw new MXApplicationException("PI_POST", "CITED");
		}
		return super.DELETE();
	}
}
