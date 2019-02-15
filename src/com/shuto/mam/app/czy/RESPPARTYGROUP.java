package com.shuto.mam.app.czy;

import psdi.app.persongroup.FldRespPartyGroup;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author
 * @email zhaowei@shuto.cn
 * @create 2017-11-27 14:33
 * @desc
 * @class com.shuto.mam.app.czy.RESPPARTYGROUP
 * @Copyright: 2017 Shuto版权所有
 **/

public class RESPPARTYGROUP extends FldRespPartyGroup {
    public RESPPARTYGROUP(MboValue mbv) throws MXException, RemoteException {
        super(mbv);
        String app=mbv.getMbo().getOwner().getThisMboSet().getApp();
//        String app=this.getMboValue().getMbo().getThisMboSet().getApp();
        if("RLPERGRP".equalsIgnoreCase(app)){
            setRelationship("PERSON_FY", "");
            String[] strFrom = new String[] { "personid" };
            String thisAttr = getMboValue().getAttributeName();
            String strTo[] = { thisAttr };
            setLookupKeyMapInOrder(strTo, strFrom);
        }
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        String app=this.getMboValue().getMbo().getOwner().getThisMboSet().getApp();
        if("RLPERGRP".equalsIgnoreCase(app)){
            setListCriteria("1=1");
        }
            return super.getList();
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
    }
}
