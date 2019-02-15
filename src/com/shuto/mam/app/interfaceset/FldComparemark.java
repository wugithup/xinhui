package com.shuto.mam.app.interfaceset;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;

public class FldComparemark extends MAXTableDomain {

	public FldComparemark(MboValue mbv) {
		super(mbv);
		setRelationship("COMPAREMARK", "1=1");
	    String[] strFrom = {"value"};
	    String[] strTo = {"comparemark"};
	    setLookupKeyMapInOrder(strTo, strFrom);
	}

}
