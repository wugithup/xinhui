package com.shuto.mam.app.asset.assettfy;
/**  
com.shuto.mam.app.asset.assettfy.Asset_tfySet 设备停复役
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月15日 下午4:58:01
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class Asset_tfySet extends MboSet
  implements Asset_tfySetRemote
{
  public Asset_tfySet(MboServerInterface paramMboServerInterface)
    throws RemoteException
  {
    super(paramMboServerInterface);
  }

  protected Mbo getMboInstance(MboSet paramMboSet)
    throws MXException, RemoteException
  {
    return new Asset_tfy(paramMboSet);
  }
}
