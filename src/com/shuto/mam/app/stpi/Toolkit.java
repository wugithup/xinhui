package com.shuto.mam.app.stpi;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * 此类中定义一些小的公用方法
 * @author mabin
 *
 */
public  class Toolkit {
	/**
	 * 判断一个对象是否在list中，如果不在则增加
	 * @param list 需要判断的list
	 * @param o 判断的对象
	 * @return 处理完成的list
	 */
	public static List<String> addList(List<String> list,String str){
		boolean bo = list.contains(str);
		if(!bo){
			list.add(str);
		}
		return list;
	}
	/**
	 * 将结果集的某一个字段拼成逗号分隔
	 * @param MboSet
	 * @param str
	 * @return ('','','')
	 * @throws MXException 
	 * @throws RemoteException 
	 */
	public static String getConcatLong(MboSetRemote MboSet,String str) throws RemoteException, MXException{
		String concat = null;
		for(int i=0;i<MboSet.count();i++){
			MboRemote mbo = MboSet.getMbo(i);
			Long value = mbo.getLong(str);
			if(i==0){
				concat="('"+value+"'";
			}else{
				concat=concat+",'"+value+"'";
			}
		}
		concat=concat+")";
		return concat;
	}
	/**
	 * 将结果集的某一个字段拼成逗号分隔
	 * @param MboSet
	 * @param str
	 * @return ('','','')
	 * @throws MXException 
	 * @throws RemoteException 
	 */
	public static String getConcat(MboSetRemote MboSet,String str) throws RemoteException, MXException{
		String concat = null;
		for(int i=0;i<MboSet.count();i++){
			MboRemote mbo = MboSet.getMbo(i);
			String value = mbo.getString(str);
			if(i==0){
				concat="('"+value+"'";
			}else{
				concat=concat+",'"+value+"'";
			}
		}
		concat=concat+")";
		return concat;
	}
	/**
	 * mboset转list
	 * @param mboSet
	 * @param field
	 * @return
	 * @throws RemoteException
	 * @throws MXException
	 */
	public static List<String> mboSetToList(MboSetRemote mboSet,String field) throws RemoteException, MXException{
		List<String> list = new ArrayList<String>();
		for(int i=0;i<mboSet.count();i++){
			MboRemote mbo = mboSet.getMbo(i);
			String fieldValue = mbo.getString(field);
			fieldValue = fieldValue.replaceAll(",", "");
			list.add(fieldValue);
		}
		return list;
	}
	/**
	 * 获取两个日期端内的间隔天数
	 * @param startDate
	 * @param endDate 
	 * @return
	 */
	public static long getDays(String startDate, String endDate) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(startDate);
			java.util.Date mydate = myFormatter.parse(endDate);
			day = (long)( mydate.getTime() -date.getTime()) / (24 * 60 * 60 * 1000) ;
		} catch (Exception e) {
			return 0;
		}
		return day ;
	}
	
	/**
	 * 时间排序
	 * @param endtArray
	 * @return
	 */
	public static Date[] dateSort(Date[] endtArray){
		for (int i = 0; i < endtArray.length; i++)
        {
            for (int j = i; j < endtArray.length; j++)
            {
                if (endtArray[i].compareTo(endtArray[j])>1 )
                {
                    Date temp = endtArray[i];
                    endtArray[i] = endtArray[j];
                    endtArray[j] = temp;
                }
            }
        }
		return endtArray;
	}
	/**
	 * dateTime加cycle天
	 * @param dateTime
	 * @param cycle
	 * @return
	 */
	public static Date getNextDayTime(Date dateTime,int cycle){
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(dateTime); 
	    calendar.add(Calendar.DATE,cycle);
	    dateTime=calendar.getTime();
		return dateTime;
	}
}
