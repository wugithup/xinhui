package com.shuto.mam.webclient.beans.oplog.tsdlxdan;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
/**
 * 停送电联系单
 com.shuto.mam.webclient.beans.oplog.tsdlxdan.DlxdanBean 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午2:27:25
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class DlxdanBean extends CAppBean
{
  public boolean hasAuth()
    throws MXException, RemoteException
  {
    long tablesid = getMbo().getLong("ST_TSDLXDANID");
    String s = getMbo().getString("STATUS");
    String s1 = getMbo().getUserInfo().getPersonId();
    if (s1.equalsIgnoreCase("maxadmin"))
      return true;
    if ("新建".equals(s))
      return true;
    MboSetRemote mbosetremote = getMbo().getMboSet("$instance",
      "WFINSTANCE",
      "ownertable='ST_TSDLXDAN' and ownerid='" + tablesid + "' and active = 1");
    if (!(mbosetremote.isEmpty())) {
      String s2 = "ownerid='" +
        tablesid +
        "' and ownertable='ST_TSDLXDAN' and assignstatus='活动' and assigncode='" +
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
    String createby = mbo.getString("CREATEPERSON");
    if ((getString("status").equals("新建")) && (!(personid.equals(createby))) &&
      (!(personid.equals("MAXADMIN")))) {
      throw new MXApplicationException("system", "SYSSAVE2");
    }

    return super.SAVE();
  }

  @Override
  public int DELETE()
    throws MXException, RemoteException
  {
    MboRemote mbo = this.app.getAppBean().getMbo();
    String personid = mbo.getUserInfo().getPersonId();
    String createby = mbo.getString("CREATEPERSON");
    if ((getString("status").equals("新建")) && (!(personid.equals(createby))) &&
      (!(personid.equals("MAXADMIN")))) {
      throw new MXApplicationException("system", "SYSDELETE2");
    }

    return super.DELETE();
  }
}