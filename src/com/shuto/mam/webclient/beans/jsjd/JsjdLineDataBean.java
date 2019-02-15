package com.shuto.mam.webclient.beans.jsjd;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**  
com.shuto.mam.webclient.beans.jsjd.JsjdLineDataBean xx大区 XX项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-13 下午4:13:25
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class JsjdLineDataBean extends DataBean
{
  public int addrow()
    throws MXException
  {
    super.addrow();
    try {
      getMbo().setValue("SN", getCurrentRow() + 1 + "");
    } catch (RemoteException localRemoteException) {
      localRemoteException.printStackTrace();
    }

    return 1;
  }
}