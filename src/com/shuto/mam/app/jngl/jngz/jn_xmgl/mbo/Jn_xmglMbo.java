/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.jngl.jngz.jn_xmgl.mbo;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;

public class Jn_xmglMbo extends Mbo
  implements Jn_xmglRemote
{
  public Jn_xmglMbo(MboSet mboset)
    throws RemoteException
  {
    super(mboset);
  }

  public void init() throws MXException
  {
    super.init();
    try {
      String type = getString("TYPE");

      if (type.equals("A")) {
        MboSetRemote jhbset = getMboSet("JHBNUM");
        if (!jhbset.isEmpty()) {
        	MboRemote localMboRemote1;
          for (int i = 0; i < jhbset.count(); ++i)
            localMboRemote1 = jhbset.getMbo(i);
        }
        else
        {
          MboRemote localMboRemote2;
            if (type.equals("changeBig")) {
            MboSetRemote fxbgset = getMboSet("KXXNUM");
            if (!fxbgset.isEmpty())
            {
              for (int i = 0; i < fxbgset.count(); ++i)
                localMboRemote2 = fxbgset.getMbo(i);
            }
          }
          else if (type.equals("C")) {
            MboSetRemote yjbgset = getMboSet("YJBGNUM");
            if (!yjbgset.isEmpty())
            {
              for (int j = 0; j < yjbgset.count(); ++j)
                localMboRemote2 = yjbgset.getMbo(j);
            }
          }
        }
      }
    }
    catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}