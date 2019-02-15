package com.shuto.mam.webclient.beans.tz.operation.dzedit;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/** 
 * @author  lzq
 * @date 创建时间：2017-3-24 上午9:31:27 
 * @since  原河南台账相关类
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
public class DzeditBean extends AppBean
{
  public boolean hasAuth()
    throws MXException, RemoteException
  {
    long tablesid = getMbo().getLong("DINGZHIEDITID");
    String s = getMbo().getString("STATUS");
    String s1 = getMbo().getUserInfo().getPersonId();
    if (s1.equalsIgnoreCase("maxadmin"))
      return true;
    if ("NEW".equals(s))
      return true;
    MboSetRemote mbosetremote = getMbo().getMboSet("$instance", 
      "WFINSTANCE", 
      "ownertable='DINGZHIEDIT' and ownerid='" + tablesid + "' and active = 1");
    if (!mbosetremote.isEmpty()) {
      String s2 = "ownerid='" + 
        tablesid + 
        "' and ownertable='DINGZHIEDIT' and assignstatus='活动' and assigncode='" + 
        s1 + "'";
      MboSetRemote mbosetremote1 = getMbo().getMboSet("$assigncode", 
        "WFASSIGNMENT", s2);
      return !mbosetremote1.isEmpty();
    }
    return false;
  }

  public int ROUTEWF()
    throws MXException, RemoteException
  {
    MboSetRemote DZXG_ZYCS = getMbo().getMboSet("DZXG_ZYCS");
    if (!hasAuth()) {
      throw new MXApplicationException("system", "SYSROUTEWF2");
    }

    if (DZXG_ZYCS.count() == 0) {
      throw new MXApplicationException("提示信息：", "请指定专业审核人！");
    }
    return super.ROUTEWF();
  }

  public int SAVE()
    throws MXException, RemoteException
  {
    MboRemote mbo = this.app.getAppBean().getMbo();

    String personid = mbo.getUserInfo().getPersonId();

    String createperson = mbo.getString("CREATEPERSON");
    if (!hasAuth()) {
      throw new MXApplicationException("system", "SYSSAVE2");
    }

    if (("NEW".equals(getString("status"))) && (!createperson.equals(personid)) && (!"MAXADMIN".equals(personid)))
    {
      throw new MXApplicationException("system", "SYSSAVE2");
    }

    return super.SAVE();
  }
}