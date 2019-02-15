package com.shuto.mam.webclient.beans.deptreward;

import java.rmi.RemoteException;

import org.apache.poi.util.SystemOutLogger;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class DeptRewardAppBean extends AppBean
{
  protected void initialize()
    throws MXException, RemoteException
  {
    super.initialize();
//    MboRemote mbo = getMbo();
//    String s2 = mbo.getString("STATUS");
//    System.out.println(s2);
//    if (("编制".equals(s2)) || ("退回".equals(s2))) {
//      String[] readonlyStr = { "PROFESSION", "DEPTREWARDREASON", "DEPARTMENT", "TEAMNAME", 
//        "REWARDMONEY", "REPORTDESC", "DESCRIPTION", "BLDEPARTMENT" };
//      String[] readonlyStr2 = { "DEPTSAFEZGYJ", "DEPTLEADERYJ", "PRODUCELEADERYJ", "COMPANYMANAGERYJ" };
//      String[] requiredStr = { "PROFESSION", "DEPARTMENT", "TEAMNAME", "REWARDMONEY", "REPORTDESC" };
//      String[] requiredStr2 = { "DEPTSAFEZGYJ" };
//      mbo.setFieldFlag(readonlyStr, 7L, false);
//      mbo.setFieldFlag(readonlyStr2, 7L, true);
//      mbo.setFieldFlag(requiredStr, 128L, true);
//      mbo.setFieldFlag(requiredStr2, 128L, false);
//    }
  }

  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();
    MboRemote localMboRemote = this.app.getAppBean().getMbo();
    localMboRemote.setValue("status", "编制", 11L);
    localMboRemote.setValue("APPTYPE", "部门奖励", 11L);

    String str1 = localMboRemote.getString("status");
    if (str1.equals("编制")) {
      localMboRemote.setFieldFlag("DEPTSAFEZGYJ", 7L, true);
      localMboRemote.setFieldFlag("DEPTSAFEZGTIME", 128L, false);
    }
    return i;
  }

  public boolean hasAuth() throws MXException, RemoteException {
    long l = getMbo().getLong("ST_SAFEREWARDID");
    String str1 = getMbo().getString("STATUS");
    String str2 = getMbo().getUserInfo().getPersonId();
    if (str2.equalsIgnoreCase("maxadmin"))
      return true;
    if ("编制".equals(str1))
      return true;
    MboSetRemote localMboSetRemote1 = getMbo().getMboSet("$instance", "WFINSTANCE", 
      "ownertable='ST_SAFEREWARD' and ownerid='" + l + "' and active = 1");

    if (!localMboSetRemote1.isEmpty()) {
      String str3 = "ownerid='" + l + "' and ownertable='ST_SAFEREWARD' and assignstatus='活动' and assigncode='" + 
        str2 + "'";

      MboSetRemote localMboSetRemote2 = getMbo().getMboSet("$assigncode", "WFASSIGNMENT", str3);

      return !localMboSetRemote2.isEmpty();
    }
    return false;
  }

  public int SAVE() throws MXException, RemoteException
  {
    if (!hasAuth()) {
      throw new MXApplicationException("hasnoAuth", "hasnoAuth");
    }
    return super.SAVE();
  }

  public int ROUTEWF() throws MXException, RemoteException
  {
    if (!hasAuth()) {
      throw new MXApplicationException("hasnoAuth", "hasnoAuth");
    }
    SAVE();
    return super.ROUTEWF();
  }

  protected void setCurrentRecordData(MboRemote mbo) throws MXException, RemoteException
  {
    super.setCurrentRecordData(mbo);
    String s2 = mbo.getString("status");
    if (("编制".equals(s2)) || ("退回".equals(s2))) {
      String[] readonlyStr = { "PROFESSION", "DEPTREWARDREASON", "DEPARTMENT", "TEAMNAME", 
        "REWARDMONEY", "REPORTDESC", "DESCRIPTION", "BLDEPARTMENT" };
      String[] readonlyStr2 = { "DEPTSAFEZGYJ", "DEPTLEADERYJ", "PRODUCELEADERYJ", "COMPANYMANAGERYJ" };
      String[] requiredStr = { "PROFESSION", "DEPARTMENT", "TEAMNAME", "REWARDMONEY", "REPORTDESC" };
      String[] requiredStr2 = { "DEPTSAFEZGYJ" };

      mbo.setFieldFlag(readonlyStr, 7L, false);
      mbo.setFieldFlag(readonlyStr2, 7L, true);
      mbo.setFieldFlag(requiredStr, 128L, true);
      mbo.setFieldFlag(requiredStr2, 128L, false);
    } else if ("待部门安全专工审核".equals(s2)) {
      String[] requiredStr = { "DEPTSAFEZGYJ" };
      String[] readonlyStr = { "DEPTLEADERYJ", "PRODUCELEADERYJ", "COMPANYMANAGERYJ" };
      mbo.setFieldFlag(readonlyStr, 7L, true);
      mbo.setFieldFlag(requiredStr, 128L, true);
    }
  }
}