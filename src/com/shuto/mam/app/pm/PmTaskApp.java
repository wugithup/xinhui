/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.pm;

import com.shuto.mam.app.MockApp;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @author SumMer
 * @version V1.0
 * @Title: PmTaskApp
 * @Package com.shuto.mam.app.pm
 * @Description:
 * @date 2018-12-19 019 13:29
 */
public class PmTaskApp extends MockApp {

    private MboRemote mbo;

    public PmTaskApp(MboRemote newMbo) {

        super();
        this.mbo = newMbo;
    }

    @Override
    public void save() throws RemoteException, MXException {

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

        mbo.setValue("WORKTYPE", "预维护任务");
        mbo.setValue("STATUS", "活动");
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void undelete(String s) {

    }
}
