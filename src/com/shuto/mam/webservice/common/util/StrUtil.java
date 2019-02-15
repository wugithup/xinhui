package com.shuto.mam.webservice.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: StrUtil.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-22 下午10:30:52 
 * @version V1.0 
 */
public class StrUtil {
	/**
	 * 判断字符串是否数字格式
	 * @Title: isNumeric  
	 * @Description: TODO  
	 * @param str
	 * @return boolean
	 * @throws
	 */
	public static boolean isNumeric(String str){ 
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
		    return false; 
		} 
		return true; 
	}
	
	/**
	 * 判断字符串是否日期格式
	 * @Title: isDate  
	 * @Description: TODO  
	 * @param str
	 * @return boolean
	 * @throws
	 */
	public static boolean isDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断字符串是否日期时间格式
	 * @Title: isDateTime  
	 * @Description: TODO  
	 * @param str
	 * @return boolean
	 * @throws
	 */
	public static boolean isDateTime(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			return false;
		} 
		return true;
	}
}
