package com.shuto.mam.webclient.beans.fybhtt;


import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月21日
 * @since:阜阳台账
 */
public class OpamdopAppBean extends CAppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    MboRemote localMboRemote = getMbo();

    if ((localMboRemote.getString("TZ_FY_OPAMDOPCODE") == null) || ("".equals(localMboRemote.getString("TZ_FY_OPAMDOPCODE"))))
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

    MboSetRemote localMboSetRemote = localMboRemote.getMboSet("$TZ_FY_OPAMDOP", "TZ_FY_OPAMDOP", "  TZ_FY_OPAMDOPCODE  in (select max(TZ_FY_OPAMDOPCODE) from TZ_FY_OPAMDOP where   to_char(ENTRYTIME,'yyyyMMdd')='" + localSimpleDateFormat.format(localCalendar.getTime()) + "'  )   ");

    DecimalFormat localDecimalFormat = new DecimalFormat("000");

    if (localMboSetRemote.isEmpty()) {
      localMboRemote.setValue("TZ_FY_OPAMDOPCODE", "BHTT-" + localSimpleDateFormat.format(localCalendar.getTime()) + "-001", 11L);
    }
    else {
      String str1 = localMboSetRemote.getMbo(0).getString("TZ_FY_OPAMDOPCODE");
      String str2 = str1.substring(str1.length() - 3, str1.length());

      int i = Integer.parseInt(str2);
      i += 1;
      localMboRemote.setValue("TZ_FY_OPAMDOPCODE", "BHTT-" + localSimpleDateFormat.format(localCalendar.getTime()) + "-" + localDecimalFormat.format(i), 11L);
    }

    localMboSetRemote.close();
  }
}