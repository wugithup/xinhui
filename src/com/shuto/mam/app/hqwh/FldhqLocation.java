package com.shuto.mam.app.hqwh;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;

import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * @author quanhw
 * @email quanhw@shuto.cn
 * @create 2018-08-08 09:07
 * @desc
 * @class com.shuto.mam.app.hqwh.FldAsset
 * @Copyright: 2018 Shuto版权所有
 **/

public class FldhqLocation extends MAXTableDomain {

	public FldhqLocation(MboValue mbv) {
		super(mbv);
		setRelationship("LOCATIONS", "1=1");
        setListCriteria("1=1");
        String[] target = { this.getMboValue().getName() };
        String[] source = { "location" };
        setLookupKeyMapInOrder(target, source);
	}
	
	 @Override
	    public MboSetRemote getList() throws MXException, RemoteException {
	        MboRemote mainMbo = this.getMboValue().getMbo();
	        String siteid = mainMbo.getString("SITEID");
	        setListCriteria(" siteid = '" + siteid + "' and bm='ZHB'");
	        return super.getList();
	    }


}
