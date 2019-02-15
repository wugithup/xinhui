package com.shuto.mam.webclient.beans.rcexpense;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RCExpenseAppBean extends AppBean {

	@Override
	public int SAVE() throws MXException, RemoteException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		Date startDate = mainMbo.getDate("STARTTIME");
		Date endDate = mainMbo.getDate("ENDTIME");
		DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		String sDateFormat = sdf.format(startDate);
		String eDateFormat = sdf.format(endDate);
		if(startDate.after(endDate)){
			throw new MXApplicationException("RCEXPENSE","DATECOMPARE",new String[]{sDateFormat,eDateFormat});
		}

		return super.SAVE();
	}

	@Override
	protected void setCurrentRecordData(MboRemote mbo) throws MXException,
			RemoteException {
		String status = mbo.getString("status");
		if("已关闭".equals(status)){
			mbo.setFlag(MboConstants.READONLY, true);
		}
		super.setCurrentRecordData(mbo);
	}

}
