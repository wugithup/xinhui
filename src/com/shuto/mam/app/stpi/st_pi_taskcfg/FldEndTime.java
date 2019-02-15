package com.shuto.mam.app.stpi.st_pi_taskcfg;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class FldEndTime extends MboValueAdapter {

	public FldEndTime() {
		super();
	}

	public FldEndTime(MboValue mbv) {
		super(mbv);
	}
	@Override
	public void init() throws MXException, RemoteException {
		super.init();
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		try {
			MboRemote mainMbo = this.getMboValue().getMbo();
			String begin_time =  mainMbo.getString("BEGIN_TIME");
			String end_time =  mainMbo.getString("END_TIME");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			Date dt1 = df.parse(begin_time);
			Date dt2 = df.parse(end_time);
			if (dt1.getTime() > dt2.getTime()) {
				throw new MXApplicationException("PI_TCFG", "ENDTIMEERROR");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
