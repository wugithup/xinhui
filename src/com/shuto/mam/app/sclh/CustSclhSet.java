package com.shuto.mam.app.sclh;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-11-08 16:49
 * @desc
 * @class com.shuto.mam.app.sclh.CustSclhSet
 * @Copyright: 2018 Shuto版权所有
 **/

public class CustSclhSet extends MboSet implements MboSetRemote {

    public CustSclhSet(MboServerInterface ms) throws RemoteException {

        super(ms);
    }

    @Override
    protected Mbo getMboInstance(MboSet mboSet) throws MXException, RemoteException {

        return new CustSclh(mboSet);
    }
}
