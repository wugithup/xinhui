package com.shuto.mam.webservice.common.service;

/**
 * @Title: CommonService.java 
 * @Description: 通用接口   
 * @author itrobot 
 * @date 2017-6-12 下午02:07:01 
 * @version V1.0 
 */
public interface CommonService {
	/**
	 * 数据查询
	 * @Title: query  
	 * @Description: TODO  
	 * @param parameter
	 * @return String
	 * @throws
	 */
	public String query(String parameter);
	/**
	 * 数据上传
	 * @Title: upload  
	 * @Description: TODO  
	 * @param parameter
	 * @return String
	 * @throws
	 */
	public String upload(String parameter);
}
