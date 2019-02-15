/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.asset.assprol;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;

public class Asspro extends Mbo implements AssproRemote {

    private static MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    public Asspro(MboSet mboSet) throws RemoteException {

        super(mboSet);
    }

    @Override
    public void add() throws MXException, RemoteException {

        super.add();
    }

    @Override
    public void init() throws MXException {

        super.init();
        try {
            String status = getString("STATUS");
            String app = getString("APP");
            if ("ASSPROL".equals(app)) {
                if ("实施人接收".equals(status)) {
                    setFieldFlag("SAFETYMEASURE", 7L, false);
                    setFieldFlag("SAFETYMEASURE", 128L, true);
                }
                if ("复位人接收".equals(status)) {
                    setFieldFlag("SAFETYMEASURE2", 7L, false);
                    setFieldFlag("SAFETYMEASURE2", 128L, true);
                }

                if ("批准临时解除".equals(status)) {
                    setFieldFlag("HFPERSON", 7L, false);
                    setFieldFlag("HFPERSON", 128L, true);
                    setFieldFlag("JHHFDATE", 7L, false);
                    setFieldFlag("JHHFDATE", 128L, true);
                    setFieldFlag("REMOVEREASON2", 7L, false);
                    setFieldFlag("REMOVEREASON2", 128L, true);
                    setFieldFlag("REMOVECONTENT2", 7L, false);
                    setFieldFlag("REMOVECONTENT2", 128L, true);
                    setFieldFlag("SAFETYMEASURE2", 7L, false);
                }

                if ("关闭".equals(status)) {
                    setFlag(7L, true);
                }
            }

            if (("已批准".equals(status)) && (!("ASSPROL".equals(app)))) {
                setFlag(7L, true);
            }
        } catch (RemoteException e) {
            MX_LOGGER.error(e);
        }
    }
}