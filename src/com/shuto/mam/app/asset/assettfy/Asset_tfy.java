package com.shuto.mam.app.asset.assettfy;
/**  
com.shuto.mam.app.asset.assettfy.Asset_tfy 设备停复役
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月15日 下午4:56:39
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboSet;

public class Asset_tfy extends Mbo
  implements Asset_tfyRemote
{
  public Asset_tfy(MboSet paramMboSet)
    throws RemoteException
  {
    super(paramMboSet);
  }
}
	