/**
 * 
 */
package com.shuto.mam.webservice.common.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * @author ITROBOT
 *
 */
public class JsonUtil {
	
	/**
	 * 判断是否JSON格式
	 * @Title: isJson  
	 * @Description: TODO  
	 * @param value
	 * @return boolean
	 * @throws
	 */
	public static boolean isJson(String value) { 
		try { 
			JSONObject.parseObject(value);
		} catch (JSONException e) { 
			return false; 
		} 
		return true; 
	}
	
	/**
	 * JSON转MAP
	 * @param json
	 * @return
	 */
	public static Map<String,Object> json2Map(String json){
		return JSON.parseObject(json, Map.class);
	}

	/**
	 * 对象转JSON
	 * @param obj
	 * @return
	 */
	public static String obj2Json(Object obj){
		return JSON.toJSONString(obj);
	}
}
