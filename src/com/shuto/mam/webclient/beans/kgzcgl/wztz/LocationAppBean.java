package com.shuto.mam.webclient.beans.kgzcgl.wztz;

import java.rmi.RemoteException;
import java.util.Vector;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.SessionContext;
import psdi.webclient.system.controller.WebClientEvent;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月7日
 * @since:控股台账
 */
public class LocationAppBean extends AppBean
{
  public void submitmovedialog()
    throws RemoteException, MXException
  {
    MboRemote localMboRemote1 = this.app.getAppBean().getMbo();

    String str1 = localMboRemote1.getString("TEMLOCA");
    String str2 = localMboRemote1.getString("CAUSE");

    MboSetRemote localMboSetRemote = localMboRemote1.getMboSet("activeasset");
    Vector localVector = localMboSetRemote.getSelection();
    for (int i = 0; i < localVector.size(); ++i) {
      MboRemote localMboRemote2 = (MboRemote)localVector.get(i);

      String str3 = localMboRemote2.getString("assetnum");
      updateGdzc(str3, str1, str2);
    }

    this.sessionContext.queueEvent(new WebClientEvent("dialogok", this.sessionContext.getCurrentPageId(), "", this.sessionContext));
  }

  public void updateGdzc(String paramString1, String paramString2, String paramString3)
    throws RemoteException, MXException
  {
    MboRemote localMboRemote1 = this.app.getAppBean().getMbo();

    MboSetRemote localMboSetRemote1 = localMboRemote1.getMboSet("&TZ_GDZC", "TZ_GDZC", "assetnum='" + paramString1 + "'");
    if (!localMboSetRemote1.isEmpty()) {
      MboRemote localMboRemote2 = localMboSetRemote1.getMbo(0);
      localMboRemote2.setValue("locationnum", paramString2);
      String str1 = localMboRemote2.getString("status");
      String str2 = localMboRemote2.getString("S_DEPARTMENTSID");
      String str3 = localMboRemote2.getString("USEPERSON");
      String str4 = localMboRemote2.getString("TZ_GDZCID");

      MboSetRemote localMboSetRemote2 = localMboRemote2.getMboSet("STATUSLIST");
      MboRemote localMboRemote3 = localMboSetRemote2.add();

      localMboRemote3.setValue("status", str1, 2L);
      localMboRemote3.setValue("s_departmentsid", str2, 2L);
      localMboRemote3.setValue("useperson", str3, 2L);
      localMboRemote3.setValue("locationnum", paramString2, 2L);
      localMboRemote3.setValue("TZ_GDZCSTATUSNUM", localMboSetRemote2.count() + "", 2L);
      localMboRemote3.setValue("assetnum", paramString1);
      localMboRemote3.setValue("DESCRIPTION", paramString3);
      localMboRemote3.setValue("TZ_GDZCID", str4);
      localMboRemote3.setValue("isnew", "0");
      localMboSetRemote1.save();
    }

    if (localMboSetRemote1 != null)
      localMboSetRemote1.close();
  }
}