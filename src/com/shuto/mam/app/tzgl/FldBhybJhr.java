package com.shuto.mam.app.tzgl;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;

public class FldBhybJhr extends MAXTableDomain {

	public FldBhybJhr(MboValue mbv) {
		super(mbv);
		setRelationship("PERSON", "1=1");
		String[] strTo = { "JHR" };
		String[] strFrom = { "DISPLAYNAME" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

}
