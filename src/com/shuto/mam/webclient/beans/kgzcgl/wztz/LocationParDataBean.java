package com.shuto.mam.webclient.beans.kgzcgl.wztz;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月7日
 * @since:控股台账
 */
public class LocationParDataBean extends DataBean
{
  public int addrow()
    throws MXException
  {
    try
    {
      MboRemote localMboRemote = this.app.getAppBean().getMbo();
      MboSetRemote localMboSetRemote = localMboRemote.getMboSet("LOCATIONPARENT");
      if (!localMboSetRemote.isEmpty())
        throw new MXApplicationException("kggdzc", "locationparentnotnull");
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }

    return super.addrow();
  }
}
