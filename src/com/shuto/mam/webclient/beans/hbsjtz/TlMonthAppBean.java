package com.shuto.mam.webclient.beans.hbsjtz;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Calendar;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
/** 
* @author  lzq
* @date 创建时间：2017-3-22 下午2:02:52 
* @since  原阜阳项目台账类
*/
public class TlMonthAppBean extends HbsjAppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();

    MboRemote localMboRemote = getMbo();
    localMboRemote.setValue("type", "A2", 11L);
    addMX();

    return i;
  }

  public int SAVE() throws MXException, RemoteException
  {
    MboRemote localMboRemote1 = getMbo();
    Calendar localCalendar = Calendar.getInstance();
    int i = localCalendar.get(1);
    int j = localCalendar.get(2) + 1;
    String str = i + "年" + j + "月 " + localMboRemote1.getString("JIZU") + "脱硫综合月报";
    localMboRemote1.setValue("description", str, 11L);

    MboSetRemote localMboSetRemote = localMboRemote1.getMboSet("TZ_HBSJLINE");
    for (int k = 0; k < localMboSetRemote.count(); ++k) {
      MboRemote localMboRemote2 = localMboSetRemote.getMbo(k);

      double d1 = localMboRemote2.getDouble("A1");
      double d2 = localMboRemote2.getDouble("A2");

      if (d1 == 0.0D) {
        localMboRemote2.setValue("A3", 100, 11L);
      }
      else {
        double d3 = (d1 - d2) / d1 * 100.0D;
        BigDecimal localBigDecimal = new BigDecimal(String.valueOf(d3));
        localBigDecimal.setScale(2, 4);
        localMboRemote2.setValue("A3", localBigDecimal.doubleValue(), 11L);
      }
    }

    return super.SAVE();
  }

  public void addMX()
    throws MXException, RemoteException
  {
    MboRemote localMboRemote1 = getMbo();
    Calendar localCalendar = Calendar.getInstance();

    int i = localCalendar.getActualMaximum(5);

    MboSetRemote localMboSetRemote = localMboRemote1.getMboSet("TZ_HBSJLINE");

    long l = localMboRemote1.getUniqueIDValue();

    for (int j = 0; j < i; ++j) {
      localCalendar.set(5, j + 1);
      MboRemote localMboRemote2 = localMboSetRemote.addAtEnd();

      localMboRemote2.setValue("jltime", localCalendar.getTime(), 11L);

      localMboRemote2.setValue("parent", l, 11L);

      localMboRemote2.setValue("A4", 24, 11L);

      localMboRemote2.setValue("A5", 24, 11L);

      localMboRemote2.setValue("A6", 100, 11L);
    }
  }
}