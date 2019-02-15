/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.pm;

import com.shuto.mam.app.MockApp;
import com.shuto.mam.app.MockAppFactory;
import com.shuto.mam.app.util.AutoNumUtil;
import psdi.app.common.AncMbo;
import psdi.app.pm.PM;
import psdi.app.pm.PMRemote;
import psdi.mbo.LinkedMboRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author SumMer
 * @version V1.0
 * @Title: CustPm
 * @Package com.shuto.mam.app.pm
 * @Description:
 * @date 2018-12-19 019 11:23
 */
public class CustPm extends PM implements PMRemote, LinkedMboRemote, AncMbo {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    MockApp mockApp;

    public CustPm(MboSet ms) throws MXException, RemoteException {

        super(ms);
        mockApp = MockAppFactory.getNewAppInstance(this);
    }

    @Override
    public void add() throws MXException, RemoteException {

        super.add();
        if (mockApp != null) {
            mockApp.add();
        }
    }

    public String getNum(MboRemote mbo, String year, String month) {

        String bgnum = null;
        try {
            bgnum = mbo.getString("BGNUM");
            if ("".equalsIgnoreCase(bgnum) || bgnum == null) {
                int num = AutoNumUtil.getNextNumBer(mbo, "PMTASK", year, month, "", "", "BGNUM");
                bgnum = "SCWH" + new SimpleDateFormat("yyMM").format(new Date()) +
                        new DecimalFormat("000").format(num);
            }
        } catch (MXException | RemoteException e) {
            MX_LOGGER.error(e);
        }
        return bgnum;
    }
}
