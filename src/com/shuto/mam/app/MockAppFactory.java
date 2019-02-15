/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app;

import com.shuto.mam.app.pm.PmTaskApp;
import com.shuto.mam.app.util.AppUtil;
import psdi.mbo.MboRemote;

/**
 * @author SumMer
 * @version V1.0
 * @Title: MockAppFactory
 * @Package com.shuto.mam.app
 * @Description:
 * @date 2018-08-29 10:44
 */
public class MockAppFactory {

    public static MockApp getNewAppInstance(MboRemote mbo) {

        MockApp mockApp = new FinalApp(mbo);
        if (mbo != null) {
            String appName = AppUtil.getAppName(mbo);
            if (appName != null) {
                if ("PMTASK".equalsIgnoreCase(appName)) {
                    mockApp = new PmTaskApp(mbo);
                }
                mockApp.setAppName(appName);
            }
        }
        return mockApp;
    }
}
