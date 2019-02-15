package com.shuto.mam.app.oplog.sbydsqd;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.oplog.sbydsqd.SbydSet 河北分公司（曹妃甸）
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月5日 下午3:18:06
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class SbydSet extends MboSet
  implements SbydSetRemote
{
  public SbydSet(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet arg0)
    throws MXException, RemoteException
  {
    return new Sbyd(arg0);
  }
}