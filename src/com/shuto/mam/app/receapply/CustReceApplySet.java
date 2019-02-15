/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.receapply;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author SumMer
 * @version V1.0
 * @Title: CustReceapplySet
 * @Package com.shuto.mam.app.receapply
 * @Description:
 * @date 2018-08-30 18:48
 */
public class CustReceApplySet extends MboSet implements MboSetRemote {

    public CustReceApplySet(MboServerInterface ms) throws RemoteException {

        super(ms);
    }

    @Override
    protected Mbo getMboInstance(MboSet mboSet) throws MXException, RemoteException {

        return new CustreceApply(mboSet);
    }
}
