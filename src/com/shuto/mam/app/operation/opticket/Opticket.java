/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.operation.opticket;

import com.shuto.mam.app.MockApp;
import com.shuto.mam.app.MockAppFactory;
import psdi.mbo.MboSet;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @Title: Opticket.java
 * @Description: TODO
 * @author: lull lull@shuto.cn
 * @date: 2017年5月5日 下午3:52:10
 * @version: V1.0.0
 */
public class Opticket extends psdi.mbo.Mbo implements OpticketRemote {

    private MockApp mockApp;

    public Opticket(MboSet ms) throws RemoteException {

        super(ms);
        mockApp = MockAppFactory.getNewAppInstance(this);
    }

    @Override
    public void initFieldFlagsOnMbo(String attrName) throws MXException {

        super.initFieldFlagsOnMbo(attrName);
        if (mockApp != null) {
            mockApp.initFieldFlagsOnMbo(attrName);
        }
    }

    @Override
    protected void save() throws MXException, RemoteException {

        super.save();
        if (mockApp != null) {
            mockApp.save();
        }
    }
}

