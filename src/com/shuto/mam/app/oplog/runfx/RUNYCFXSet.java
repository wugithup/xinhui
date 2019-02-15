package com.shuto.mam.app.oplog.runfx;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 运行分析
 com.shuto.mam.app.oplog.runfx.RUNYCFXSet 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:25:21
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class RUNYCFXSet extends MboSet
  implements RUNYCFXSetRemote
{
  public RUNYCFXSet(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet arg0)
    throws MXException, RemoteException
  {
    return new RUNYCFX(arg0);
  }
}