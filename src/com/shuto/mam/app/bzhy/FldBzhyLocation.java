package com.shuto.mam.app.bzhy;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;

public class FldBzhyLocation extends MAXTableDomain {

	public FldBzhyLocation(MboValue mbv) {
		super(mbv);
		setRelationship("LOCATIONS", "1=1");
		setListCriteria("1=1");
		String[] target = { this.getMboValue().getName() };
		String[] source = { "LOCATION" };
		setLookupKeyMapInOrder(target, source);
	}

}
