package com.shuto.mam.webclient.beans.jngl.jntz;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**  
com.shuto.mam.webclient.beans.jngl.jntz.JnzbwhDataBean 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 下午3:22:21
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class JnzbwhDataBean extends DataBean
{
  public int addrow()
    throws MXException
  {
    try
    {
      super.addrow();
      MboRemote Mbo = getMbo().getOwner();
      String applyid = Mbo.getString("jnzbwhnum");
      getMbo().setValue("jnzbwhnum", applyid, 11L);
    }
    catch (RemoteException e) {
      e.printStackTrace();
    }
    return 1;
  }

  public int toggledeleterow() throws MXException {
    return super.toggledeleterow();
  }
}