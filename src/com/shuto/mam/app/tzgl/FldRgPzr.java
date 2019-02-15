package com.shuto.mam.app.tzgl;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;

public class FldRgPzr extends MAXTableDomain {

	public FldRgPzr(MboValue mbv) {
		super(mbv);
		setRelationship("PERSON", "1=1");
		String[] strTo = { "PZR" };
		String[] strFrom = { "DISPLAYNAME" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

}
