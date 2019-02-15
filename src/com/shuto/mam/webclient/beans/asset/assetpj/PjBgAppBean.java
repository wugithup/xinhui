package com.shuto.mam.webclient.beans.asset.assetpj;

import com.shuto.mam.webclient.beans.base.CAppBean;
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class PjBgAppBean extends CAppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    if (!hasAuth()) {
      throw new MXApplicationException("hasnoAuth", "hasnoAuth");
    }
    String str = yzRequired(getMbo());
    if (!"".equals(str)) {
      throw new MXApplicationException("信息提示", "下列设备未完成评价:<br>" + str);
    }
    return super.SAVE();
  }

  public int ROUTEWF() throws MXException, RemoteException {
    SAVE();
    return super.ROUTEWF();
  }

  private String yzRequired(MboRemote paramMboRemote)
  {
    String str1 = "";
    try {
      String str2 = paramMboRemote.getString("siteid");
      String str3 = paramMboRemote.getString("status");
      MboSetRemote localMboSetRemote1 = paramMboRemote.getMboSet("assetpjsb");
      MboSetRemote localMboSetRemote2 = null;
      MboRemote localMboRemote = null;
      String str4 = "";

      for (int j = 0; j < localMboSetRemote1.count(); j++) {
        str4 = localMboSetRemote1.getMbo(j).getString("description");
        localMboSetRemote2 = localMboSetRemote1.getMbo(j).getMboSet("assetpjline");
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        int i = 0;
        for (int k = 0; k < localMboSetRemote2.count(); k++) {
          localMboRemote = localMboSetRemote2.getMbo(k);
          str5 = localMboRemote.getString("pjjg1");
          str6 = localMboRemote.getString("pjjg2");
          str7 = localMboRemote.getString("pjjg3");
          str8 = localMboRemote.getString("pjjg4");
          if ("HRNR000".equals(str2)) {
            if ("待运行专工评价".equals(str3)) {
              if ((str5 == null) || ("".equals(str5)))
                i = 1;
            }
            else if ("待技术部专工评价".equals(str3)) {
              if ((str6 == null) || ("".equals(str6)))
                i = 1;
            }
            else if ("待技术部部门评价".equals(str3)) {
              if ((str7 == null) || ("".equals(str7)))
                i = 1;
            }
            else if (("待公司领导评价".equals(str3)) && (
              (str8 == null) || ("".equals(str8)))) {
              i = 1;
            }
          }
        }

        if (i != 0)
          str1 = str1 + str4 + "<br>";
      }
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    catch (MXException localMXException) {
      localMXException.printStackTrace();
    }
    return str1;
  }

  public boolean hasAuth()
    throws MXException, RemoteException
  {
    long l = getMbo().getUniqueIDValue();
    String str1 = getMbo().getName().toUpperCase();
    String str2 = getMbo().getString("STATUS");
    String str3 = getMbo().getUserInfo().getPersonId();

    if (str3.equalsIgnoreCase("maxadmin")) {
      return true;
    }
    if (isNew(str2)) {
      return true;
    }
    MboSetRemote localMboSetRemote1 = getMbo().getMboSet("$instance", "WFINSTANCE", "ownertable='" + str1 + "' and ownerid='" + l + "' and active = 1");

    if (!localMboSetRemote1.isEmpty()) {
      String str4 = "ownerid='" + l + "' and ownertable='" + str1 + "' and assignstatus='活动' and assigncode='" + str3 + "'";

      MboSetRemote localMboSetRemote2 = getMbo().getMboSet("$assigncode", "WFASSIGNMENT", str4);

      return !localMboSetRemote2.isEmpty();
    }
    return false;
  }

  private boolean isNew(String paramString) {
    return "新建".equals(paramString);
  }
}