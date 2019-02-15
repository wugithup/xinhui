/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.receapply;

import com.shuto.mam.app.MockApp;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @author SumMer
 * @version V1.0
 * @Title: ReceApplyApp
 * @Package com.shuto.mam.app.receapply
 * @Description:
 * @date 2018-08-30 18:50
 */
public class ReceApplyApp extends MockApp {

    private MboRemote mbo;

    public ReceApplyApp(MboRemote newMbo) {

        super();
        this.mbo = newMbo;
    }

    @Override
    public void save() throws RemoteException, MXException {

        if ("RECEAPPLY".equalsIgnoreCase(this.getAppName())) {
            MboSetRemote receapp06MboSet = mbo.getMboSet("RECEAPP06");
            MboRemote receapp06Mbo;
            if (!receapp06MboSet.isEmpty()) {
                for (int i = 0; (receapp06Mbo = receapp06MboSet.getMbo(i)) != null; i++) {
                    receapp06Mbo.setModified(true);
                }
            }
            MboSetRemote receapp07MboSet = mbo.getMboSet("RECEAPP07");
            MboRemote receapp07Mbo;
            if (!receapp07MboSet.isEmpty()) {
                for (int i = 0; (receapp07Mbo = receapp07MboSet.getMbo(i)) != null; i++) {
                    receapp07Mbo.setModified(true);
                }
            }
        }
    }

    @Override
    public void changeStatus(String status, Date date, String memo, long accessModifier) {

    }

    @Override
    public void init() throws MXException, RemoteException {

    }

    @Override
    public void initFieldFlagsOnMbo(String s) {

    }

    @Override
    public void add() throws MXException, RemoteException {

    }

    @Override
    public void delete(String s) throws MXException, RemoteException {

    }

    @Override
    public void undelete(String s) {

    }
}
