/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.zsapply;

import com.shuto.mam.app.MockApp;
import com.shuto.mam.app.MockAppFactory;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;

public class CustZsapply extends Mbo implements MboRemote {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    private MockApp mockApp;

    public CustZsapply(MboSet ms) throws RemoteException {

        super(ms);
        mockApp = MockAppFactory.getNewAppInstance(this);
    }

    @Override
    public void save() throws RemoteException, MXException {

        super.save();
        if (mockApp != null) {
            mockApp.save();
        }

    }

    @Override
    public void init() throws MXException {

        super.init();
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
            MboRemote ownerMbo = this.getOwner();
            String status = ownerMbo.getString("STATUS");
            String[] cardAndDriver = new String[]{"DRIVER", "CARCARD"};
            switch (status) {
                case "待综合部处理":
                    this.setFieldFlag(cardAndDriver, REQUIRED, true);
                    break;
                case "待申请人进行用车评价":
                    this.setFieldFlag("FWPJ", REQUIRED, true);
                    break;
                case "待综合部统计":
                    this.setFieldFlag("CYCOST", REQUIRED, true);
                    break;
                case "待车辆管理人派车":
                    this.setFieldFlag(cardAndDriver, REQUIRED, true);
                    break;
                case "待申请人确认":
                    this.setFieldFlag("FWPJ", REQUIRED, true);
                    break;
                default:
            }
        } catch (RemoteException e) {
            MX_LOGGER.error(e);
        }
    }
}