/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.carapply;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author SumMer
 * @version V1.0
 * @Title: CustCarapplySet
 * @Package com.shuto.mam.app.carapply
 * @Description:
 * @date 2018-08-30 18:19
 */
public class CustCarapplySet extends MboSet implements MboSetRemote {

    public CustCarapplySet(MboServerInterface ms) throws RemoteException {

        super(ms);
    }

    @Override
    protected Mbo getMboInstance(MboSet mboSet) throws MXException, RemoteException {

        return new CustCarapply(mboSet);
    }
}
