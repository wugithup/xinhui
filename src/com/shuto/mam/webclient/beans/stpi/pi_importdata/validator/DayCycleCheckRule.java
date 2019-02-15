package com.shuto.mam.webclient.beans.stpi.pi_importdata.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shuto.mam.webclient.beans.stpi.pi_importdata.ErrorMessage;

import cc.aicode.e2e.exception.ExcelContentInvalidException;
import cc.aicode.e2e.extension.ExcelRule;

public class DayCycleCheckRule implements ExcelRule<String> {

    public void check(int rowNumber,Object value, String columnName, String fieldName) throws ExcelContentInvalidException {
        
    	String val = (String) value;
    	int row = rowNumber+2;
    	if(!"".equals(val)){
    		if (!isRight(val)) {
            	ErrorMessage errorMessage = ErrorMessage.getInstance();
            	errorMessage.setResult(columnName+" 第 "+row+" 行，格式不正确！");
            }
    	}
    }

    public String filter(int rowNumber,Object value, String columnName, String fieldName) {
        String val = (String) value;
        return val;
    }
    
    /**
    * @param str
    * 要匹配的字符串
    * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
    */
    private static boolean match(String str) {
	    Pattern pattern = Pattern.compile("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
    }
    
    private boolean isRight(String str) {
		String[] timeslot = str.split(",");
		for (int i = 0; i < timeslot.length; i++) {
			String[] times = timeslot[i].split("\\|");
			for (int j = 0; j < times.length; j++) {
				if(!DayCycleCheckRule.match(times[j])){
					return false;
				}
			}
		}
		return true;
	}
    
}
