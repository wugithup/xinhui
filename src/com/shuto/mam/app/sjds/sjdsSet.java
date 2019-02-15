package com.shuto.mam.app.sjds;
/**  
com.shuto.mam.app.sjds.sjdsSet 河北
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月23日 上午11:53:14
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

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
