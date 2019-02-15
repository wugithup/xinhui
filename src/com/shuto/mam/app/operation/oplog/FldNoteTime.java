/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.operation.oplog;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FldNoteTime extends MboValueAdapter {
	public FldNoteTime(MboValue mbovalue) {
		super(mbovalue);
	}

    @Override
    public void action() throws RemoteException, MXException {
		super.action();
        String notetime = getMboValue().getMbo().getString("NOTETIME");
		Date enterdate = getMboValue().getMbo().getDate("WRITEDATE");
		boolean falg = notetime.matches("[0-9]{4}");
		if (falg) {
			String hour1 = notetime.substring(0, 2);
			int hour2 = Integer.parseInt(hour1);
			String second = notetime.substring(2, 4);
			int second2 = Integer.parseInt(second);
			if ((hour2 > 24) || (second2 > 60)) {
				throw new MXApplicationException("", "输入时间格式错误，请重新输入！");
			}
			if ((hour2 <= 24) && (hour2 >= 0) && (second2 <= 60) && (second2 >= 0)) {
                if (Integer.parseInt(notetime.substring(0, 2)) >= 2) {
                    getMboValue().getMbo().setValue("NOTETIME", hour1 + ":" + second, 11L);
					getMboValue().getMbo().setValue("HAPPENDATE", getDate(enterdate, hour2, second2), 11L);
				} else {
                    getMboValue().getMbo().setValue("NOTETIME", hour1 + ":" + second, 11L);
					getMboValue().getMbo().setValue("HAPPENDATE", getDate(enterdate, hour2, second2), 11L);
				}
			}
		}
	}

    public String getDate(Date date, int hours, int minute) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(6, date.getYear());
		cal.set(5, date.getMonth());
		cal.set(5, date.getDate());
		cal.set(11, hours);
		cal.set(12, minute);
		cal.set(13, 0);
		Date time = cal.getTime();
        return df.format(time);
	}
}