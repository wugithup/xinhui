package com.shuto.mam.app.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.operation.oplog.FldNoticLogType 磴口项目
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月5日 下午8:29:30
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class FldNoticLogType extends MAXTableDomain
{
  public FldNoticLogType(MboValue mbovalue)
  {
    super(mbovalue);
    setRelationship("opconfig", "");
    String[] strFrom = { "opconfignum" };
    String[] strTo = { "NoticeLogType" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList()
    throws MXException, RemoteException
  {
    setListCriteria("configtype='OPLOG'");
    return super.getList();
  }
}