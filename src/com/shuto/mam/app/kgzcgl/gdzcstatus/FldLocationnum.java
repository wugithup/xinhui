package com.shuto.mam.app.kgzcgl.gdzcstatus;


import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月30日
 * @since:控股台账
 */
public class FldLocationnum extends MAXTableDomain
{
  public FldLocationnum(MboValue paramMboValue)
  {
    super(paramMboValue);
    String str = getMboValue().getName();
    setRelationship("locationlist", "1=1");
    String[] arrayOfString1 = { "locationnum" };
    String[] arrayOfString2 = { str };
    setLookupKeyMapInOrder(arrayOfString2, arrayOfString1);
  }

  public void action() throws MXException, RemoteException {
    super.action();
  }
}