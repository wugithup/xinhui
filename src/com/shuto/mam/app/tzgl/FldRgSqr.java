package com.shuto.mam.app.tzgl;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;

public class FldRgSqr extends MAXTableDomain {

	public FldRgSqr(MboValue mbv) {
		super(mbv);
		setRelationship("PERSON", "1=1");
		String[] strTo = { "SQR" };
		String[] strFrom = { "DISPLAYNAME" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

}
