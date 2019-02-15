package com.shuto.mam.webclient.beans.tz.check;

import java.rmi.RemoteException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class CheckAppBean extends AppBean
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
  public void EXE() throws RemoteException, MXException {
    super.SAVE();
    getMbo().setValue("STATUS", "关闭", 11L);
    super.SAVE();
  }
}