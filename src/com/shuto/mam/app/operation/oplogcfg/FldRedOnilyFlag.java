package com.shuto.mam.app.operation.oplogcfg;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldRedOnilyFlag extends MboValueAdapter
{
  public FldRedOnilyFlag(MboValue mbv)
  {
    super(mbv);
  }

  public void action()
    throws MXException, RemoteException
  {
    super.action();

    MboRemote thisMbo = getMboValue().getMbo();
    int flag = getMboValue().getInt();

    if (flag != 1)
      return;
    thisMbo.setFieldFlag("description", 7L, true);

    thisMbo.setFieldFlag("assetnum", 7L, true);

    thisMbo.setFieldFlag("location", 7L, true);

    thisMbo.setFieldFlag("unit", 7L, true);
  }

  public void init()
    throws MXException, RemoteException
  {
    super.init();

    MboRemote thisMbo = getMboValue().getMbo();
    int flag = getMboValue().getInt();

    if (flag != 1) {
      return;
    }

    thisMbo.setFieldFlag("description", 7L, true);

    thisMbo.setFieldFlag("assetnum", 7L, true);

    thisMbo.setFieldFlag("location", 7L, true);

    thisMbo.setFieldFlag("unit", 7L, true);

    thisMbo.setFieldFlag("ordernum", 7L, true);
  }
}