package com.shuto.mam.app.asset.assetpj;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月21日
 * @since:阜阳台账
 */
public class FldLocation extends MAXTableDomain
{
  public FldLocation(MboValue paramMboValue)
    throws MXException, RemoteException
  {
    super(paramMboValue);
    String str = paramMboValue.getName();
    setRelationship("LOCATIONS", "location=:" + str + " and siteid = :siteid  ");

    String[] arrayOfString1 = { "LOCATION" };
    String[] arrayOfString2 = { str };
    setLookupKeyMapInOrder(arrayOfString2, arrayOfString1);
  }

  public void action() throws MXException, RemoteException
  {
    super.action();
    Mbo localMbo = getMboValue().getMbo();
    String str1 = getMboValue().getString();
    MboSetRemote localMboSetRemote = localMbo.getMboSet("#ASSET", "ASSET", " location= '" + str1 + "'");

    if (localMboSetRemote.count() > 0) {
      String str2 = localMboSetRemote.getMbo(0).getString("ASSETNUM");
      if (!str2.isEmpty()) {
        localMbo.setValue("ASSETNUM", str2, 11L);
      }

    }

    localMboSetRemote.close();
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    setListCriteria("type='操作' or type='操作中'");

    return super.getList();
  }
}