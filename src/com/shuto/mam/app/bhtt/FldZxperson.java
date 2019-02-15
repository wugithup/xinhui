package com.shuto.mam.app.bhtt;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月21日
 * @since:阜阳台账
 */
public class FldZxperson extends MAXTableDomain
{
  public FldZxperson(MboValue paramMboValue)
    throws MXException, RemoteException
  {
    super(paramMboValue);
    setRelationship("person", "");
    String str = getMboValue().getAttributeName();
    String[] arrayOfString1 = { "personid" };
    String[] arrayOfString2 = { str };
    setLookupKeyMapInOrder(arrayOfString2, arrayOfString1);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    setListCriteria(" personid  in (select  RESPPARTYGROUP from PERSONGROUPTEAM where PERSONGROUP='ZYCH_RK') ");

    return super.getList();
  }
}