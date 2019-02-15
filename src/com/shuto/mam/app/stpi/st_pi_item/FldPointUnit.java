package com.shuto.mam.app.stpi.st_pi_item;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPointUnit extends MAXTableDomain
{
  public FldPointUnit(MboValue mbv)
  {
    super(mbv);
    setRelationship("st_pi_item_unit", "");
    setLookupKeyMapInOrder(new String[] { "POINT_UNIT" }, new String[] { "unit" });
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    return super.getList();
  }
  public void action() throws MXException, RemoteException {
    super.action();
  }
}