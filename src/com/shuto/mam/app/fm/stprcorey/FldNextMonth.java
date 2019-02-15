package com.shuto.mam.app.fm.stprcorey;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 电费结算和电量结算的下个月的月份
 com.shuto.mam.app.fm.stprcorey.FldNextMonth 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午8:52:51
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldNextMonth extends MboValueAdapter
{
  public FldNextMonth(MboValue mbv)
  {
    super(mbv); }

  public void action() throws MXException, RemoteException {
    super.action();
    MboRemote mbo = getMboValue().getMbo();
    int nextyear1 = mbo.getInt("NEXTYEAR1");
    int nextmonth = mbo.getInt("NEXTMONTH");
    int nextyear2 = 0;
    int thirdmonth = 0;
    String ny2 = null;
    String tm = null;
    if (nextmonth == 12) {
      nextyear2 = nextyear1 + 1;
      thirdmonth = 1;
      ny2 = Integer.toString(nextyear2);
      tm = Integer.toString(thirdmonth);
      mbo.setValue("NEXTYEAR2", ny2, 2L);
      mbo.setValue("THIRDMONTH", tm, 2L);
    } else {
      ny2 = Integer.toString(nextyear1);
      tm = Integer.toString(nextmonth + 1);
      mbo.setValue("NEXTYEAR2", ny2, 2L);
      mbo.setValue("THIRDMONTH", tm, 2L);
    }
  }
}