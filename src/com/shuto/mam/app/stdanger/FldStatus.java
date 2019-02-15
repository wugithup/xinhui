/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.stdanger;

import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map.Entry;

public class FldStatus extends MboValueAdapter {

    public FldStatus(MboValue paramMboValue) {

        super(paramMboValue);
    }

    @Override
    public void action() throws MXException, RemoteException {

        super.action();
        Mbo localMbo = getMboValue().getMbo();
        String str = localMbo.getString("STATUS");
        localMbo.getThisMboSet().getApp();

        if ("ST_DANGER".equals(localMbo.getThisMboSet().getApp())) {
            if ("新建".equals(str)) {
                setAttributesReadOnly("ST_DANGER",
                        new String[]{"DESCRIPTION", "DANGERDESC", "DANGERDEPT", "PROFESSIONAL",
                                "ASSESSMENTYDJ", "CONSEQUENCE"});
            }

            localMbo.setFieldFlag("DESCRIPTION", 7L, false);
            localMbo.setFieldFlag("DANGERDESC", 7L, false);
            localMbo.setFieldFlag("DANGERDEPT", 7L, false);
            localMbo.setFieldFlag("PROFESSIONAL", 7L, false);
            localMbo.setFieldFlag("CONSEQUENCE", 7L, false);
            localMbo.setFieldFlag("ASSESSMENTYDJ", 7L, false);

            localMbo.setFieldFlag("DESCRIPTION", 128L, true);
            localMbo.setFieldFlag("DANGERDESC", 128L, true);
            localMbo.setFieldFlag("DANGERDEPT", 128L, true);
            localMbo.setFieldFlag("PROFESSIONAL", 128L, true);
            localMbo.setFieldFlag("CONSEQUENCE", 128L, true);
            localMbo.setFieldFlag("ASSESSMENTYDJ", 128L, true);
        }
    }

    private void setAttributesReadOnly(String paramString, String[] paramArrayOfString)
            throws RemoteException, MXException {

        HashMap<String, String> localHashMap = new HashMap<>();
        Mbo localMbo = getMboValue().getMbo();
        MboSetRemote localMboSetRemote = localMbo.getMboSet("$maxattribute", "maxattribute",
                "objectname='" + paramString + "'");

        if (!localMboSetRemote.isEmpty()) {
            for (int i = 0; i < localMboSetRemote.count(); i++) {
                localHashMap
                        .put(localMboSetRemote.getMbo(i).getString("attributename").toUpperCase(),
                                localMboSetRemote.getMbo(i).getString("attributename")
                                                 .toUpperCase());
            }
        }
        Object localObject;
        for (String aParamArrayOfString : paramArrayOfString) {
            localObject = aParamArrayOfString;
            if (localHashMap.containsKey(((String) localObject).toUpperCase())) {
                localHashMap.remove(localObject);
            }
        }

        for (Entry<String, String> stringStringEntry : localHashMap.entrySet()) {
            localObject = stringStringEntry;
            String str = (String) ((Entry) localObject).getKey();
            localMbo.setFieldFlag(str, 7L, true);
        }

        localMboSetRemote.close();
    }
}
