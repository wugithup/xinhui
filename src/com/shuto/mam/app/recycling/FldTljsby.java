package com.shuto.mam.app.recycling;
/**  
com.shuto.mam.app.recycling.FldTljsby 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月10日 下午4:35:07
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldTljsby extends MAXTableDomain
{
  public FldTljsby(MboValue mbv)
  {
    super(mbv);
    setRelationship("person", "");
    String[] strFrom = { "displayname" };
    String[] strTo = { "TLJSBY" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException
  {
    setListCriteria(" locationsite = :siteid and status='活动'");
    return super.getList();
  }
}