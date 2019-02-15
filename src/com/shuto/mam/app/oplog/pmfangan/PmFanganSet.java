package com.shuto.mam.app.oplog.pmfangan;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 混配煤方案
 com.shuto.mam.app.oplog.pmfangan.PmFanganSet 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月4日 下午8:56:33
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class PmFanganSet extends MboSet
  implements PmFanganSetRemote
{
  public PmFanganSet(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet arg0)
    throws MXException, RemoteException
  {
    return new PmFangan(arg0);
  }
}