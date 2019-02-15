package com.shuto.mam.app.sbpj;
/**  
com.shuto.mam.app.sbpj.SbpjSet 华南
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年6月21日 下午3:07:25
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class SbpjSet extends MboSet
  implements SbpjSetRemote
{
  public SbpjSet(MboServerInterface arg0)
    throws RemoteException
  {
    super(arg0);
  }

  protected Mbo getMboInstance(MboSet arg0) throws MXException, RemoteException
  {
    Mbo mbo = new Sbpj(arg0);
    return mbo;
  }
}