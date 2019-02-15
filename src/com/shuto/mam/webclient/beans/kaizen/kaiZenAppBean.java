package com.shuto.mam.webclient.beans.kaizen;

import java.rmi.RemoteException;
import java.util.Date;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class kaiZenAppBean extends AppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    try
    {
      super.SAVE();
    } catch (Exception localException) {
      localException.printStackTrace();
    }
    return 1;
  }

  public int INSERT() throws MXException, RemoteException
  {
    try
    {
      super.INSERT();
      MboRemote localMboRemote1 = getMbo();
      localMboRemote1.setValue("CREATEDATE", new Date());
      String str1 = localMboRemote1.getUserInfo().getPersonId();
      MboRemote localMboRemote2 = localMboRemote1.getMboSet("$person", "person", "personid='" + str1 + "'").getMbo(0);
      String str2 = "";
      String str3 = "";
      if (localMboRemote2 != null) {
        str2 = localMboRemote2.getString("department");
        str3 = localMboRemote2.getString("post");
      }
      localMboRemote1.setValue("REQUESTBY", str1, 11L);
      localMboRemote1.setValue("DEPARTEMNT", str2, 11L);
      localMboRemote1.setValue("status", "草稿", 11L);
      localMboRemote1.setValue("EFFECTIVE", Boolean.TRUE.booleanValue(), 11L);
      localMboRemote1.setValue("COMPLETEMODE", "本人", 2L);
      localMboRemote1.setValue("SCORE", 2, 2L);
      localMboRemote1.setValue("ZHIBIE", str3, 2L);
      localMboRemote1.setValue("SSPERSON", str1, 2L);

      refreshTable();
    } catch (Exception localException) {
      localException.printStackTrace();
    }
    return 1;
  }

  public int DUPLICATE()
    throws MXException, RemoteException
  {
    MboRemote localMboRemote1 = this.app.getAppBean().getMbo();
    Date localDate = MXServer.getMXServer().getDate();
    String str1 = localMboRemote1.getThisMboSet().getUserInfo().getPersonId();
    String str2 = localMboRemote1.getString("siteid");
    MboSetRemote localMboSetRemote = localMboRemote1.getMboSet("$PERSON", "PERSON", "PERSONID = '" + str1 + "' and LOCATIONSITE = '" + str2 + "'");
    MboRemote localMboRemote2 = localMboSetRemote.getMbo(0);
    String str3 = localMboRemote2.getString("department");
    MboRemote localMboRemote3 = getMboSet().add();
    String str4 = localMboRemote1.getString("projectname");
    String str5 = localMboRemote1.getString("zhibie");
    String str6 = localMboRemote1.getString("bimprovement");
    String str7 = localMboRemote1.getString("aimprovement");
    localMboRemote3.setValue("status", "草稿", 11L);
    localMboRemote3.setValue("createdate", localDate, 11L);
    localMboRemote3.setValue("requestby", str1, 11L);
    localMboRemote3.setValue("PROJECTNAME", str4, 11L);
    localMboRemote3.setValue("ZHIBIE", str5, 11L);
    localMboRemote3.setValue("BIMPROVEMENT", str6, 11L);
    localMboRemote3.setValue("AIMPROVEMENT", str7, 11L);
    localMboRemote3.setValue("DEPARTEMNT", str3, 11L);
    setCurrentRecordData(localMboRemote3);
    localMboSetRemote.close();
    return 1;
  }
}