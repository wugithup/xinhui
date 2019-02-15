package com.shuto.mam.webclient.beans.threeedu;
/**  
com.shuto.mam.webclient.beans.threeedu.ThreeEduAppBean 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月27日 下午9:37:28
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import com.shuto.mam.webclient.beans.base.CAppBean;

public class ThreeEduAppBean extends CAppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();
    MboRemote localMboRemote = this.app.getAppBean().getMbo();
    localMboRemote.setValue("status", "新建", 2L);

    refreshTable();
    return i;
  }
  public void FINISH() throws RemoteException, MXException {
    getMbo().setValue("status", "完成", 11L);
    super.SAVE();
  }

  public int SAVE()
    throws MXException, RemoteException
  {
    if (!hasAuth()) {
      throw new MXApplicationException("hasnoAuth", "hasnoAuth");
    }

    return super.SAVE();
  }

  public boolean hasAuth() throws MXException, RemoteException {
    System.out.println();
    long l = getMbo().getLong("ST_THREESAFEID");
    String str1 = getMbo().getString("STATUS");
    String str2 = getMbo().getUserInfo().getPersonId();
    if (str2.equalsIgnoreCase("maxadmin"))
      return true;
    if ("新建".equals(str1))
      return true;
    MboSetRemote localMboSetRemote1 = getMbo().getMboSet("$instance", "WFINSTANCE", "ownertable='ST_THREESAFE' and ownerid='" + l + "' and active = 1");

    if (!localMboSetRemote1.isEmpty()) {
      String str3 = "ownerid='" + l + "' and ownertable='ST_THREESAFE' and assignstatus='活动' and assigncode='" + str2 + "'";

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
