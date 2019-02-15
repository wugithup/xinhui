package com.shuto.mam.webclient.beans.om.drill;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**  
com.shuto.mam.webclient.beans.om.drill.DrillAppBean 华东大区 阜阳项目 台账
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-18 下午1:49:25
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class DrillAppBean extends AppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();
    String appName = this.getMbo().getThisMboSet().getApp();
    this.getMbo().setValue("APP", appName, 11L);
    return 1;
  }

  public int SAVE() throws MXException, RemoteException {
    return super.SAVE();
  }

  public void EXE() throws RemoteException, MXException {
    super.SAVE();
    getMbo().setValue("STATUS", "关闭", 11L);
    super.SAVE();
  }
}