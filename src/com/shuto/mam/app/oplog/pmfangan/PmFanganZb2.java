package com.shuto.mam.app.oplog.pmfangan;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
/**
 * 库存煤质维护
 com.shuto.mam.app.oplog.pmfangan.PmFanganZb2 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月4日 下午5:36:56
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class PmFanganZb2 extends Mbo
  implements PmFanganZb2Remote
{
  public PmFanganZb2(MboSet ms)
    throws RemoteException
  {
    super(ms);
  }
}