/**
 * 
 */
package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Administrator
 *
 */
public class FldWpMatCost extends MboValueAdapter {
	public FldWpMatCost(MboValue mbv) {
		super(mbv);
	}
	
	public void action() throws MXException, RemoteException {
		double qty = 0.0D;
		double lineCost = 0.0D;
		qty = getMboValue("itemqty").getDouble();
		lineCost = getMboValue().getDouble() * qty;
		getMboValue("linecost").setValue(lineCost, 2L);
	}
}
