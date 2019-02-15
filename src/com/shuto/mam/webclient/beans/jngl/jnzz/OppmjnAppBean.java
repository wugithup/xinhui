package com.shuto.mam.webclient.beans.jngl.jnzz;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**  
com.shuto.mam.webclient.beans.jngl.jnzz.OppmjnAppBean 华南大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 下午3:19:20
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class OppmjnAppBean extends AppBean{
	public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();
    getMbo().setValue("WORKTYPE", "JNPM", 11L);
    getMbo().setValue("LOCATION", "FYDC", 11L);
    return 1;
  }

  public void TZ() throws RemoteException, MXException {
    String DateStr = getMbo().getString("TZDATE");
    if (!("".equals(DateStr))) {
      super.SAVE();
      Date NewDate = getMbo().getDate("TZDATE");
      getMbo().setValue("NEXTDATE", NewDate, 11L);
      super.SAVE();
    }
  }

}
