package com.shuto.mam.webclient.beans.tz.hbsj;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class TlHbsjAppBean extends HbsjAppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();

    MboRemote localMboRemote = getMbo();
    localMboRemote.setValue("type", "A1", 11L);

    addMX();

    return i;
  }

  public int SAVE() throws MXException, RemoteException
  {
    MboRemote localMboRemote = getMbo();
    Calendar localCalendar = Calendar.getInstance();
    int i = localCalendar.get(1);
    int j = localCalendar.get(2) + 1;
    String str = i + "年" + j + "月 " + localMboRemote.getString("JIZU") + "脱硫运行基础参数";
    localMboRemote.setValue("description", str, 11L);

    return super.SAVE();
  }

  public void addMX()
    throws MXException, RemoteException
  {
    MboRemote localMboRemote1 = getMbo();
    Calendar localCalendar = Calendar.getInstance();

    int i = localCalendar.getActualMaximum(5);

    MboSetRemote localMboSetRemote = localMboRemote1.getMboSet("HBSJLINE");

    long l = localMboRemote1.getUniqueIDValue();

    for (int j = 0; j < i; ++j) {
      localCalendar.set(5, j + 1);
      MboRemote localMboRemote2 = localMboSetRemote.addAtEnd();

      localMboRemote2.setValue("jltime", localCalendar.getTime(), 11L);

      localMboRemote2.setValue("parent", l);

      localMboRemote2.setValue("EXT1", "已拆除", 11L);

      localMboRemote2.setValue("EXT2", "已拆除", 11L);

      localMboRemote2.setValue("EXT3", "无", 11L);
    }
  }

  public static void main(String[] paramArrayOfString) {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(5, 1);
    System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime()));
  }
}