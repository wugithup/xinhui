/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.util;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @author SumMer
 * @version V1.0
 * @Title: CronPmUtil
 * @Package com.shuto.mam.app.util
 * @Description: 定期工作工具类
 * @date 2018-11-30 0030 13:00
 */
public class CronPmUtil {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.crontask");

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Calendar today = null;

    public CronPmUtil() {

        try {
            this.today = Calendar.getInstance();
            this.today.setTime(this.dateFormat.parse(this.dateFormat.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Calendar getNextdate(MboRemote pm) {

        Calendar nextday = Calendar.getInstance();
        try {
            nextday.setTime(this.today.getTime());
            //频率单位
            String frequnit = pm.getString("FREQUNIT");
            //指定日期
            String fixedday = pm.getString("FIXEDDAY");
            //频率
            String frequency = pm.getString("FREQUENCY");
            switch (frequnit) {
                case "固定日期":
                    if (!fixedday.isEmpty()) {
                        MX_LOGGER.info("======>固定日期<=====");
                        nextday = getNextdateFixedday(pm);
                    } else {
                        MX_LOGGER.info(">>>>>error - don't config fixedday.");
                        nextday = null;
                    }
                    break;
                case "固定星期":
                    if (!fixedday.isEmpty()) {
                        MX_LOGGER.info("======>固定星期<=====");
                        nextday = getNextdateWeekday(pm);
                    } else {
                        MX_LOGGER.info(" >>>>>error - doesn't config fixedweekday");
                        nextday = null;
                    }
                    break;
                case "天":
                    if (!frequency.isEmpty()) {
                        MX_LOGGER.info("======>天<=====");
                        nextday = getNextdateDays(pm);
                    } else {
                        MX_LOGGER.info(" >>>>>error - doesn't config fixedweekday");
                        nextday = null;
                    }
                    break;
                case "周":
                    if (!frequency.isEmpty()) {
                        MX_LOGGER.info("======>周<=====");
                        nextday = getNextdateWeek(pm);
                    } else {
                        MX_LOGGER.info(" >>>>>error - doesn't config fixedweekday");
                        nextday = null;
                    }
                    break;
                case "月":
                    if (!frequency.isEmpty()) {
                        MX_LOGGER.info("======>月<=====");
                        nextday = getNextdateMonth(pm);
                    } else {
                        MX_LOGGER.info(" >>>>>error - doesn't config fixedweekday");
                        nextday = null;
                    }
                    break;
                case "年":
                    if (!frequency.isEmpty()) {
                        MX_LOGGER.info("======>年<=====");
                        nextday = getNextdateYear(pm);
                    } else {
                        MX_LOGGER.info(" >>>>>error - doesn't config fixedweekday");
                        nextday = null;
                    }
                    break;
                default:
                    MX_LOGGER.info("switch default没有任何的方法");
            }
            MX_LOGGER.info(" >>>>>nextdate=" +
                    ((nextday == null) ? "" : this.dateFormat.format(nextday.getTime())));

        } catch (Exception e) {
            MX_LOGGER.error(e);
        }
        return nextday;
    }

    /**
     * 获取固定日期
     */
    private Calendar getNextdateFixedday(MboRemote pm) throws RemoteException, MXException {

        MX_LOGGER.info("======>get固定日期<=====");
        //指定日期为空，不更新下一个执行日期
        Calendar nextday = getCalendar(pm);
        do {
            nextday = newNextdateFixedday(pm, nextday);
            try {
                nextday.setTime(this.dateFormat.parse(this.dateFormat.format(nextday.getTime())));
            } catch (ParseException e) {
                MX_LOGGER.error(e);
                e.printStackTrace();
            }
        } while (nextday.getTime().getTime() <= this.today.getTime().getTime());
        return nextday;
    }

    /**
     * 设置固定日期
     */
    private Calendar newNextdateFixedday(MboRemote pm, Calendar curdate)
            throws RemoteException, MXException {

        MX_LOGGER.info("======>设置固定日期<=====");
        Calendar nextday = Calendar.getInstance();
        nextday.setTime(curdate.getTime());
        boolean flag = false;
        StringTokenizer st = new StringTokenizer(pm.getString("FIXEDDAY"), ",");
        while (st.hasMoreTokens()) {
            String day = st.nextToken();
            if (Integer.valueOf(day) > curdate.get(Calendar.DATE)) {
                flag = true;
            }
            if (!flag) {
                continue;
            }
            nextday.set(Calendar.DATE, Integer.valueOf(day));
            break;
        }
        if (!flag) {
            //下一个月
            nextday.add(Calendar.MONTH, 1);
            st = new StringTokenizer(pm.getString("FIXEDDAY"), ",");
            String defday = st.nextToken();
            nextday.set(Calendar.DATE, Integer.valueOf(defday));
        }
        MX_LOGGER.info(" >>>>>newNextdateFixedday=" + this.dateFormat.format(nextday.getTime()));
        return nextday;
    }

    /**
     * 获取周
     */
    private Calendar getNextdateWeekday(MboRemote pm) throws RemoteException, MXException {

        MX_LOGGER.info("======>get周<=====");
        //指定日期为空，不更新下一个执行日期
        Calendar nextday = getCalendar(pm);
        do {
            nextday = newNextdateWeekday(pm, nextday);
            try {
                nextday.setTime(this.dateFormat.parse(this.dateFormat.format(nextday.getTime())));
            } catch (ParseException e) {
                MX_LOGGER.error(e);
            }
        } while (nextday.getTime().getTime() <= this.today.getTime().getTime());
        return nextday;
    }

    /**
     * 设置周
     */
    private Calendar newNextdateWeekday(MboRemote pm, Calendar curdate)
            throws RemoteException, MXException {

        MX_LOGGER.info("======>设置周<=====");
        Calendar nextday = Calendar.getInstance();
        nextday.setTime(curdate.getTime());
        boolean flag = false;
        StringTokenizer st = new StringTokenizer(pm.getString("FIXEDDAY"), ",");
        while (st.hasMoreTokens()) {
            String weekday = st.nextToken();
            if (Integer.valueOf(weekday) > nextday.get(Calendar.DAY_OF_WEEK) - 1) {
                flag = true;
            }
            if (flag) {
                if (Integer.valueOf(weekday) < 7) {
                    nextday.set(Calendar.DAY_OF_WEEK, Integer.valueOf(weekday) + 1);
                    break;
                }
                nextday.add(Calendar.DATE, 7);
                nextday.set(Calendar.DAY_OF_WEEK, 1);
                break;
            }
        }
        if (!flag) {
            //下一周
            nextday.add(Calendar.DAY_OF_MONTH, 7);
            st = new StringTokenizer(pm.getString("FIXEDDAY"), ",");
            String weekday = st.nextToken();
            if (Integer.valueOf(weekday) < 7) {
                nextday.set(Calendar.DAY_OF_WEEK, Integer.valueOf(weekday) + 1);
            } else {
                nextday.add(Calendar.DATE, 7);
                nextday.set(Calendar.DAY_OF_WEEK, 1);
            }
        }
        MX_LOGGER.info(" >>>>>newNextdateWeekday=" + this.dateFormat.format(nextday.getTime()));
        return nextday;
    }

    /**
     * 天
     */
    private Calendar getNextdateDays(MboRemote pm) throws RemoteException, MXException {

        MX_LOGGER.info("======>get天<=====");
        Calendar nextday = getCalendar(pm);
        if (nextday == null) {
            return null;
        }
        do {
            nextday.add(Calendar.DATE, pm.getInt("FREQUENCY"));
            try {
                nextday.setTime(this.dateFormat.parse(this.dateFormat.format(nextday.getTime())));
            } catch (ParseException e1) {
                MX_LOGGER.error(e1);
            }
        } while (nextday.getTime().getTime() <= this.today.getTime().getTime());
        return nextday;
    }

    /**
     * 周
     */
    private Calendar getNextdateWeek(MboRemote pm) throws RemoteException, MXException {

        MX_LOGGER.info("======>get周<=====");
        Calendar nextday = getCalendar(pm);
        if (nextday == null) {
            return null;
        }
        do {
            nextday.add(Calendar.DATE, pm.getInt("FREQUENCY") * 7);
            try {
                nextday.setTime(this.dateFormat.parse(this.dateFormat.format(nextday.getTime())));
            } catch (ParseException e) {
                MX_LOGGER.error(e);
            }
        } while (nextday.getTime().getTime() <= this.today.getTime().getTime());
        return nextday;
    }

    /**
     * 月
     */
    private Calendar getNextdateMonth(MboRemote pm) throws RemoteException, MXException {

        MX_LOGGER.info("======>get月<=====");
        Calendar nextday = getCalendar(pm);
        if (nextday == null) {
            return null;
        }
        do {
            nextday.add(Calendar.MONTH, pm.getInt("FREQUENCY"));
            try {
                nextday.setTime(this.dateFormat.parse(this.dateFormat.format(nextday.getTime())));
            } catch (ParseException e) {
                MX_LOGGER.error(e);
                e.printStackTrace();
            }
        } while (nextday.getTime().getTime() <= this.today.getTime().getTime());
        return nextday;
    }

    /**
     * 年
     *
     * @param pm
     * @return
     * @throws RemoteException
     * @throws MXException
     */
    private Calendar getNextdateYear(MboRemote pm) throws RemoteException, MXException {

        MX_LOGGER.info("======>get年<=====");
        Calendar nextday = getCalendar(pm);
        if (nextday == null) {
            return null;
        }
        do {
            nextday.add(Calendar.YEAR, pm.getInt("FREQUENCY"));
            try {
                nextday.setTime(this.dateFormat.parse(this.dateFormat.format(nextday.getTime())));
            } catch (ParseException e) {
                MX_LOGGER.info(e);
                e.printStackTrace();
            }
        } while (nextday.getTime().getTime() <= this.today.getTime().getTime());
        return nextday;
    }

    private Calendar getCalendar(MboRemote pm) throws MXException, RemoteException {

        if (pm.isNull("NEXTDATE")) {
            return null;
        }
        // if (pm.isNull("FREQUENCY") || pm.getInt("FREQUENCY") <= 0) {
        //     return null;
        // }
        Calendar nextday = Calendar.getInstance();
        nextday.setTime(pm.getDate("NEXTDATE"));
        return nextday;
    }
}
