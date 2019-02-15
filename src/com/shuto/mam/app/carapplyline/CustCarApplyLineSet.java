/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.carapplyline;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author SumMer
 * @version V1.0
 * @Title: CustCarapplyLineSet
 * @Package com.shuto.mam.app
 * @Description:
 * @date 2018-08-29 18:44
 */
public class CustCarApplyLineSet extends MboSet implements MboSetRemote {

    public CustCarApplyLineSet(MboServerInterface ms) throws RemoteException {

        super(ms);

    }

    @Override
    protected Mbo getMboInstance(MboSet mboSet) throws MXException, RemoteException {

        return new CustCarApplyLine(mboSet);
    }
}
