package com.shuto.mam.app.operation.oplogassetinfo;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldJdperson extends MAXTableDomain
{
  public FldJdperson(MboValue mbv)
    throws RemoteException, MXException
  {
    super(mbv);

    String thisAtt = getMboValue().getName();

    setRelationship("oplogperson", "1=1");

    String[] strTo = { thisAtt };

    String[] strFrom = { "personid" };

    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList()
    throws MXException, RemoteException
  {
    MboRemote oplogassetinfombo = getMboValue().getMbo();
    String oplogtype = oplogassetinfombo.getString("OPLOGTYPE");
    String siteid = oplogassetinfombo.getString("siteid");

    setListCriteria("oplogtype = '" + oplogtype + "' and siteid = '" + siteid + "'");
    System.out.println("------------------------------------com.shuto.mam.app.operation.oplogassetinfo.FldJdperson-----------------------------------------------------------------------");

    return super.getList();
  }
}