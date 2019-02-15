package com.shuto.mam.app.opma;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * com.shuto.mam.app.opma.FldPerson.java
 * <p>
 * Email:xiamy@shuto.cn 2017年8月29日 下午10:26:09
 */
public class FldPerson extends MAXTableDomain {
    public FldPerson(MboValue mbv) {
        super(mbv);
        setRelationship("person", "1=1");
        setListCriteria("1=1");
        String[] target = {getMboValue().getName()};
        String[] source = {"personid"};
        setLookupKeyMapInOrder(target, source);
    }

    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote mainMbo = getMboValue().getMbo();
        String siteid = mainMbo.getString("SITEID");
        if ("".equals(siteid)) {
            siteid = mainMbo.getInsertSite();
        }
        setListCriteria("LOCATIONSITE = '" + siteid + "' and STATUS='活动' ");
        return super.getList();
    }
}
