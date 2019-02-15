/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.pm;

import psdi.app.pm.PMSet;
import psdi.app.pm.PMSetRemote;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author SumMer
 * @version V1.0
 * @Title: CustPmSet
 * @Package com.shuto.mam.app.pm
 * @Description:
 * @date 2018-12-19 019 11:21
 */
public class CustPmSet extends PMSet implements PMSetRemote, MboSetRemote {

    public CustPmSet(MboServerInterface ms) throws MXException, RemoteException {

        super(ms);
    }

    @Override
    protected Mbo getMboInstance(MboSet mboSet) throws MXException, RemoteException {

        return new CustPm(mboSet);
    }
}
