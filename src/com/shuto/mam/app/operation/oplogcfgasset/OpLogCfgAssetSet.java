package com.shuto.mam.app.operation.oplogcfgasset;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class OpLogCfgAssetSet extends MboSet
  implements OpLogCfgAssetSetRemote
{
  public OpLogCfgAssetSet(MboServerInterface mboserverinterface)
    throws MXException, RemoteException
  {
    super(mboserverinterface);
  }

  protected Mbo getMboInstance(MboSet mboset)
    throws MXException, RemoteException
  {
    return new OpLogCfgAsset(mboset);
  }
}