package com.shuto.mam.webclient.beans.jngl.jngz.jn_xmgl;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**  
com.shuto.mam.webclient.beans.jngl.jngz.jn_xmgl.Jn_kxxyjbgAppBean 华南大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 下午3:32:08
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class Jn_kxxyjbgAppBean extends AppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();
    MboRemote mbo = getMbo();
    mbo.setValue("TYPE", "C");

    return 1;
  }

  public int SAVE()
    throws MXException, RemoteException
  {
    return super.SAVE();
  }
}