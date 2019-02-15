package com.shuto.mam.webservice.common.util;

import java.util.HashMap;
import java.util.Map;

import com.shuto.mam.webservice.common.bean.Service;


/**
 * @Title: JcacheUtil.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-13 下午09:03:10 
 * @version V1.0 
 */
public class JcacheUtil {
	
	/**
	 * 接口服务缓存对象
	 */
	public static Map<String, Service> serviceCache = new HashMap<String, Service>();
	
	/**
	 * 根据接口服务名从缓存中获取接口服务对象
	 * @Title: getServiceFromCache  
	 * @Description: TODO  
	 * @param servicename
	 * @return Service
	 * @throws
	 */
	public static Service getServiceFromCache(String servicename) {
		Service service = serviceCache.get(servicename);
		return service;
	}
	
	/**
	 * 将接口服务对象加载到缓存中
	 * @Title: putServiceToCache  
	 * @Description: TODO  
	 * @param service void
	 * @throws
	 */
	public static void putServiceToCache(Service service) {
		serviceCache.put(service.getServiceName(), service);
	}
}
