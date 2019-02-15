package com.shuto.mam.app.bzlog;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @author wuchang
 * @version V1.0
 * @Title: BzLogSet
 * @Package com.shuto.mam.app.bzlog
 * @Description: 班组日志的MboSet
 * @date 2019/1/10 13:57
 */
public class BzLogSet extends MboSet implements BzLogSetRemote{

    public BzLogSet(MboServerInterface ms) throws RemoteException {

        super(ms);
    }

    @Override
    protected Mbo getMboInstance(MboSet mboSet) throws MXException, RemoteException {

        return new BzLog(mboSet);
    }
}
