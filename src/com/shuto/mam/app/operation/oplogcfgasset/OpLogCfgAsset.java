package com.shuto.mam.app.operation.oplogcfgasset;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class OpLogCfgAsset extends Mbo
  implements OpLogCfgAssetRemote
{
  public OpLogCfgAsset(MboSet mboset)
    throws RemoteException
  {
    super(mboset);
  }

  public void add() throws MXException, RemoteException {
    MboRemote parent = getOwner();
    if (parent != null) {
      super.add();
      setValue("oplogcfgid", parent.getString("oplogcfgid"), 11L);
    }
  }
}