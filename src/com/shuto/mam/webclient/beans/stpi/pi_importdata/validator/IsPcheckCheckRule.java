package com.shuto.mam.webclient.beans.stpi.pi_importdata.validator;

import com.shuto.mam.webclient.beans.stpi.pi_importdata.ErrorMessage;

import cc.aicode.e2e.exception.ExcelContentInvalidException;
import cc.aicode.e2e.extension.ExcelRule;

public class IsPcheckCheckRule implements ExcelRule<String> {

    public void check(int rowNumber,Object value, String columnName, String fieldName) throws ExcelContentInvalidException {
        
    	String val = (String) value;
    	int row = rowNumber+2;
    	if (!"是".equals(val)&&!"否".equals(val)) {
        	ErrorMessage errorMessage = ErrorMessage.getInstance();
        	errorMessage.setResult(columnName+" 第 "+row+" 行，只能填写是和否！");
        }
    }

    public String filter(int rowNumber,Object value, String columnName, String fieldName) {
        String val = (String) value;
        if("是".equals(val)){
        	val="1";
        }else if("否".equals(val)){
        	val="0";
        }
        return val;
    }
    
}
