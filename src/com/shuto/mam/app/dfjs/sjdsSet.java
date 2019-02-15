package com.shuto.mam.app.dfjs;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 电费结算
 com.shuto.mam.app.dfjs.sjdsSet 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:48:17
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class sjdsSet extends MboSet
  implements sjdsSetRemote
{
  public sjdsSet(MboServerInterface arg0)
    throws RemoteException, MXException
  {
    super(arg0);
  }

  public void init()
    throws MXException, RemoteException
  {
    super.init();
  }

  protected Mbo getMboInstance(MboSet arg0)
    throws MXException, RemoteException
  {
    return new sjds(arg0);
  }
}