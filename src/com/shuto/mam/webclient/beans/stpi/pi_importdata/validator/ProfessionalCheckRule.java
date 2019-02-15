package com.shuto.mam.webclient.beans.stpi.pi_importdata.validator;

import java.sql.SQLException;

import com.shuto.mam.crontask.stpi.dbutils.MaximoUtils;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.ErrorMessage;

import cc.aicode.e2e.exception.ExcelContentInvalidException;
import cc.aicode.e2e.extension.ExcelRule;

public class ProfessionalCheckRule implements ExcelRule<String> {

    public void check(int rowNumber,Object value, String columnName, String fieldName) throws ExcelContentInvalidException {
        
    	String val = (String) value;
    	int row = rowNumber+2;
    	if("".equals(val)){
    		ErrorMessage errorMessage = ErrorMessage.getInstance();
        	errorMessage.setResult(columnName+" 第 "+row+" 行，不能为空！");
    	}else if (!isStandard(val)) {
        	ErrorMessage errorMessage = ErrorMessage.getInstance();
        	errorMessage.setResult(columnName+" 第 "+row+" 行，系统中不存在 "+val+"！");
        }
    }

    public String filter(int rowNumber,Object value, String columnName, String fieldName) {
        String val = (String) value;
        return val;
    }
    
    private boolean isStandard(String professional){
    	String sql = "select value from alndomain where domainid = 'PI_PROFESSIONAL' and value = '"+professional+"'";
    	MaximoUtils maximoUtils = new MaximoUtils();
    	maximoUtils.getMaximoConn();
    	boolean flag = false;
		try {
			flag = maximoUtils.isHasRecord(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return flag;
    }
}
