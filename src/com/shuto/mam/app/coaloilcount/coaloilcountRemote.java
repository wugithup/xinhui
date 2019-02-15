package com.shuto.mam.app.coaloilcount;
/**  
com.shuto.mam.app.coaloilcount.coaloilcountRemote 河北
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月22日 下午8:27:11
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public abstract interface coaloilcountRemote extends MboRemote
{
  public abstract void save()
    throws MXException, RemoteException;

  public abstract void add()
    throws MXException, RemoteException;
}
