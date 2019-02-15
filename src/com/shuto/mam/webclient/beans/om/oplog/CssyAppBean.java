package com.shuto.mam.webclient.beans.om.oplog;


import java.rmi.RemoteException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class CssyAppBean extends AppBean
{
  public void TJ()
    throws RemoteException, MXException
  {
    getMbo().setValue("STATUS", "关闭", 11L);
    super.SAVE();
  }
}