package com.shuto.mam.app.operation.oplogperson;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldOpLogType extends MAXTableDomain
{
  public FldOpLogType(MboValue mbovalue)
  {
    super(mbovalue);
    setRelationship("oplogcfg", "");
    String[] strFrom = { "oplogtype" };
    String[] strTo = { "oplogtype" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList()
    throws MXException, RemoteException
  {
    setListCriteria("cfgtype='OPLOG'");
    return super.getList();
  }
}