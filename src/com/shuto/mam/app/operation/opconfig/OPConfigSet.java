package com.shuto.mam.app.operation.opconfig;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.operation.opconfig.OPConfigSet 磴口项目
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月7日 下午1:27:47
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class OPConfigSet extends MboSet
  implements OPConfigSetRemote
{
  public OPConfigSet(MboServerInterface mboserverinterface)
    throws MXException, RemoteException
  {
    super(mboserverinterface);
  }

  protected Mbo getMboInstance(MboSet mboset)
    throws MXException, RemoteException
  {
    return new OPConfig(mboset);
  }
}