package com.shuto.mam.webclient.beans.stpi.pi_importdata.validator;

import java.sql.SQLException;

import com.shuto.mam.crontask.stpi.dbutils.MaximoUtils;
import com.shuto.mam.webclient.beans.stpi.pi_importdata.ErrorMessage;

import cc.aicode.e2e.exception.ExcelContentInvalidException;
import cc.aicode.e2e.extension.ExcelRule;

public class DataFormatNoCheckRule implements ExcelRule<String> {

    public void check(int rowNumber,Object value, String columnName, String fieldName) throws ExcelContentInvalidException {
        
    	String val = (String) value;
    	int row = rowNumber+2;
    	if(!"".equals(val)){
    		if (!isStandard(val)) {
            	ErrorMessage errorMessage = ErrorMessage.getInstance();
            	errorMessage.setResult(columnName+" 第 "+row+" 行，系统中不存在 "+val+"！");
            }
    	}
    }

    public String filter(int rowNumber,Object value, String columnName, String fieldName) {
        String val = (String) value;
        return val;
    }
    
    private boolean isStandard(String val){
    	String sql = "select NO from ST_PI_DATAFORMAT where no = '"+val+"'";
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
