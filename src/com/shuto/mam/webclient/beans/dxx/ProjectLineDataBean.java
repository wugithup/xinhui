package com.shuto.mam.webclient.beans.dxx;

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
public class ProjectLineDataBean extends DataBean
{
  protected void initialize()
    throws MXException, RemoteException
  {
    MboSetRemote localSet = getMboSet();

    int i = 1;
    for (MboRemote thisMbo = localSet.moveFirst(); thisMbo != null; thisMbo = localSet.moveNext()) {
      thisMbo.setValue("seqnum", i, 11L);
      ++i;
    }
  }
}