package com.shuto.mam.app.stdanger;
/**  
com.shuto.mam.app.stdanger.StDangerSet 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月27日 下午3:49:13
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class StDangerSet extends MboSet
  implements StDangerSetRemote
{
  public StDangerSet(MboServerInterface paramMboServerInterface)
    throws RemoteException
  {
    super(paramMboServerInterface);
  }

  protected Mbo getMboInstance(MboSet paramMboSet) throws MXException, RemoteException
  {
    return new StDanger(paramMboSet);
  }
}
