package com.shuto.mam.app.coaloilcount;
/**  
com.shuto.mam.app.coaloilcount.coaloilcountSet 河北
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月22日 下午8:28:21
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class coaloilcountSet extends MboSet
  implements coaloilcountSetRemote
{
  public coaloilcountSet(MboServerInterface arg0)
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
    return new coaloilcount(arg0);
  }
}