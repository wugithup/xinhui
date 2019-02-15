package com.shuto.mam.app.operation.oplogcfg;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class OpLogCfg extends Mbo
  implements OpLogCfgRemote
{
  public OpLogCfg(MboSet mboset)
    throws RemoteException
  {
    super(mboset);
  }

  public void add() throws MXException, RemoteException {
    super.add();
  }
}