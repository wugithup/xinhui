package com.shuto.mam.webservice.common.service;

import com.shuto.mam.webservice.common.bean.Service;

/**
 * @Title: BaseService.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-14 下午02:53:28 
 * @version V1.0 
 */
public interface BaseService {
	/**
	 * 执行接口服务
	 * @Title: execute  
	 * @Description: TODO  
	 * @param service
	 * @return String
	 * @throws
	 */
	String execute(Service service);
}
