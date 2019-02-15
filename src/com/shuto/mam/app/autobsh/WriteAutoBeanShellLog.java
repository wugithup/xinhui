/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.autobsh;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;

public class WriteAutoBeanShellLog {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    private MboRemote mbo = null;

    private MboSetRemote mboSet = null;

    public WriteAutoBeanShellLog(MboRemote mbo) {
        // TODO Auto-generated constructor stub
        try {
            this.mbo = mbo;
            this.mboSet = mbo.getThisMboSet();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public WriteAutoBeanShellLog(MboSetRemote mboSet) {
        // TODO Auto-generated constructor stub
        this.mboSet = mboSet;
    }

    public void error(String message) {
        // TODO Auto-generated method stub
        if (mboSet == null) {
            return;
        }
        try {
            StringBuffer arg = new StringBuffer("人员ID=").append(mboSet.getUserInfo().getPersonId())
                                                        .append(",人员名称=")
                                                        .append(mboSet.getUserInfo()
                                                                      .getDisplayName())
                                                        .append(",表名=").append(mboSet.getName());
            if (mbo != null) {
                arg.append(",记录唯一ID=").append(mbo.getUniqueIDValue());
            }
            arg.append("\n");
            MboSetRemote logSet =
                    MXServer.getMXServer().getMboSet("BeanSHellLogs", mboSet.getUserInfo());
            logSet.setWhere("1=2");
            logSet.reset();
            MboRemote log = logSet.add();
            log.setValue("LOG", arg.append(message).toString());
            log.setValue("DATETIME", MXServer.getMXServer().getDate());
            logSet.save();
            logSet.close();
        } catch (RemoteException e) {
            MX_LOGGER.error(e);
        } catch (MXException e) {
            MX_LOGGER.error(e);
        }
    }

}
