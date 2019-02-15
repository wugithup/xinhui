package com.shuto.mam.webclient.beans.workplan;

import java.rmi.RemoteException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class WorkPlanAppBean extends AppBean
{
  public int DUPLICATE()
    throws MXException, RemoteException
  {
    super.DUPLICATE();
    this.app.getAppBean().setValue("ACTFINISH", "", 2L);
    this.app.getAppBean().setValue("COMPLETION", "", 2L);
    this.app.getAppBean().setValue("status", "新建", 2L);
    return 1;
  }
}