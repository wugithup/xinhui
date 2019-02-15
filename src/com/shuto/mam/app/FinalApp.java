/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app;

import com.shuto.mam.app.util.AppUtil;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @author SumMer
 * @version V1.0
 * @Title: FinalApp
 * @Package com.shuto.mam.app
 * @Description:
 * @date 2018-08-29 10:48
 */
public class FinalApp extends MockApp {

    private final static MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    private MboRemote mbo;

    public FinalApp(MboRemote newMbo) {

        super();
        mbo = newMbo;

    }

    @Override
    public void save() throws RemoteException, MXException {

        mbo.setModified(true);
        MboSetRemote carApplyLineMboSet = mbo.getMboSet("OPTICKETNUM_OPTICKETLINE");
        for (int i = 0; carApplyLineMboSet.getMbo(i) != null; i++) {
            //设置为modified为true表示有必填信息未填写
            carApplyLineMboSet.getMbo(i).setModified(true);
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

        try {
            String appName = AppUtil.getAppName(mbo);
            String mboName = mbo.getName();
            String status = mbo.getString("STATUS");
            if ("".equalsIgnoreCase(status)) {
                status = mbo.getOwner().getString("STATUS");
            }
            String sql = "APPNAME=:1 AND MBONAME=:2";
            SqlFormat sf = new SqlFormat(sql);
            sf.setObject(1, "ATTRIBUTECONTROL", "APPNAME", appName);
            sf.setObject(2, "ATTRIBUTECONTROL", "MBONAME", mboName);
            MboSetRemote attMsr =
                    mbo.getMboSet("$ATTRIBUTECONTROL", "ATTRIBUTECONTROL", sf.format());
            if (!attMsr.isEmpty()) {
                MboSetRemote attLineMsr = attMsr.getMbo(0).getMboSet("ATTRIBUTECONTROLLINE");
                attLineMsr.setWhere("STATUS='" + status + "'");
                int i = 0;
                while (attLineMsr.getMbo(i) != null) {
                    //获取属性名称
                    String[] rdStr = attLineMsr.getMbo(i).getString("description").split(",");
                    mbo.setFieldFlag(rdStr, MboConstants.REQUIRED, true);
                    i++;
                }
            }
        } catch (RemoteException | MXException e) {
            MX_LOGGER.error(e);
        }
    }

    @Override
    public void add() throws MXException, RemoteException {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void undelete(String s) {

    }
}
