package com.shuto.mam.app.operation.oplogcfgasset;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPointnum extends MAXTableDomain
{
  public FldPointnum(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    setRelationship("measurepoint", "pointnum=:pointnum");
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    return super.getList();
  }
}