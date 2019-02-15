/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.webclient.beans.jngl.jngz.jn_xmgl;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;

public class Jn_kxxfxbgAppBean extends AppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();
    MboRemote mbo = getMbo();
      mbo.setValue("TYPE", "changeBig");

    return 1;
  }

  public int SAVE()
    throws MXException, RemoteException
  {
    return super.SAVE();
  }
}