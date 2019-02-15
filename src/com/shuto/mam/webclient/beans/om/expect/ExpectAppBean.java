package com.shuto.mam.webclient.beans.om.expect;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**  
com.shuto.mam.webclient.beans.om.expect.ExpectAppBean 华东大区 阜阳项目 台账
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-18 下午1:49:47
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class ExpectAppBean extends AppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();
    String appName = getMbo().getThisMboSet().getApp();
    getMbo().setValue("APP", appName, 11L);
    return 1;
  }

  public int SAVE() throws MXException, RemoteException {
    return super.SAVE();
  }
}