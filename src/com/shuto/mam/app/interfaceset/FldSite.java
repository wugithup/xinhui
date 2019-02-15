/**
 * 
 */
package com.shuto.mam.app.interfaceset;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @author ITROBOT
 *
 */
public class FldSite extends MAXTableDomain {

	public FldSite(MboValue mbv) {
		super(mbv);
		setRelationship("site", "1=1");
	    String[] strFrom = {"siteid","orgid"};
	    String[] strTo = {"siteid","orgid"};
	    setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
	   return super.getList();
	}
}
