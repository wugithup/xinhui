package com.shuto.mam.app.fm.stprcorey;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 电费结算和电量结算的月份
 com.shuto.mam.app.fm.stprcorey.FLdMonth 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:51:46
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FLdMonth extends MboValueAdapter
{
  public FLdMonth(MboValue mbv)
  {
    super(mbv); }

  public void action() throws MXException, RemoteException {
    super.action();
    MboRemote mbo = getMboValue().getMbo();
    int year = mbo.getInt("YEAR");
    int month = mbo.getInt("MONTH");
    int nextyear1 = 0;
    int nextmonth = 0;
    String ny1 = null;
    String nm = null;
    if (month == 12) {
      nextyear1 = year + 1;
      nextmonth = 1;
      ny1 = Integer.toString(nextyear1);
      nm = Integer.toString(nextmonth);
      mbo.setValue("NEXTYEAR1", ny1, 2L);
      mbo.setValue("NEXTMONTH", nm, 2L);
    } else {
      ny1 = Integer.toString(year);
      nm = Integer.toString(month + 1);
      mbo.setValue("NEXTYEAR1", ny1, 2L);
      mbo.setValue("NEXTMONTH", nm, 2L);
    }
  }
}