package com.shuto.mam.webclient.beans.om.drill;
/**  
com.shuto.mam.webclient.beans.om.drill.DrillDataBean 华东
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月11日 下午5:03:06
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class DrillDataBean extends DataBean
{
  public int addrow()
    throws MXException
  {
    try
    {
      MboRemote Mbo = this.app.getAppBean().getMbo();
      String datanum = Mbo.getString("TZ_SAFETYACTIVITYNUM");
      String app = Mbo.getString("APP");
      String status = Mbo.getString("STATUS");
      if ("新建".equals(status)) {
        super.addrow();
        getMbo().setValue("DATANUM", datanum, 11L);
        getMbo().setValue("APP", app, 11L);
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }

    return 1;
  }

  public int toggledeleterow() throws MXException
  {
    try {
      MboRemote Mbo = this.app.getAppBean().getMbo();
      String status = Mbo.getString("STATUS");
      if ("新建".equals(status))
        super.toggledeleterow();
    }
    catch (RemoteException e) {
      e.printStackTrace();
    }
    return 1;
  }
}
