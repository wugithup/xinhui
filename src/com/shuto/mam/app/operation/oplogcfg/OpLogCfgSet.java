package com.shuto.mam.app.operation.oplogcfg;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class OpLogCfgSet extends MboSet
  implements OpLogCfgSetRemote
{
  public OpLogCfgSet(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet mboset)
    throws MXException, RemoteException
  {
    return new OpLogCfg(mboset);
  }
}