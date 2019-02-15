/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.workflow.pmtask;

import com.shuto.mam.app.pm.CustPm;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PmtaskScbh implements ActionCustomClass {

    @Override
    public void applyCustomAction(MboRemote mainmbo, Object[] arg1)
            throws MXException, RemoteException {

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        String year = yearFormat.format(new Date());
        String month = monthFormat.format(new Date());
        mainmbo.setValue("BGNUM", ((CustPm) mainmbo).getNum(mainmbo, year, month));
    }

}
