/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.gzrd;

import psdi.mbo.MboRemote;
import psdi.security.SecurityService;
import psdi.security.SecurityServiceRemote;
import psdi.server.MXServer;

import java.rmi.RemoteException;

public class SsoService extends SecurityService implements SecurityServiceRemote {

    public final static ThreadLocal<String> sso = new ThreadLocal<String>();

    public SsoService(MXServer mxServer) throws RemoteException {

        super(mxServer);
    }

    @Override
    protected void verifyUser(String userName, String enteredPassword, MboRemote userMbo)
            throws Exception {

        if (sso.get() == null) {
            super.verifyUser(userName, enteredPassword, userMbo);
        }

    }

}
