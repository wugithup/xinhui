package com.shuto.mam.app.operation.oplogassetinfo;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldBackupStatus extends MboValueAdapter
{
  public FldBackupStatus(MboValue mbv)
  {
    super(mbv);
  }

  public void action()
    throws MXException, RemoteException
  {
    super.action();
    MboRemote mbo = getMboValue().getMbo();

    if (getMboValue("BACKUP").getBoolean()) {
      mbo.setValue("OVERHAUL", "0");
      mbo.setValue("RUN", "0");
    }
  }
}