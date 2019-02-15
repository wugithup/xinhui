package com.shuto.mam.app.oplog.pmfangan;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 混配煤方案
 com.shuto.mam.app.oplog.pmfangan.PmFanganZbSet 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月4日 下午9:29:41
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class PmFanganZbSet extends MboSet
  implements PmFanganZbSetRemote
{
  public PmFanganZbSet(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet arg0)
    throws MXException, RemoteException
  {
    return new PmFanganZb(arg0);
  }
}