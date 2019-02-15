/**
 * 
 */
package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @author ITROBOT
 *
 */
public class FldIfOrgtype extends MAXTableDomain {

	public FldIfOrgtype(MboValue mbv) {
		super(mbv);
		setRelationship("ORGMAP", "1=1");
		setListCriteria("1=1");
		String[] target = { "INTERFACEORGTYPE" }; 
		String[] source = { "INTERFACEORGTYPE" };
		setLookupKeyMapInOrder(target, source);
	}
	
	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = this.getMboValue().getMbo();
		MboSetRemote orgmaps = mainMbo.getMboSet("$orgmap", "ORGMAP", "maxorg=:orgid and maxsite=:siteid");
		return orgmaps;
	}
}
