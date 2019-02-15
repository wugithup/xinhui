package com.shuto.mam.app.tzgl;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;

public class FldJcperson extends MAXTableDomain {

	public FldJcperson(MboValue mbv) {
		super(mbv);
		setRelationship("PERSON", "1=1");
		String[] strTo = { "JCPERSON" };
		String[] strFrom = { "DISPLAYNAME" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}
}
