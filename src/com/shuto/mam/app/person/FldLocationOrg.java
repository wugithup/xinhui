package com.shuto.mam.app.person;

import java.rmi.RemoteException;

import psdi.app.person.FldPersonLocationOrg;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @Title: FldLocationOrg.java
 * @Description: TODO
 * @author: lull lull@shuto.cn
 * @date: 2017年4月21日 下午3:25:14
 * @version: V1.0.0
 */
public class FldLocationOrg extends FldPersonLocationOrg {
	// com.shuto.mam.app.person.FldLocationOrg

	/**
	 * Title: Description:
	 * 
	 * @param mbv
	 * @throws MXException
	 */
	public FldLocationOrg(MboValue mbv) throws MXException {
		super(mbv);
	}

	/**
	 * <p>
	 * Title: getList
	 * <p>
	 * Description:
	 * 
	 * @return
	 * @throws MXException
	 * @throws RemoteException
	 * @see psdi.app.site.FldOrganizationID#getList()
	 */
	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("active =1");
		return super.getList();
	}

	/**
	 * <p>
	 * Title: action
	 * <p>
	 * Description:
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 * @see psdi.app.person.FldPersonLocationOrg#action()
	 */
	@Override
	public void action() throws MXException, RemoteException {
		getMboValue("locationsite").setValue("", 2l);
		super.action();
	}

	/**
	 * <p>
	 * Title: init
	 * <p>
	 * Description:
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 * @see psdi.mbo.MboValueAdapter#init()
	 */
	@Override
	public void init() throws MXException, RemoteException {
		MboRemote mbo = getMboValue().getMbo();
		String siteid = mbo.getInsertSite();

		MboSetRemote siteSet = mbo.getMboSet("$site", "site", "siteid = '" + siteid + "'");
		String yetai = "";
		if (!siteSet.isEmpty()) {
			yetai = siteSet.getMbo(0).getString("yetai");
		}
		siteSet.clear();
		siteSet.close();
		if ("控股".equals(yetai)) {

		} else if ("本部".equals(yetai)) {

		} else {
//			getMboValue("POST").setFlag(Mbo.REQUIRED, true);
//			getMboValue("DEPARTMENT").setFlag(Mbo.REQUIRED, true);
			getMboValue("locationorg").setFlag(Mbo.READONLY, true);
			getMboValue("locationsite").setFlag(Mbo.READONLY, true);

		}
		super.init();
	}
}
