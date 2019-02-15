package com.shuto.mam.webclient.beans.tz.common;

import java.rmi.RemoteException;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.beans.doclinks.AddDocLinksBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class AddDocBean extends AddDocLinksBean
{
  public int execute()
    throws MXException, RemoteException
  {
    long l = this.app.getAppBean().getMbo().getUniqueIDValue();
    String str = this.app.getAppBean().getMbo().getName();
    MboSetRemote localMboSetRemote = getMbo().getMboSet("$doclinks", "doclinks", "ownerid='" + l + "' and ownertable='" + str + "'");
    getMbo().setValue("sn", localMboSetRemote.count() + 1);
    super.execute();
    return 1;
  }
}