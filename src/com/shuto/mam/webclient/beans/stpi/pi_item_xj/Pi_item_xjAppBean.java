package com.shuto.mam.webclient.beans.stpi.pi_item_xj;

import java.rmi.RemoteException;
import java.text.DecimalFormat;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class Pi_item_xjAppBean extends AppBean {

	public Pi_item_xjAppBean() {
		super();
	}

	@Override
	public int INSERT() throws MXException, RemoteException {
		int insert = super.INSERT();
		int number = 1;
		String siteid = getMbo().getString("siteid");
		MboSetRemote resultSet = getMbo().getMboSet("$resultSet", "ST_PI_ITEM",
				"no = (select max(no) from ST_PI_ITEM where type = '巡检' and siteid = '" + siteid + "')");
		if (!resultSet.isEmpty()) {
			number = Integer.valueOf(resultSet.getMbo(0).getString("no").substring(3));
			++number;
		}
		DecimalFormat decimalFormat = new DecimalFormat("0000");
		String tmp = decimalFormat.format(number);
		String strNo = "XXM" + tmp;
		getMbo().setValue("no", strNo, 11L);
		resultSet.close();
		return insert;
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		/*if (!(hasAuth())) {
			throw new MXApplicationException("hasnoAuth", "hasnoAuth");
		}*/
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
		// 项目删除时判断是否被引用
		MboRemote mainMbo = app.getAppBean().getMbo();
		MboSetRemote areaSet = mainMbo.getMboSet("ST_PI_AREA_ITEM");
		if (!areaSet.isEmpty()) {
			throw new MXApplicationException("PI_ITEM", "CITED");
		}
		return super.DELETE();
	}
}
