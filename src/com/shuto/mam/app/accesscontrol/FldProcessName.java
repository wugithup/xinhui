package com.shuto.mam.app.accesscontrol;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

public class FldProcessName extends MAXTableDomain {
    public FldProcessName(MboValue mbv) {
        super(mbv);
        setRelationship("WFPROCESS", "enabled=1");
        String[] strTo = {"processname", "processrev"};
        String[] strFrom = {"processname", "processrev"};
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        return super.getList();
    }

    @Override
    public void action() throws MXException, RemoteException {
        getMboValue("nodeid").setValueNull();
        getMboValue("description").setValueNull();
        getMboValue("relationship").setValueNull();
        super.action();
    }
}