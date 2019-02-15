package com.shuto.mam.app.reports;

import java.util.Calendar;
import java.util.Date;;

/**  
com.shuto.mam.app.reports.ZhibiaoTuuyou 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 上午11:07:49
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class ZhibiaoTuuyou {
	
	public Date formatDate(Date statDate,String type,String statisticsType){
//		java.text.SimpleDateFormat bartDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss w W");
		Calendar cal = Calendar.getInstance();
		cal.setTime(statDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if (type.equals("日")) {
			// 保持默認即可
			if (statisticsType==null||"".equals(statisticsType)||"直接取值".equals(statisticsType)) {
				
			}else if("昨天/上月/去年".equals(statisticsType)){
				cal.add(Calendar.DATE, -1);
			}else if("同期".equals(statisticsType)){
				cal.add(Calendar.YEAR, -1);
			}
		} else if (type.equals("月")) {
			if (statisticsType==null||"".equals(statisticsType)||"直接取值".equals(statisticsType)) {
				/* 設置月末 */
				// 日，设为一号
				cal.set(Calendar.DATE, 1);
				// 月份加一，得到下个月的一号
				cal.add(Calendar.MONTH, 1);
				// 下一个月减一为本月最后一天
				cal.add(Calendar.DATE, -1);
			}else if("昨天/上月/去年".equals(statisticsType)){
				// 日，设为一号
				cal.set(Calendar.DATE, 1);
				// 得到上月月末
				cal.add(Calendar.DATE, -1);
			}else if("同期".equals(statisticsType)){
				cal.add(Calendar.YEAR, -1);
			}
		} else if (type.equals("年")) {
			if (statisticsType==null||"".equals(statisticsType)||"直接取值".equals(statisticsType)) {
				/* 設置月末 */
				cal.set(Calendar.DATE, 1);
				cal.set(Calendar.MONTH, 0);
				cal.add(Calendar.DATE, -1);
				cal.add(Calendar.YEAR, 1);
			}else if("昨天/上月/去年".equals(statisticsType)){
				/* 設置月末 */
				cal.set(Calendar.DATE, 1);
				cal.set(Calendar.MONTH, 0);
				cal.add(Calendar.DATE, -1);
			}
		}
		//				System.out.println(bartDateFormat.format(statDate));
		//				System.out.println(bartDateFormat.format(cal.getTime()));
		//				System.out.println(type);
		return cal.getTime();
	}

}
