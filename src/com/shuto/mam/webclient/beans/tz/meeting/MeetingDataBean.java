package com.shuto.mam.webclient.beans.tz.meeting;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class MeetingDataBean extends DataBean
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