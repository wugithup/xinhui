/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app;

import psdi.mbo.MboConstants;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @author SumMer
 * @version V1.0
 * @Title: MockApp
 * @Package com.shuto.mam.app
 * @Description:
 * @date 2018-08-29 10:30
 */
public abstract class MockApp implements MboConstants {

    private String appName = "";

    public abstract void save() throws RemoteException, MXException;

    public abstract void changeStatus(String status, Date date, String memo, long accessModifier)
            throws MXException, RemoteException;

    public abstract void init() throws MXException, RemoteException;

    public abstract void initFieldFlagsOnMbo(String s) throws MXException;

    public abstract void add() throws MXException, RemoteException;

    public abstract void delete(String s) throws MXException, RemoteException;

    public abstract void undelete(String s) throws MXException;

    public String getAppName() {

        return this.appName;
    }

    public void setAppName(String appName) {

        this.appName = appName;
    }
}


