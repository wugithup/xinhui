package com.shuto.mam.webclient.beans.oplog.sbyd;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 
com.shuto.mam.webclient.beans.oplog.sbyd.SbydysBean 河北分公司（曹妃甸）
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月5日 下午3:19:20
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class SbydysBean extends AppBean
{
  public boolean hasAuth()
    throws MXException, RemoteException
  {
    long tablesid = getMbo().getLong("JGTRANSACTIONID");
    String s = getMbo().getString("STATUS");
    String s1 = getMbo().getUserInfo().getPersonId();
    if (s1.equalsIgnoreCase("maxadmin"))
      return true;
    if ("NEW".equals(s))
      return true;
    MboSetRemote mbosetremote = getMbo().getMboSet("$instance", 
      "WFINSTANCE", 
      "ownertable='JGTRANSACTION' and ownerid='" + tablesid + "' and active = 1");
    if (!(mbosetremote.isEmpty())) {
      String s2 = "ownerid='" + 
        tablesid + 
        "' and ownertable='JGTRANSACTION' and assignstatus='活动' and assigncode='" + 
        s1 + "'";
      MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode", 
        "WFASSIGNMENT", s2);
      return (!(mbosetremote1.isEmpty()));
    }
    return false;
  }

  public int ROUTEWF() throws MXException, RemoteException
  {
    if (!(hasAuth())) {
      throw new MXApplicationException("system", "SYSROUTEWF2");
    }
    return super.ROUTEWF();
  }

  public int SAVE() throws MXException, RemoteException
  {
    if (!(hasAuth())) {
      throw new MXApplicationException("system", "SYSSAVE2");
    }
    MboRemote mbo = this.app.getAppBean().getMbo();
    String personid = mbo.getUserInfo().getPersonId();
    String createby = mbo.getString("newperson");
    if ((getString("status").equals("NEW")) && (!(personid.equals(createby))) && 
      (!(personid.equals("MAXADMIN")))) {
      throw new MXApplicationException("system", "SYSSAVE2");
    }

    return super.SAVE();
  }

  public int DELETE()
    throws MXException, RemoteException
  {
    MboRemote mbo = this.app.getAppBean().getMbo();
    String personid = mbo.getUserInfo().getPersonId();
    String createby = mbo.getString("newperson");
    if ((getString("status").equals("NEW")) && (!(personid.equals(createby))) && 
      (!(personid.equals("MAXADMIN")))) {
      throw new MXApplicationException("system", "SYSDELETE2");
    }

    return super.DELETE();
  }
}