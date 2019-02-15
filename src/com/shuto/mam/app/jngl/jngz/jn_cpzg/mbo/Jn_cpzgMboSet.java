package com.shuto.mam.app.jngl.jngz.jn_cpzg.mbo;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**  
com.shuto.mam.app.jngl.jngz.jn_cpzg.mbo.Jn_cpzgMboSet 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 上午9:35:56
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class Jn_cpzgMboSet extends MboSet
  implements Jn_cpzgSetRemote
{
  public Jn_cpzgMboSet(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet mboset)
    throws MXException, RemoteException
  {
    return new Jn_cpzgMbo(mboset);
  }
}