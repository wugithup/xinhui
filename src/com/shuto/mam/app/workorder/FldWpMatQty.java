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
public class FldWpMatQty extends MboValueAdapter {
	public FldWpMatQty(MboValue mbv) {
		super(mbv);
	}
	
	public void action()throws MXException, RemoteException {
		double ucost = 0.0D; 
		double qty = 0.0D;
		MboValue mboRate = getMboValue("UnitCost");
		if (!mboRate.isNull()) {
			ucost = mboRate.getDouble();
		}
		MboValue mboQty = getMboValue();
		if (!mboQty.isNull()) {
			qty = mboQty.getDouble();
		}
		getMboValue("LineCost").setValue(qty * ucost, 2L);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		MboValue mboQty = getMboValue();
		if (mboQty.isNull()) {
			throw new psdi.util.MXApplicationException("提示", " 物料数量不能为空!");
		} else {
			Double itemqty = mboQty.getDouble();
			if (itemqty <= 0.0D) {
				throw new psdi.util.MXApplicationException("提示", " 物料数量必须大于0!");
			}
		}
	}
	
}
