package com.shuto.mam.app.operation.oplogcfg;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPrimaryCalnum extends MAXTableDomain
{
  public FldPrimaryCalnum(MboValue mbv)
    throws MXException, RemoteException
  {
    super(mbv);

    setRelationship("calendar", "calnum=:primarycalnum");
    setLookupKeyMapInOrder(new String[] { "primarycalnum" }, new String[] { "calnum" });
  }

  public void action()
    throws MXException, RemoteException
  {
    super.action();

    MboValue thisValue = getMboValue();

    Mbo thisMbo = thisValue.getMbo();

    String primarycalnum = thisMbo.getString("primarycalnum");

    MboSetRemote PersonGTSetRemote = thisMbo.getMboSet("oplogtype_oplogperson");
    if (!PersonGTSetRemote.isEmpty())
      for (int i = 0; i < PersonGTSetRemote.count(); ++i)
      {
        PersonGTSetRemote.getMbo(i).setValue("primarycalnum", primarycalnum, 11L);
      }
  }

  public MboSetRemote getList()
    throws MXException, RemoteException
  {
    return super.getList();
  }
}