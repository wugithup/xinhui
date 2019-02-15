package com.shuto.mam.app.person;

import java.rmi.RemoteException;

import psdi.app.person.FldPersonLocationSite;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @Title: FldLocationSite.java
 * @Description: TODO
 * @author: lull lull@shuto.cn
 * @date: 2017年4月20日 下午11:18:03
 * @version: V1.0.0
 */
public class FldLocationSite extends FldPersonLocationSite {
	// com.shuto.mam.app.person.FldLocationSite

	/**
	 * Title: Description:
	 * 
	 * @param mbv
	 * @throws MXException
	 */
	public FldLocationSite(MboValue mbv) throws MXException {
		super(mbv);
		// TODO Auto-generated constructor stub
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
	 * @see psdi.app.site.FldSiteID#getList()
	 */
	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		String orgid = getMboValue("locationorg").getString();
		setListCriteria("active = 1 and orgid = '" + orgid + "'");
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
	 * @see psdi.app.person.FldPersonLocationSite#action()
	 */
	@Override
	public void action() throws MXException, RemoteException {
		getMboValue("DEPARTMENT").setValue("", 2l);
		super.action();
	}

}
