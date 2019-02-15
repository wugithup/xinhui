package com.shuto.mam.app.tzgl;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;

public class FldSbydZxr extends MAXTableDomain {

	public FldSbydZxr(MboValue mbv) {
		super(mbv);
		setRelationship("PERSON", "1=1");
		String[] strTo = { "ZXR" };
		String[] strFrom = { "DISPLAYNAME" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

}
