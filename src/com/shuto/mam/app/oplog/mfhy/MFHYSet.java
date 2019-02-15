package com.shuto.mam.app.oplog.mfhy;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 煤粉细度化验报告
 com.shuto.mam.app.oplog.mfhy.MFHYSet 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午3:55:14
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class MFHYSet extends MboSet
  implements MFHYSetRemote
{
  public MFHYSet(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet arg0)
    throws MXException, RemoteException
  {
    return new MFHY(arg0);
  }
}