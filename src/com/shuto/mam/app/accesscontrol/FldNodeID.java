package com.shuto.mam.app.accesscontrol;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

public class FldNodeID extends MAXTableDomain {
    public FldNodeID(MboValue mbv) {
        super(mbv);
        setRelationship("WFNODE", "");
        String[] strTo = {"nodeid"};
        String[] strFrom = {"nodeid"};
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        String processName = getMboValue("PROCESSNAME").getString();
        String processrev = getMboValue("PROCESSREV").getString();
        setListCriteria("nodetype = '任务' and PROCESSNAME = '" + processName + "'  and processrev = '" + processrev + "'");
        return super.getList();
    }
}