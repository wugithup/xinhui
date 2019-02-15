package com.shuto.mam.webclient.beans.tz.operation.qjcz;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class qjczAppBean extends AppBean
{
  public int DELETE()
    throws MXException, RemoteException
  {
    MboRemote localMboRemote = this.app.getAppBean().getMbo();

    localMboRemote.getMboSet("TZ_QJCZMX").deleteAll();
    localMboRemote.delete();
    return super.DELETE();
  }
}