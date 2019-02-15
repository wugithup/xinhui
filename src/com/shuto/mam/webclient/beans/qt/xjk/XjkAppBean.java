package com.shuto.mam.webclient.beans.qt.xjk;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

public class XjkAppBean extends CAppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();
    MboRemote Mbo = getMbo();
    MboRemote PersonMbo = Mbo.getMboSet("CREATEBY").getMbo(0);
    String depnum = PersonMbo.getString("DEPARTMENT");

    Mbo.setValue("DEPNUM", depnum, 11L);
    return 1;
  }

  public int SAVE() throws MXException, RemoteException {
    super.SAVE();
    MboSetRemote SnSet = getMbo().getMboSet("XJKDETAIL");
    MboRemote SnMbo = null;

    if (!SnSet.isEmpty()) {
      SnSet.setOrderBy("DATEFX");
      SnSet.reset();
      int snS = 0;
      for (int sn = 0; sn < SnSet.count(); ++sn) {
        SnMbo = SnSet.getMbo(sn);
        snS = sn + 1;
        SnMbo.setValue("SN", snS);
      }
      super.SAVE();
    }
    return 1;
  }
}