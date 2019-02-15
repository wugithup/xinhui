package com.shuto.mam.app.stpi.st_pi_taskcfg;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldCycleUnit extends MboValueAdapter{
	public FldCycleUnit(MboValue mbv)
	  {
	    super(mbv);
	  }
	  public void action() throws MXException, RemoteException {
	    super.action();
	    MboRemote mbo = getMboValue().getMbo();
	    String type = mbo.getString("type");
	    if("点检".equals(type)){
	    	if (!"天".equals(getMboValue().getString())) {
	  	      mbo.setValueNull("CYCLE", 11L);
	  	      mbo.setFieldFlag("CYCLE", 128L, false);
	  	      mbo.setFieldFlag("CYCLE", 7L, true);
	  	    } else {
	  	      mbo.setValue("CYCLE", 1, 11L);
	  	      mbo.setFieldFlag("CYCLE", 7L, false);
	  	      mbo.setFieldFlag("CYCLE", 128L, true);
	  	    }
	    }
	  }
}
