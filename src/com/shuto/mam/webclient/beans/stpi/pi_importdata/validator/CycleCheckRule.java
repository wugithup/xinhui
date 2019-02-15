package com.shuto.mam.webclient.beans.stpi.pi_importdata.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.shuto.mam.webclient.beans.stpi.pi_importdata.ErrorMessage;

import cc.aicode.e2e.exception.ExcelContentInvalidException;
import cc.aicode.e2e.extension.ExcelRule;

public class CycleCheckRule implements ExcelRule<String> {

    public void check(int rowNumber,Object value, String columnName, String fieldName) throws ExcelContentInvalidException {
        
    	String val = (String) value;
    	int row = rowNumber+2;
    	if(!"".equals(val)){
    		if (!match(val)) {
            	ErrorMessage errorMessage = ErrorMessage.getInstance();
            	errorMessage.setResult(columnName+" 第 "+row+" 行，周期必须是大于0的整数！");
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
	    Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
    }
}
