/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.zsapply;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;

public class CustZsapplySet extends MboSet implements MboSetRemote {

    public CustZsapplySet(MboServerInterface ms) throws RemoteException {

        super(ms);
    }

    @Override
    protected Mbo getMboInstance(MboSet arg0) throws MXException, RemoteException {

        return new CustZsapply(arg0);
    }
}