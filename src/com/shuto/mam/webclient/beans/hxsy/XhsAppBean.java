package com.shuto.mam.webclient.beans.hxsy;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class XhsAppBean extends AppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();

    MboRemote localMboRemote = this.app.getAppBean().getMbo();

    Date localDate = new Date();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    String str1 = localSimpleDateFormat.format(localDate);

    MboSetRemote localMboSetRemote = localMboRemote.getMboSet("$TZ_XHS", "TZ_XHS", "  TZ_XHSNO  in (select max(TZ_XHSNO) from TZ_XHS where   to_char(createdate,'yyyyMMdd')='" + str1 + "'  )   ");

    DecimalFormat localDecimalFormat = new DecimalFormat("00");
    if (localMboSetRemote.isEmpty()) {
      localMboRemote.setValue("TZ_XHSNO", "CRPHZ/JSZCB—" + str1 + "01", 11L);
    }
    else {
      String str2 = localMboSetRemote.getMbo(0).getString("TZ_XHSNO");
      String str3 = str2.substring(str2.length() - 2, str2.length());
      int j = Integer.parseInt(str3);
      j += 1;
      localMboRemote.setValue("TZ_XHSNO", "CRPHZ/JSZCB—" + str1 + localDecimalFormat.format(j), 11L);
    }

    this.app.getAppBean().refreshTable();
    localMboSetRemote.close();

    return i;
  }
}