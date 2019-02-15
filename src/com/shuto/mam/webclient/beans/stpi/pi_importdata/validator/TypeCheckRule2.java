package com.shuto.mam.webclient.beans.stpi.pi_importdata.validator;

import com.shuto.mam.webclient.beans.stpi.pi_importdata.ErrorMessage;

import cc.aicode.e2e.exception.ExcelContentInvalidException;
import cc.aicode.e2e.extension.ExcelRule;

public class TypeCheckRule2 implements ExcelRule<String> {

    public void check(int rowNumber,Object value, String columnName, String fieldName) throws ExcelContentInvalidException {
        
    	String val = (String) value;
    	int row = rowNumber+2;
    	if("".equals(val)){
    		ErrorMessage errorMessage = ErrorMessage.getInstance();
        	errorMessage.setResult(columnName+" 第 "+row+" 行，不能为空！");
    	}else if (!"巡检".equals(val)) {
        	ErrorMessage errorMessage = ErrorMessage.getInstance();
        	errorMessage.setResult(columnName+" 第 "+row+" 行，任务类型必须为巡检！");
        }
    }

    public String filter(int rowNumber,Object value, String columnName, String fieldName) {
        String val = (String) value;
        return val;
    }
    
}
