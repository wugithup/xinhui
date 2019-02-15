package com.shuto.mam.app.stpi.st_pi_taskcfg;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldCycle_type extends MboValueAdapter{
	public FldCycle_type(MboValue mbv)
	  {
	    super(mbv);
	  }
	  public void action() throws MXException, RemoteException {
	    super.action();
	    MboRemote mbo = getMboValue().getMbo();
	    String cycle_type = mbo.getString("cycle_type");
	    MboSetRemote daycycleSet = mbo.getMboSet("ST_PI_DAYCYCLE");
	    if("时间周期配置".equals(cycle_type)){
	    	 mbo.setValueNull("CYCLE_SHIFT", 11L);
	    	 mbo.setValueNull("ST_PI_DUTYINFONUM", 11L);
	    	 mbo.setFieldFlag("ST_PI_DUTYINFONUM", 128L, false);
	    	 mbo.setFieldFlag("ST_PI_DUTYINFONUM", 7L, true);
	    	 mbo.setFieldFlag("CYCLE_UNIT_XJ", 7L, false);
	  	     mbo.setFieldFlag("CYCLE", 7L, false);
	  	     mbo.setFieldFlag("CYCLE_UNIT_XJ", 128L, true);
	  	     mbo.setFieldFlag("CYCLE", 128L, true);
	  	     
	  	     daycycleSet.deleteAll(11L);
	  	     daycycleSet.setFlag(7L, true);
	    }else{
	    	 mbo.setValueNull("CYCLE_UNIT_XJ", 11L);
	    	 mbo.setValueNull("CYCLE", 11L);
	    	 mbo.setFieldFlag("CYCLE_UNIT_XJ", 128L, false);
	  	     mbo.setFieldFlag("CYCLE", 128L, false);
	    	 mbo.setFieldFlag("CYCLE_UNIT_XJ", 7L, true);
	  	     mbo.setFieldFlag("CYCLE", 7L, true);
	    	 mbo.setFieldFlag("ST_PI_DUTYINFONUM", 7L, false);
	    	 mbo.setFieldFlag("ST_PI_DUTYINFONUM", 128L, true);
	    	 
	    	 daycycleSet.setFlag(7L, false);
	    }
	  }
}
