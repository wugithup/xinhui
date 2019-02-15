package com.shuto.mam.app.operation.oplognopro;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class OplognoproMboSet extends MboSet
  implements OplognoproMboSetRemote
{
  public OplognoproMboSet(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet mboset)
    throws MXException, RemoteException
  {
    return new OplognoproMbo(mboset);
  }
}