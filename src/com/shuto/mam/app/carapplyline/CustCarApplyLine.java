/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.carapplyline;

import com.shuto.mam.app.MockApp;
import com.shuto.mam.app.MockAppFactory;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;

/**
 * @author SumMer
 * @version V1.0
 * @Title: CustCarapplyLine
 * @Package com.shuto.mam.app
 * @Description:
 * @date 2018-08-29 18:44
 */
public class CustCarApplyLine extends Mbo implements MboRemote {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    private MockApp mockApp;

    public CustCarApplyLine(MboSet ms) throws RemoteException {

        super(ms);
        mockApp = MockAppFactory.getNewAppInstance(this);
    }

    /**
     * 控制界面只读必填信息
     *
     * @param attrName
     * @throws MXException
     */
    @Override
    public void initFieldFlagsOnMbo(String attrName) throws MXException {

        super.initFieldFlagsOnMbo(attrName);
        try {
            String status = this.getOwner().getString("STATUS");
            if ("待综合部派车".equalsIgnoreCase(status)) {
                this.setFieldFlag("DRIVER", REQUIRED, true);
                this.setFieldFlag("CARCARD", REQUIRED, true);
            } else if ("待申请人确认".equalsIgnoreCase(status)) {
                this.setFieldFlag("FWPJ", REQUIRED, true);
            }
        } catch (RemoteException e) {
            MX_LOGGER.error(e);
        }
    }
}
