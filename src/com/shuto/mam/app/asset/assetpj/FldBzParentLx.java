package com.shuto.mam.app.asset.assetpj;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldBzParentLx extends MAXTableDomain
{
  public FldBzParentLx(MboValue paramMboValue)
    throws MXException
  {
    super(paramMboValue);

    setRelationship("ASSETBZFL", "1=1");

    String str = getMboValue().getName();
    String[] arrayOfString1 = { str };

    String[] arrayOfString2 = { "assetbzflnum" };

    setLookupKeyMapInOrder(arrayOfString1, arrayOfString2);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    setListCriteria("siteid=:siteid and fltype=:fltype and (fljb+1)=:fljb");
    setListOrderBy("fljb");
    return super.getList();
  }
}