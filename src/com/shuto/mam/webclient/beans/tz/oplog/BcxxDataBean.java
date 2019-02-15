package com.shuto.mam.webclient.beans.tz.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/** 
* @author  lzq
* @date 创建时间：2017-3-22 下午2:02:52 
* @since  原阜阳项目台账类
*/
public class BcxxDataBean extends DataBean
{
  public int addrow()
    throws MXException
  {
    try
    {
      MboRemote Mbo = this.app.getAppBean().getMbo();
      String datanum = Mbo.getString("TZ_YX_YXFXNUM");
      String app = Mbo.getThisMboSet().getApp();
      System.out.println(app);
      String status = Mbo.getString("STATUS");
      if ("新建".equals(status)) {
        super.addrow();
        getMbo().setValue("NUM", datanum, 11L);
      }
    }
    catch (RemoteException e) {
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