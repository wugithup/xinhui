package com.shuto.mam.app.oplog.shiguyx;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 事故预想
 com.shuto.mam.app.oplog.shiguyx.ShiGuYXSet 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午4:30:07
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class ShiGuYXSet extends MboSet
  implements ShiGuYXSetRemote
{
  public ShiGuYXSet(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet arg0)
    throws MXException, RemoteException
  {
    return new ShiGuYX(arg0);
  }
}