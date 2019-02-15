package com.shuto.mam.webclient.beans.stpi.pi_importdata.validator;

import com.shuto.mam.webclient.beans.stpi.pi_importdata.ErrorMessage;

import cc.aicode.e2e.exception.ExcelContentInvalidException;
import cc.aicode.e2e.extension.ExcelRule;

public class EmptyAndSizeCheckRule implements ExcelRule<String> {

    public void check(int rowNumber,Object value, String columnName, String fieldName) throws ExcelContentInvalidException {
        
    	String val = (String) value;
    	int row = rowNumber+2;
    	if("".equals(val)){
    		ErrorMessage errorMessage = ErrorMessage.getInstance();
        	errorMessage.setResult(columnName+" 第 "+row+" 行，不能为空！");
    	}else if (isTooLong(val, fieldName)) {
        	ErrorMessage errorMessage = ErrorMessage.getInstance();
        	errorMessage.setResult(columnName+" 第 "+row+" 行，内容超长！");
        }
    }

    public String filter(int rowNumber,Object value, String columnName, String fieldName) {
        String val = (String) value;
        return val;
    }
    
    private boolean isTooLong(String val,String fieldName){
    	boolean flag = false;
    	if(val.length() > 250&&"taskcfgName".equals(fieldName)){
    		flag = true;
    	}if(val.length() > 250&&"areaName".equals(fieldName)){
    		flag = true;
    	}if(val.length() > 30&&"rfidCode".equals(fieldName)){
    		flag = true;
    	}if(val.length() > 15&&"deviceNo".equals(fieldName)){
    		flag = true;
    	}if(val.length() > 200&&"deviceName".equals(fieldName)){
    		flag = true;
    	}if(val.length() > 15&&"location".equals(fieldName)){
    		flag = true;
    	}if(val.length() > 200&&"devicePartName".equals(fieldName)){
    		flag = true;
    	}if(val.length() > 200&&"checkProject".equals(fieldName)){
    		flag = true;
    	}if(val.length() > 250&&"pointNorm".equals(fieldName)){
    		flag = true;
    	}
    	return flag;
    }
}
