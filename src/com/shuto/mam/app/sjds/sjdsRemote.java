package com.shuto.mam.app.sjds;
/**  
com.shuto.mam.app.sjds.sjdsRemote 河北
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月23日 上午11:52:43
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public abstract interface sjdsRemote extends MboRemote
{
  public abstract void save()
    throws MXException, RemoteException;

  public abstract void add()
    throws MXException, RemoteException;
}