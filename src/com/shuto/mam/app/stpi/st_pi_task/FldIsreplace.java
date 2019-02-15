package com.shuto.mam.app.stpi.st_pi_task;


import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldIsreplace extends MboValueAdapter
{
  public FldIsreplace(MboValue mbv)
  {
    super(mbv);
  }
  public void action() throws MXException, RemoteException {
    super.action();
    MboRemote mbo = getMboValue().getMbo();
    if (mbo.getBoolean("ISREPLACE")) {
      mbo.setFieldFlag("REPLACEPER", 7L, false);
      mbo.setFieldFlag("REPLACEPER", 128L, true);
    } else {
      mbo.setFieldFlag("REPLACEPER", 128L, false);
      mbo.setValueNull("REPLACEPER");
      mbo.setFieldFlag("REPLACEPER", 7L, true);
    }
  }
}