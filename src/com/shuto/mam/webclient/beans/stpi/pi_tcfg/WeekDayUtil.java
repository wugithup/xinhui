package com.shuto.mam.webclient.beans.stpi.pi_tcfg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 获取某一时间段特定星期几的日期
 * @author finder.zhou
 */
public class WeekDayUtil {
	public static void main(String[] args) {
//        getDates("2013-04-01", "2014-04-14","星期一|星期二|星期日");
		Date[] dateList = getDates("2013-04-01", "2013-07-01", 7);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0;i<dateList.length;i++){
			System.out.println(dateFormat.format(dateList[i]));
		}
		
    }

	/**
	 * 获取某一时间段特定周期的日期
	 * @param dateFrom
	 * @param dateEnd
	 * @param cycle
	 * @return
	 */
	 public static Date[] getDates(String dateFrom, String dateEnd, int cycle) {
		 List<Date> dateList = new ArrayList<Date>();
		 SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		 Calendar ca = Calendar.getInstance();
		 try {
			 Date startDate = dateFormat.parse(dateFrom);
			 Date endDate = dateFormat.parse(dateEnd);
			 ca.setTime(startDate);
			 while(startDate.compareTo(endDate)<=0){
				 dateList.add(startDate);
				 ca.add(Calendar.DATE, cycle);
				 startDate = ca.getTime();
				}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	        Date[] dateArray = new Date[dateList.size()];
	        dateList.toArray(dateArray);
	        return dateArray;
	    }
	
	
	/**
	 * 获取某一时间段特定星期几的日期
	 * @param dateFrom 开始时间
	 * @param dateEnd 结束时间
	 * @param weekDays 星期
	 * @return 返回时间数组
	 */
    public static Date[] getDates(String dateFrom, String dateEnd, String weekDays) {
        long time = 1l;
        long perDayMilSec = 24 * 60 * 60 * 1000;
        List<Date> dateList = new ArrayList<Date>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //需要查询的星期系数
        String strWeekNumber = weekForNum(weekDays);
        try {
			dateFrom = sdf.format(sdf.parse(dateFrom).getTime() - perDayMilSec);
			while (true) {
					time = sdf.parse(dateFrom).getTime();
					time = time + perDayMilSec;
					Date date = new Date(time);
					dateFrom = sdf.format(date);
					if (dateFrom.compareTo(dateEnd) <= 0) {
						//查询的某一时间的星期系数
						Integer weekDay = dayForWeek(date);                    
						//判断当期日期的星期系数是否是需要查询的
						if (strWeekNumber.indexOf(weekDay.toString())!=-1) {
							dateList.add(date);
						}
					} else {
						break;
					}
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
        Date[] dateArray = new Date[dateList.size()];
        dateList.toArray(dateArray);
        return dateArray;
    }

    //等到当期时间的周系数。星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
    public static Integer dayForWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 得到对应星期的系数  星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
     * @param weekDays 星期格式  星期一|星期二
     */
    public static String weekForNum(String weekDays){
    	//返回结果为组合的星期系数
    	String weekNumber = "";
    	//解析传入的星期
    	if(weekDays.indexOf("|")!=-1){//多个星期数
    		String []strWeeks = weekDays.split("\\|");
    		for(int i=0;i<strWeeks.length;i++){
    			weekNumber = weekNumber + "" + getWeekNum(strWeeks[i]).toString();
    		}
    	}else{//一个星期数
    		weekNumber = getWeekNum(weekDays).toString();
    	}
    	
    	return weekNumber;
    	
    }
    
    //将星期转换为对应的系数  星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
    public static Integer getWeekNum(String strWeek){
    	Integer number = 1;//默认为星期日
    	if("星期日".equals(strWeek)){
    		number = 1;
    	}else if("星期一".equals(strWeek)){
    		number = 2;
    	}else if("星期二".equals(strWeek)){
    		number = 3;
    	}else if("星期三".equals(strWeek)){
    		number = 4;
    	}else if("星期四".equals(strWeek)){
    		number = 5;
    	}else if("星期五".equals(strWeek)){
    		number = 6;
    	}else if("星期六".equals(strWeek)){
    		number = 7;
    	}else{
    		number = 1;
    	}
    	return number;
    }

}