package com.shuto.mam.app.oplog.tsdlxdan;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 停送电联系单
 com.shuto.mam.app.oplog.tsdlxdan.TsdlxDanSet 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午2:26:57
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class TsdlxDanSet extends MboSet
  implements TsdlxDanSetRemote
{
  public TsdlxDanSet(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet arg0)
    throws MXException, RemoteException
  {
    return new TsdlxDan(arg0);
  }
}