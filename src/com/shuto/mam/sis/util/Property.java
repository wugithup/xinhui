package com.shuto.mam.sis.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * 
com.shuto.mam.sis.util.Property 江苏
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月16日 下午5:45:36
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class Property {
	public String  getProperty(String properName) {
		Properties proper = new Properties();
	    InputStream read = getClass().getResourceAsStream("/shuto.properties");
	    try {
	      proper.load(read);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return proper.getProperty(properName);
	}
	
	public String getServerurl() {
		return getProperty("sis.server.url.crpnr");
	}
	
	public String getServerurl(String siteid) {
		String url = null;
		if ("HRNR000".equals(siteid)) {
			url = "sis.server.url.crpnr";
		} else if ("XZHP000".equals(siteid)) {
			url = "sis.server.url.crpxz";
		}
		return getProperty(url);
	}
}
