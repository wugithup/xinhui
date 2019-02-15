package com.shuto.mam.app.stpi.st_pi_task_item;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldIsnormal extends MboValueAdapter
{
	  public FldIsnormal(MboValue mbv)
	    throws MXException, RemoteException
	  {
	    super(mbv);
	  }

	  public void action() throws MXException, RemoteException
	  {
	    MboRemote mbo = getMboValue().getMbo();
	    boolean isnormal = mbo.getBoolean("ISNORMAL");
	    String PERSON = mbo.getUserInfo().getPersonId();

	    if (isnormal) {
	      mbo.setFieldFlag("NOTE", 128L, true);
	      mbo.setValue("PI_AMEND_USER", PERSON, 11L);
	      mbo.setValue("PI_AMEND_TIME", new Date(), 11L);
	    } else {
	      mbo.setFieldFlag("NOTE", 128L, false);
	      mbo.setValue("PI_AMEND_USER", "", 11L);
	      mbo.setValue("PI_AMEND_TIME", "", 11L);
	      mbo.setValue("NOTE", "", 11L);
	    }

	    super.action();
	  }
	}