package com.shuto.mam.webclient.beans.hbsjtz;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/** 
* @author  lzq
* @date 创建时间：2017-3-22 下午2:02:52 
* @since  原阜阳项目台账类
*/
public abstract class HbsjAppBean extends AppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();

    MboRemote localMboRemote = getMbo();

    String str = localMboRemote.getUserInfo().getPersonId();

    localMboRemote.setValue("createby", str, 11L);

    localMboRemote.setValue("createdate", new Date(), 11L);

    Calendar localCalendar = Calendar.getInstance();
    int j = localCalendar.get(1);
    int k = localCalendar.get(2) + 1;

    localMboRemote.setValue("year", j, 11L);
    localMboRemote.setValue("month", k, 11L);

    return i;
  }

  public int SAVE() throws MXException, RemoteException
  {
    return super.SAVE();
  }

  public abstract void addMX()
    throws MXException, RemoteException;
}