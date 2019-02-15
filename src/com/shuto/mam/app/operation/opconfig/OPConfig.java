package com.shuto.mam.app.operation.opconfig;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 
com.shuto.mam.app.operation.opconfig.OPConfig 磴口项目
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月7日 下午1:27:38
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class OPConfig extends Mbo
  implements OPConfigRemote
{
  public OPConfig(MboSet mboset)
    throws RemoteException
  {
    super(mboset);
  }

  public void add() throws MXException, RemoteException {
    super.add();
    setValue("orgid", getInsertOrganization());
    setValue("siteid", getInsertSite());
  }

  public void delete(long l) throws MXException, RemoteException {
    getMboSet("opconfigasset").deleteAll(2L);
    super.delete(l);
  }
}