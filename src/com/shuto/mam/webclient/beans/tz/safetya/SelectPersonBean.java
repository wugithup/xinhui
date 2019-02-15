package com.shuto.mam.webclient.beans.tz.safetya;

import java.rmi.RemoteException;
import java.util.Vector;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.NonPersistentMboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class SelectPersonBean extends DataBean
{
  public MboSetRemote getMboSetRemote()
    throws MXException, RemoteException
  {
    MboRemote Mbo = this.app.getAppBean().getMbo();
    MboSetRemote personSet = Mbo.getMboSet("$person", "PERSON", "status = '活动' and department is not null");
    return personSet;
  }
  public int execute() throws RemoteException, MXException {
    if (this.mboSetRemote != null)
      if (this.mboSetRemote instanceof NonPersistentMboSetRemote) {
        ((NonPersistentMboSetRemote)this.mboSetRemote).execute();
        this.clientSession.addMXWarnings(this.mboSetRemote.getWarnings());
        if (this.parent != null)
          return this.parent.execute();
        this.app.getAppBean().save();
      }
      else if ((this.parent != null) && (this.parentRelationship != null)) {
        return this.parent.execute();
      }
    Vector personLineSet = getMboSet().getSelection();
    MboRemote personLineMbo = null;
    String personid = "";
    MboRemote Mbo = this.app.getAppBean().getMbo();
    String appName = Mbo.getThisMboSet().getApp();
    String noticebillnum = Mbo.getString("TZ_SAFETYACTIVITYNUM").replaceAll(",", "");
    MboSetRemote overhauljdpersonSet = Mbo.getMboSet("PERSONDATA");
    MboRemote overhauljdpersonMbo = null;
    for (int i = 0; i < personLineSet.size(); ++i) {
      personLineMbo = (MboRemote)personLineSet.elementAt(i);
      personid = personLineMbo.getString("PERSONID");
      overhauljdpersonMbo = overhauljdpersonSet.add();
      overhauljdpersonMbo.setValue("DATANUM", noticebillnum);
      overhauljdpersonMbo.setValue("APP", appName);
      overhauljdpersonMbo.setValue("PERSONID", personid);
    }
    this.app.getAppBean().reloadTable();
    return 1;
  }
}