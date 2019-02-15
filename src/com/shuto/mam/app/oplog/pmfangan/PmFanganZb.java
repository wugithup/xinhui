package com.shuto.mam.app.oplog.pmfangan;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 混配煤方案
 com.shuto.mam.app.oplog.pmfangan.PmFanganZb 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月4日 下午9:30:03
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class PmFanganZb extends Mbo
  implements PmFanganZbRemote
{
  public PmFanganZb(MboSet ms)
    throws RemoteException
  {
    super(ms);
  }

  public void add() throws MXException, RemoteException
  {
    super.add();
    setValue("XH", (int)getThisMboSet().max("XH") + 1, 11L);
  }
}