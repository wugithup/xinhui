package com.shuto.mam.webclient.beans.threeper;
/**  
com.shuto.mam.webclient.beans.threeper.ThreespAppBean 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月27日 下午2:30:55
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class ThreespAppBean extends CAppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();
    MboRemote localMboRemote = this.app.getAppBean().getMbo();
    MboSetRemote localMboSetRemote = localMboRemote.getMboSet("PERSON");
    localMboRemote.setValue("DEPARTMENT", localMboSetRemote.getMbo(0).getString("DEPARTMENT"), 11L);
    localMboRemote.setValue("status", "编制", 11L);
    refreshTable();
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
    long l = getMbo().getLong("THREEPERSONID");
    String str1 = getMbo().getString("STATUS");
    String str2 = getMbo().getUserInfo().getPersonId();
    if (str2.equalsIgnoreCase("maxadmin"))
      return true;
    if ("编制".equals(str1))
      return true;
    MboSetRemote localMboSetRemote1 = getMbo().getMboSet("$instance", "WFINSTANCE", "ownertable='THREEPERSON' and ownerid='" + l + "' and active = 1");

    if (!localMboSetRemote1.isEmpty()) {
      String str3 = "ownerid='" + l + "' and ownertable='THREEPERSON' and assignstatus='活动' and assigncode='" + str2 + "'";

      MboSetRemote localMboSetRemote2 = getMbo().getMboSet("$assigncode", "WFASSIGNMENT", str3);

      return !localMboSetRemote2.isEmpty();
    }
    return false;
  }

  public int ROUTEWF() throws MXException, RemoteException
  {
    if (!hasAuth()) {
      throw new MXApplicationException("hasnoAuth", "hasnoAuth");
    }
    SAVE();
    return super.ROUTEWF();
  }
}
