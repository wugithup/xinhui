package com.shuto.mam.webclient.beans.asset;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
com.shuto.mam.webclient.beans.asset.AssprodAppBean 河北分公司（曹妃甸）
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月5日 下午3:18:59
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class AssprodAppBean extends AppBean
{
  public boolean hasAuth()
    throws MXException, RemoteException
  {
    long l = getMbo().getLong("TZ_ASSPROID");
    String s = getMbo().getString("STATUS");
    String s1 = getMbo().getUserInfo().getPersonId();
    if (s1.equalsIgnoreCase("maxadmin"))
      return true;
    if ("草稿".equals(s))
      return true;
    MboSetRemote mbosetremote = getMbo().getMboSet(
      "$instance", 
      "WFINSTANCE", 
      "ownertable='TZ_ASSPRO' and ownerid='" + l + 
      "' and active = 1");
    if (!(mbosetremote.isEmpty())) {
      String s2 = "ownerid='" + 
        l + 
        "' and ownertable='TZ_ASSPRO' and assignstatus='活动' and assigncode='" + 
        s1 + "'";
      MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode", 
        "WFASSIGNMENT", s2);
      return (!(mbosetremote1.isEmpty()));
    }
    return false; }

  public int ROUTEWF() throws MXException, RemoteException {
    if (!(hasAuth())) {
      throw new MXApplicationException("hasnoAuth", "hasnoAuth");
    }

    return super.ROUTEWF();
  }

  public int DELETE() throws MXException, RemoteException {
    MboRemote mbo = this.app.getAppBean().getMbo();
    String personid = mbo.getUserInfo().getPersonId();
    String createby = mbo.getString("createby");
    if ((getString("status").equals("草稿")) && (!(personid.equals(createby))) && 
      (!(personid.equals("MAXADMIN")))) {
      throw new MXApplicationException("hasnoAuth", "hasnoAuth");
    }
    return super.DELETE(); }

  public int SAVE() throws MXException, RemoteException {
    MboRemote mbo = this.app.getAppBean().getMbo();
    if (!(hasAuth())) {
      throw new MXApplicationException("hasnoAuth", "hasnoAuth");
    }
    String personid = mbo.getUserInfo().getPersonId();
    String createby = mbo.getString("createby");

    if ((getString("status").equals("草稿")) && (!(personid.equals(createby))) && 
      (!(personid.equals("MAXADMIN")))) {
      throw new MXApplicationException("hasnoAuth", "hasnoAuth");
    }

    String SAFETYMEASURE = mbo.getString("SAFETYMEASURE");
    String status = mbo.getString("status");
    if ((SAFETYMEASURE.isEmpty()) && (status.equals("实施人接收"))) {
      throw new MXApplicationException("ASSPROL", "请填写解除方法及安全措施");
    }
    MboSetRemote ASSPROZBSet = getMbo().getMboSet("TZ_ASSPROZB");
    String app = mbo.getString("app");
    if ((ASSPROZBSet.isEmpty()) && (app.equals("TZ_ASSPROD"))) {
      throw new MXApplicationException("ASSPROD", "请填写会签人员");
    }
    super.SAVE();
    return 1;
  }
}