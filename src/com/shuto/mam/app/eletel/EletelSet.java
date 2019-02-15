package com.shuto.mam.app.eletel;
/**  
com.shuto.mam.app.eletel.EletelSet 江苏
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月24日 下午5:35:53
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class EletelSet extends MboSet
  implements EletelSetRemote
{
  private static final long serialVersionUID = 1L;

  public EletelSet(MboServerInterface paramMboServerInterface)
    throws MXException, RemoteException
  {
    super(paramMboServerInterface);
  }

  protected Mbo getMboInstance(MboSet paramMboSet) throws MXException, RemoteException {
    return new Eletel(paramMboSet);
  }
}
