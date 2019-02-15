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
/**
 * 
 * 类说明  控制计划开始时间不能小于开始时间并大于结束时间
 * @author 马斌
 * @date 2016年12月5日
 */
public class FldBeginTime extends MboValueAdapter {

	public FldBeginTime() {
		super();
	}

	public FldBeginTime(MboValue mbv) {
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
//			String createdate =  mainMbo.getString("CREATEDATE");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			if(!"".equals(end_time)){
				Date dt1 = df.parse(begin_time);
				Date dt2 = df.parse(end_time);
				if (dt1.getTime() > dt2.getTime()) {
					throw new MXApplicationException("PI_TCFG", "ENDTIMEERROR");
				}
			}
//			Date dt3 = df.parse(createdate);
//			if (dt3.getTime() > dt1.getTime()) {
//				throw new MXApplicationException("PI_TCFG", "BEGINTIMEERROR");
//			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
