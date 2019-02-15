/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.carapply;

import com.shuto.mam.app.MockApp;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @author SumMer
 * @version V1.0
 * @Title: CarApplyApp
 * @Package com.shuto.mam.app.carapply
 * @Description:
 * @date 2018-08-30 18:24
 */
public class CarApplyApp extends MockApp {

    private MboRemote mbo;

    public CarApplyApp(MboRemote newMbo) {

        super();
        this.mbo = newMbo;
    }

    @Override
    public void save() throws RemoteException, MXException {

        if ("CARAPPLY".equalsIgnoreCase(this.getAppName())) {
            //获取子表的MBO对象
            MboSetRemote carApplyLineMboSet = mbo.getMboSet("CARAPPLYLINE");
            for (int i = 0; carApplyLineMboSet.getMbo(i) != null; i++) {
                //设置为modified为true表示有必填信息未填写
                carApplyLineMboSet.getMbo(i).setModified(true);
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
