package com.shuto.mam.webclient.beans.rl.rlghjl;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

public class RlghjlAppBean extends CAppBean
{
  public int ROUTEWF()
    throws MXException, RemoteException
  {
    SAVE();
    return super.ROUTEWF();
  }

  public int SAVE()
    throws MXException, RemoteException
  {
    MboRemote AppMbo = this.app.getAppBean().getMbo();
    String status = AppMbo.getString("STATUS");
    if ("新建".equals(status)) {
      MboSetRemote RldhtzSet = AppMbo.getMboSet("RLDHTZ");
      if (!RldhtzSet.isEmpty()) {
        MboRemote RldhtzMbo = null;
        for (int i = 0; i < RldhtzSet.count(); ++i) {
          RldhtzMbo = RldhtzSet.getMbo(i);
          RldhtzMbo.setValue("WCCS", RldhtzMbo.getMboSet("RLJLMX")
            .count(), 11L);
        }
      }
    }
    return super.SAVE();
  }
}