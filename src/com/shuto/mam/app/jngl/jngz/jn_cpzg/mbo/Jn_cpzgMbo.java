package com.shuto.mam.app.jngl.jngz.jn_cpzg.mbo;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**  
com.shuto.mam.app.jngl.jngz.jn_cpzg.mbo.Jn_cpzgMbo 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 上午9:35:46
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class Jn_cpzgMbo extends Mbo
  implements Jn_cpzgRemote
{
  public Jn_cpzgMbo(MboSet mboset)
    throws RemoteException
  {
    super(mboset);
  }

  public void init() throws MXException
  {
    super.init();
    try
    {
      MboSetRemote zbbset = getMboSet("JN_CPZGNUM");

      if (!zbbset.isEmpty()) {
        for (int i = 0; i < zbbset.count(); ++i) {
          MboRemote localMboRemote = zbbset.getMbo(i);
        }
      }

    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }
}