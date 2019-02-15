package com.shuto.mam.webclient.beans.jngl.jngz.jn_xmgl;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**  
com.shuto.mam.webclient.beans.jngl.jngz.jn_xmgl.Jn_kxxyjbgDataBean 华南大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 下午3:32:56
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class Jn_kxxyjbgDataBean extends DataBean
{
  public int addrow()
    throws MXException
  {
    try
    {
      MboRemote Mbo = this.app.getAppBean().getMbo();
      String datanum = Mbo.getString("JN_XMGLNUM");

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