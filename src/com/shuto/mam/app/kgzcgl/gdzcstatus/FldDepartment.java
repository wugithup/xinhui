package com.shuto.mam.app.kgzcgl.gdzcstatus;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class FldDepartment extends MAXTableDomain
{
  public FldDepartment(MboValue paramMboValue)
  {
    super(paramMboValue);

    String str = getMboValue().getName();
    setRelationship("department", "1=1");
    String[] arrayOfString1 = { "sid" };
    String[] arrayOfString2 = { str };
    setLookupKeyMapInOrder(arrayOfString2, arrayOfString1);
  }

  public MboSetRemote getList() throws MXException, RemoteException
  {
    String str = getMboValue().getMbo().getProfile().getDefaultSite();
    setListCriteria(" siteid='" + str + "' ");
    return super.getList();
  }
}