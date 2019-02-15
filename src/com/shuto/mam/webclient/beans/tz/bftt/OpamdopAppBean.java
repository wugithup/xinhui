package com.shuto.mam.webclient.beans.tz.bftt;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class OpamdopAppBean extends AppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    MboRemote localMboRemote = getMbo();

    if ((localMboRemote.getString("OPAMDOPCODE") == null) || ("".equals(localMboRemote.getString("OPAMDOPCODE"))))
    {
      createOpamdopCode();
    }
    return super.SAVE();
  }

  public void createOpamdopCode()
    throws RemoteException, MXException
  {
    MboRemote localMboRemote = getMbo();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    Calendar localCalendar = Calendar.getInstance();

    MboSetRemote localMboSetRemote = localMboRemote.getMboSet("$OPAMDOP", "OPAMDOP", "  OPAMDOPCODE  in (select max(OPAMDOPCODE) from OPAMDOP where   to_char(ENTRYTIME,'yyyyMMdd')='" + localSimpleDateFormat.format(localCalendar.getTime()) + "'  )   ");

    DecimalFormat localDecimalFormat = new DecimalFormat("000");

    if (localMboSetRemote.isEmpty()) {
      localMboRemote.setValue("OPAMDOPCODE", "BHTT-" + localSimpleDateFormat.format(localCalendar.getTime()) + "-001", 11L);
    }
    else {
      String str1 = localMboSetRemote.getMbo(0).getString("OPAMDOPCODE");
      String str2 = str1.substring(str1.length() - 3, str1.length());

      int i = Integer.parseInt(str2);
      i += 1;
      localMboRemote.setValue("OPAMDOPCODE", "BHTT-" + localSimpleDateFormat.format(localCalendar.getTime()) + "-" + localDecimalFormat.format(i), 11L);
    }

    localMboSetRemote.close();
  }
}