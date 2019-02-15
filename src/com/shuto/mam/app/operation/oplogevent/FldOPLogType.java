package com.shuto.mam.app.operation.oplogevent;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.operation.oplogevent.FldOPLogType 磴口项目
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月11日 下午2:21:14
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class FldOPLogType extends MAXTableDomain
{
  public FldOPLogType(MboValue mbovalue)
  {
    super(mbovalue);
    setRelationship("opconfig", "");
    String[] strFrom = { "opconfignum" };
    String[] strTo = { "oplogtype" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList()
    throws MXException, RemoteException
  {
    setListCriteria("configtype='OPLOG'");
    return super.getList();
  }
}