package com.shuto.mam.app.operation.oplogperson;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldZhibie extends MAXTableDomain
{
  public FldZhibie(MboValue mbv)
  {
    super(mbv);
    setRelationship("shift", "");
    String[] strFrom = { "shiftnum" };
    String[] strTo = { getMboValue().getAttributeName() };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    setListCriteria("shifttype = '班值'");
    return super.getList();
  }
}