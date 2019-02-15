package com.shuto.mam.webclient.beans.tz.calendar;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * @author lzq
 * @date 创建时间：2017-3-24 上午9:31:27
 * @since 原华南台账相关类
 */
public class CaldjztyPeriodTableBean extends DataBean
{
  private SimpleDateFormat dtfmt = new SimpleDateFormat("yyyy-MM-dd");

  public int execute()
    throws MXException, RemoteException
  {
    MboRemote cal = this.app.getAppBean().getMbo();
    MboSetRemote lines = cal.getMboSet("workperiodjzty");

    String tylb = getMbo().getString("jztylb");

    Calendar cstart = Calendar.getInstance();
    cstart.setTime(getMbo().getDate("startdate"));

    Calendar cend = Calendar.getInstance();
    cend.setTime(getMbo().getDate("enddate"));

    long days = (cend.getTimeInMillis() - cstart.getTimeInMillis()) / 86400000L + 1L;
    if (days < 0L) {
      throw new MXApplicationException("calendar", "startenddate");
    }
    for (int i = 0; i < days; ++i)
    {
      lines.resetQbe();
      lines.setQbe("workdate", this.dtfmt.format(cstart.getTime()));
      lines.reset();
      if (lines.count() == 0) {
        MboRemote newline = lines.add();
        newline.setValue("workdate", cstart.getTime());
        newline.setValue("starttime", "00:00:00");
        newline.setValue("endtime", "00:00:00");
        newline.setValue("jztylb", tylb);
        newline.setValue("shiftnum", "白班");
        lines.save();
      }
      cstart.add(5, 1);
    }

    lines.resetQbe();
    lines.reset();
    this.app.getDataBean("table_workperiod").refreshTable();
    this.app.getDataBean("table_workperiod").reloadTable();
    return 1;
  }
}