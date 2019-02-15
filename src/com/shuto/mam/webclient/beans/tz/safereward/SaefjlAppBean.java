package com.shuto.mam.webclient.beans.tz.safereward;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class SaefjlAppBean extends AppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();
    MboRemote localMboRemote = getMbo();
    MboSetRemote localMboSetRemote = localMboRemote.getMboSet("PERSON");
    localMboRemote.setValue("BLDEPARTMENT", localMboSetRemote.getMbo(0).getString("S_DEPARTMENTSID"), 11L);
    localMboRemote.setValue("status", "编制", 11L);
    localMboRemote.setValue("APPTYPE", "奖励", 11L);
    return i;
  }

  public int SAVE() throws MXException, RemoteException
  {
    if (!hasAuth()) {
      throw new MXApplicationException("hasnoAuth", "hasnoAuth");
    }

    return super.SAVE();
  }
  public boolean hasAuth() throws MXException, RemoteException {
    long l = getMbo().getLong("ST_SAFEREWARDID");
    String str1 = getMbo().getString("STATUS");
    String str2 = getMbo().getUserInfo().getPersonId();
    if (str2.equalsIgnoreCase("maxadmin"))
      return true;
    if ("编制".equals(str1))
      return true;
    MboSetRemote localMboSetRemote1 = getMbo().getMboSet("$instance", "WFINSTANCE", "ownertable='ST_SAFEREWARD' and ownerid='" + l + "' and active = 1");

    if (!localMboSetRemote1.isEmpty()) {
      String str3 = "ownerid='" + l + "' and ownertable='ST_SAFEREWARD' and assignstatus='活动' and assigncode='" + str2 + "'";

      MboSetRemote localMboSetRemote2 = getMbo().getMboSet("$assigncode", "WFASSIGNMENT", str3);

      return !localMboSetRemote2.isEmpty();
    }
    return false;
  }
  public int ROUTEWF() throws MXException, RemoteException {
    if (!hasAuth()) {
      throw new MXApplicationException("hasnoAuth", "hasnoAuth");
    }
    SAVE();
    return super.ROUTEWF();
  }
}