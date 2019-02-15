package com.shuto.mam.app.oplog.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.oplog.oplog.FldLocaTion 河北分公司（曹妃甸）
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月5日 下午3:17:30
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class FldLocaTion extends MAXTableDomain
{
  public FldLocaTion(MboValue mbv)
    throws MXException, RemoteException
  {
    super(mbv);
    String where = "STATUS='操作中' and siteid=:siteid";
    setRelationship("LOCATIONS", where);
    setListCriteria(where);
    setLookupKeyMapInOrder(new String[] { "LOCATION" }, new String[] { "LOCATION", "DESCRIPTION" });
  }
}