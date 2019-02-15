package com.shuto.mam.webclient.beans.oplog.jsexplain;

import java.io.IOException;
import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
com.shuto.mam.webclient.beans.oplog.jsexplain.JsexplainBean 河北分公司（曹妃甸）
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月5日 上午10:49:55
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */

public class JsexplainBean extends AppBean
{
  public void JDTRUE()
    throws RemoteException, MXException, IOException
  {
    MboRemote rec = getMbo();
    rec.setValue("STATUS", "有效");
    super.save();
  }

  public void JDFALSE() throws RemoteException, MXException, IOException
  {
    MboRemote rec = getMbo();
    rec.setValue("STATUS", "无效");
    super.save();
  }

  public boolean hasAuth() throws MXException, RemoteException
  {
    long tablesid = getMbo().getLong("ST_JSEXPLAINID");
    String s = getMbo().getString("STATUS");
    String s1 = getMbo().getUserInfo().getPersonId();
    if (s1.equalsIgnoreCase("maxadmin"))
      return true;
    if (!("有效".equals(s)))
      return true;
    MboSetRemote mbosetremote = getMbo().getMboSet("$instance", 
      "WFINSTANCE", 
      "ownertable='ST_JSEXPLAIN' and ownerid='" + tablesid + "' and active = 1");
    if (!(mbosetremote.isEmpty())) {
      String s2 = "ownerid='" + 
        tablesid + 
        "' and ownertable='ST_JSEXPLAIN' and assignstatus='活动' and assigncode='" + 
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
    if ((!(getString("status").equals("有效"))) && (!(personid.equals(createby))) && 
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
    if ((!(getString("status").equals("有效"))) && (!(personid.equals(createby))) && 
      (!(personid.equals("MAXADMIN")))) {
      throw new MXApplicationException("system", "SYSDELETE2");
    }

    return super.DELETE();
  }
}