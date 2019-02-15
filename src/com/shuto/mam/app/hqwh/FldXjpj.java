package com.shuto.mam.app.hqwh;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;


public class FldXjpj extends MboValueAdapter {
	
	public FldXjpj(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.action();
		MboRemote mbo = getMboValue().getMbo();
		String xjpj = mbo.getString("XJPJ");
		if("1星".equals(xjpj)||"2星".equals(xjpj)||"3星".equals(xjpj)){
			mbo.setFieldFlag("PJSM", 128L, true);
		}else{
			mbo.setFieldFlag("PJSM", 128L, false);
		}
	}

}
