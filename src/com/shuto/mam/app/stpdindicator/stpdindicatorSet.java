package com.shuto.mam.app.stpdindicator;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 生产指标录入
 com.shuto.mam.app.stpdindicator.stpdindicatorSet 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:30:11
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class stpdindicatorSet extends MboSet
  implements stpdindicatorSetRemote
{
  public stpdindicatorSet(MboServerInterface arg0)
    throws RemoteException, MXException
  {
    super(arg0);
  }

  public void init() throws MXException, RemoteException
  {
    setOrderBy("tjdate desc");
    super.init();
  }

  protected Mbo getMboInstance(MboSet arg0)
    throws MXException, RemoteException
  {
    return new stpdindicator(arg0);
  }
}