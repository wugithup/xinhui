package com.shuto.mam.app.oplog.pmfangan;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 库存煤质维护
 com.shuto.mam.app.oplog.pmfangan.PmFanganZb2Set 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月4日 下午5:24:58
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class PmFanganZb2Set extends MboSet
  implements PmFanganZb2SetRemote
{
  public PmFanganZb2Set(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet arg0)
    throws MXException, RemoteException
  {
    return new PmFanganZb2(arg0);
  }
}