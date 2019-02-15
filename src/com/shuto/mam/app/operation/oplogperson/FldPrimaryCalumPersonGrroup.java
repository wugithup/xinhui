package com.shuto.mam.app.operation.oplogperson;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPrimaryCalumPersonGrroup extends MAXTableDomain
{
  public FldPrimaryCalumPersonGrroup(MboValue mbv)
    throws MXException, RemoteException
  {
    super(mbv);

    setRelationship("calendar", "calnum=:primarycalnum");
    setLookupKeyMapInOrder(new String[] { "primarycalnum" }, new String[] { "calnum" });
  }

  public MboSetRemote getList()
    throws MXException, RemoteException
  {
    return super.getList();
  }
}