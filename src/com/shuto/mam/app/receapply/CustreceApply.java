/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.receapply;

import com.shuto.mam.app.MockApp;
import com.shuto.mam.app.MockAppFactory;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author SumMer
 * @version V1.0
 * @Title: Custreceapply
 * @Package com.shuto.mam.app.receapply
 * @Description:
 * @date 2018-08-30 18:48
 */
public class CustreceApply extends Mbo implements MboRemote {

    MockApp mockApp;

    public CustreceApply(MboSet ms) throws RemoteException {

        super(ms);
        mockApp = MockAppFactory.getNewAppInstance(this);
    }

    @Override
    protected void save() throws MXException, RemoteException {

        super.save();
        if (mockApp != null) {
            mockApp.save();
        }
    }
}
