package com.shuto.mam.app.sclh;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;

/**
 * @author zhaowei
 * @email zhaowei@shuto.cn
 * @create 2018-11-08 11:15
 * @desc
 * @class com.shuto.mam.app.sclh.CronCreateSclh
 * @Copyright: 2018 Shuto版权所有
 **/

public class CronCreateSclh extends SimpleCronTask {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    MXServer mxserver;

    UserInfo userInfo;

    @Override
    public void init() throws MXException {

        try {
            super.init();
            this.mxserver = MXServer.getMXServer();
            this.userInfo = getRunasUserInfo();
        } catch (Exception e) {
            MX_LOGGER.error(e);
        }
    }

    /**
     * 触发定时任务
     */
    @Override
    public void cronAction() {

        try {
            creatCron();
        } catch (Exception e) {
            MX_LOGGER.error(e);
        }
    }

    private void creatCron() {

        try {
            MboSetRemote opprlogSet = mxserver.getMboSet("SCDDHY", userInfo);
            MboRemote opprlogMbo = opprlogSet.getMbo(0);
            CustSclh custSclh = (CustSclh) opprlogMbo;
            custSclh.createSclh();
        } catch (MXException | RemoteException e) {
            MX_LOGGER.error(e);
        }
    }

}
