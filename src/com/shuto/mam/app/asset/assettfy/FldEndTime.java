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

public class FldEndTime extends MboValueAdapter {

    private static MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    public FldEndTime(MboValue mbv) {

        super(mbv);
    }

    @Override
    public void action() throws MXException, RemoteException {

        String thisAtt = getMboValue().getName();
        MboRemote mainRemote = this.getMboValue().getMbo();
        String endtime = mainRemote.getString(thisAtt);
        if ("JHJXENDDATE".equalsIgnoreCase(thisAtt)) {
            String starttime = mainRemote.getString("JHJXSTARTDATE");
            compare(endtime, starttime);
        } else if ("REAL_STOPDATE".equalsIgnoreCase(thisAtt)) {
            String starttime = mainRemote.getString("REAL_STARTDATE");
            compare(endtime, starttime);
        } else if ("TJSJ_STOPDATE".equalsIgnoreCase(thisAtt)) {
            String starttime = mainRemote.getString("TJSJ_STARTDATE");
            compare(endtime, starttime);
        } else if ("DDJPZ_STOPDATE".equalsIgnoreCase(thisAtt)) {
            String starttime = mainRemote.getString("DDJPZ_STARTDATE");
            compare(endtime, starttime);
        } else if ("ZZPZSTOPTIME".equalsIgnoreCase(thisAtt)) {
            String starttime = mainRemote.getString("ZZPZSTARTTIME");
            compare(endtime, starttime);
        }
        super.action();
    }

    public void compare(String endtime, String starttime) throws MXApplicationException {

        if (!starttime.isEmpty()) {
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
