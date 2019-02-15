package com.shuto.mam.webclient.beans.tz.kgzcgl.gdzc;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class GdzcStatusDataBean extends DataBean
{
  public int addrow()
    throws MXException
  {
    super.addrow();
    try {
      MboRemote localMboRemote1 = getMbo();
      MboRemote localMboRemote2 = localMboRemote1.getOwner();
      MboSetRemote localMboSetRemote = localMboRemote2.getMboSet("STATUSLIST");
      localMboRemote1.setValue("GDZCSTATUSNUM", localMboSetRemote.count() + "");
    } catch (RemoteException localRemoteException) {
      localRemoteException.printStackTrace();
    }

    return 1;
  }
}