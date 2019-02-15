package com.shuto.mam.webclient.beans.stpi.pi_tcfg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalenderUtils
{
  private static String getWeekDay(Calendar c)
  {
    if (c == null) {
      return "星期一";
    }
    if (2 == c.get(7)) {
      return "星期一";
    }
    if (3 == c.get(7)) {
      return "星期二";
    }
    if (4 == c.get(7)) {
      return "星期三";
    }
    if (5 == c.get(7)) {
      return "星期四";
    }
    if (6 == c.get(7)) {
      return "星期五";
    }
    if (7 == c.get(7)) {
      return "星期六";
    }
    if (1 == c.get(7)) {
      return "星期日";
    }
    return "星期一";
  }

  private static List<String> getAllDate(String startTime, String endTime, String dayWeek, int cycle)
  {
    List list = new ArrayList();
    try {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      long start = df.parse(startTime).getTime();
      long end = df.parse(endTime).getTime();
      if ("其它".equals(dayWeek)) {
        Calendar a = Calendar.getInstance();
        a.setTimeInMillis(start);
        Calendar b = Calendar.getInstance();
        b.setTimeInMillis(end);
        while (start <= end) {
          list.add(df.format(a.getTime()));
          a.add(5, cycle);
          start = a.getTimeInMillis();
        }
      }
      else {
        for (long i = start; i < end + 86400000L; i += 86400000L) {
          Date date = new Date();
          date.setTime(i);
          String da = df.format(date);
          Calendar c = Calendar.getInstance();
          c.setTime(date);
          if ("周一至周五".equals(dayWeek)) {
            if ((getWeekDay(c).equals("星期六")) || 
              (getWeekDay(c).equals("星期日"))) continue;
            list.add(da);
          }
          else if ("每天".equals(dayWeek)) {
            list.add(da);
          }
          else if (getWeekDay(c).equals(dayWeek)) {
            list.add(da);
          }
        }
      }
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }

    return list;
  }

  public static List<Map<String, String>> getDateList(String startDateTime, String endDateTime, String dayWeek, int cycle)
    throws Exception
  {
    List allDates = null;
    List dates = new ArrayList();
    Calendar c = Calendar.getInstance();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    String beginDate = sdf2.format(sdf.parse(startDateTime));
    String endDate = sdf2.format(sdf.parse(endDateTime));

    String beginTime = " 06:00:00";

    String endTime = " 18:00:00";

    allDates = getAllDate(beginDate, endDate, dayWeek, cycle);

    for (int x = 0; x < allDates.size(); x++) {
      c.setTime(sdf2.parse((String)allDates.get(x)));
      Map date = new HashMap();
      date.put("startTime", ((String)allDates.get(x)).concat(beginTime));
      date.put("endTime", ((String)allDates.get(x)).concat(endTime));
      dates.add(date);
    }

    return dates;
  }

  public static List<Map<String, String>> getTimePeriod(int severalTimes, String startDateTime, String endDateTime, String dayWeek)
    throws Exception
  {
    String[][] periods = { { "0:00:00", "8:00:00" }, 
      { "8:00:00", "16:00:00" }, { "16:00:00", "0:00:00" } };

    int hour = 24 / periods.length;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    String beginDate = sdf2.format(sdf.parse(startDateTime));
    String endDate = sdf2.format(sdf.parse(endDateTime));

    List allDate = getAllDate(beginDate, endDate, dayWeek, 0);

    List timePeriods = new ArrayList();

    Calendar c = Calendar.getInstance();
    c.setTime(sdf2.parse((String)allDate.get(0)));
    c.set(11, 0);
    c.set(12, 0);
    c.set(13, 0);
    String startDate = "";
    for (int x = 0; x < allDate.size(); x++) {
      for (int i = 0; i < periods.length; i++) {
        startDate = sdf2.format(c.getTime());
        if (i == 2) {
          c.add(5, 1);
        }
        for (int j = 0; j < severalTimes; j++) {
          Map dateMap = new HashMap();
          dateMap.put("startTime", startDate + " " + periods[i][0]);
          dateMap.put("endTime", sdf2.format(c.getTime()) + " " + 
            periods[i][1]);
          timePeriods.add(dateMap);
        }
      }
    }

    return timePeriods;
  }
}
