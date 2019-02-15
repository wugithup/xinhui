package com.shuto.mam.webclient.beans.tz.contract;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class ContTaskOrderAppBean extends AppBean
{
  public synchronized boolean fetchRecordData()
    throws MXException, RemoteException
  {
    MboRemote localMboRemote = getMbo();
    if (localMboRemote != null) {
      String str1 = localMboRemote.getString("status");
      String str2 = localMboRemote.getString("siteid");
      String[] arrayOfString = { "XMMC", "PROFESSION", "CONTENT", "ISBGGC" };
      if ("湖北电厂".equals(str2)) {
        if (("待委托部门审批".equals(str1)) || ("待经营策划部审批".equals(str1)) || ("待经营策划部接收".equals(str1))) {
          localMboRemote.setFieldFlag(arrayOfString, 7L, true);
        }
        else if (!"草稿".equals(str1)) {
          localMboRemote.setFlag(7L, true);
        }
      }
    }
    return super.fetchRecordData();
  }
}