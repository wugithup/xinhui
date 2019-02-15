package com.shuto.mam.webservice.common.util;

/**
 * @Title: CacheConfig.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-13 下午10:27:10 
 * @version V1.0 
 */
public class CacheConfig {
	/**
	 * 缓存名
	 */
	private String cacheName;
	/**
	 * 缓存对象数
	 */
    private int entrySize;
    /**
     * 缓存生命周期(分钟)
     */
    private int timeToLiveSecond;

    public CacheConfig(String cacheName, int entrySize, int timeToLive) {
        this.cacheName = cacheName;
        this.entrySize = entrySize;
        this.timeToLiveSecond = timeToLive;
    }

    public String getCacheName() { 
    	return this.cacheName; 
    }
    public int getEntrySize() { 
    	return this.entrySize; 
    }
    public int getTimeToLiveSecond() { 
    	return this.timeToLiveSecond; 
    }
}
