/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.asset.assettfy;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FldStartTime extends MboValueAdapter {

    private static MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    public FldStartTime(MboValue mbv) {

        super(mbv);
    }

    @Override
    public void action() throws MXException, RemoteException {

        String thisAtt = getMboValue().getName();
        MboRemote mainRemote = this.getMboValue().getMbo();
        String starttime = mainRemote.getString(thisAtt);

        if ("JHJXSTARTDATE".equalsIgnoreCase(thisAtt)) {
            String endtime = mainRemote.getString("JHJXENDDATE");
            compare(starttime, endtime);
        } else if ("REAL_STARTDATE".equalsIgnoreCase(thisAtt)) {
            String endtime = mainRemote.getString("REAL_STOPDATE");
            compare(starttime, endtime);
        } else if ("TJSJ_STARTDATE".equalsIgnoreCase(thisAtt)) {
            String endtime = mainRemote.getString("TJSJ_STOPDATE");
            compare(starttime, endtime);
        } else if ("DDJPZ_STARTDATE".equalsIgnoreCase(thisAtt)) {
            String endtime = mainRemote.getString("DDJPZ_STOPDATE");
            compare(starttime, endtime);
        } else if ("ZZPZSTARTTIME".equalsIgnoreCase(thisAtt)) {
            String endtime = mainRemote.getString("ZZPZSTOPTIME");
            compare(starttime, endtime);
        }

        super.action();
    }

    public void compare(String starttime, String endtime) throws MXApplicationException {

        if (!endtime.isEmpty()) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = df.parse(starttime);
                Date date2 = df.parse(endtime);
                if (date1.getTime() > date2.getTime()) {
                    throw new MXApplicationException("assettfy", "comparetime");
                }
            } catch (ParseException e) {
                MX_LOGGER.error(e);
            }
        }
    }
}
