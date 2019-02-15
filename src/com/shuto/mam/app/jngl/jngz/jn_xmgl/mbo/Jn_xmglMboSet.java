package com.shuto.mam.app.jngl.jngz.jn_xmgl.mbo;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**  
com.shuto.mam.app.jngl.jngz.jn_xmgl.mbo.Jn_xmglMboSet 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 上午9:23:59
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class Jn_xmglMboSet extends MboSet
  implements Jn_xmglSetRemote
{
  public Jn_xmglMboSet(MboServerInterface ms)
    throws RemoteException
  {
    super(ms);
  }

  protected Mbo getMboInstance(MboSet mboset)
    throws MXException, RemoteException
  {
    return new Jn_xmglMbo(mboset);
  }
}