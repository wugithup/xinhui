package com.shuto.mam.webservice.common.util;

import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.expiry.Expiry;

import com.shuto.mam.webservice.common.bean.Service;


/**
 * @Title: EhcacheUtil.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-13 下午09:03:10 
 * @version V1.0 
 */
public class EhcacheUtil {
	
	/**
	 * 接口服务缓存对象
	 */
	public static Cache<String, Service> serviceCache = null;
	
	public static <K, V> Cache<K, V> createCache(CacheConfig cacheConfig, Class<K> kClass, Class<V> vClass) {
        try {
            String cacheName = cacheConfig.getCacheName();
            int entrySize = cacheConfig.getEntrySize();
            int ttl = cacheConfig.getTimeToLiveSecond();
            
            Expiry ttlExpiry = Expirations.timeToLiveExpiration(new Duration(ttl, TimeUnit.SECONDS));
            CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache(
            		cacheName,  
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(  
                    		kClass, vClass,  
                            ResourcePoolsBuilder.heap(entrySize)).build()).build(true);  
            return cacheManager.getCache(cacheName, kClass, vClass);
        } catch (Exception e) {
            return null;
        }
    }
	
	/**
	 * 根据接口服务名从缓存中获取接口服务对象
	 * @Title: getServiceFromCache  
	 * @Description: TODO  
	 * @param servicename
	 * @return Service
	 * @throws
	 */
	public static Service getServiceFromCache(String servicename) {
		Service service = null;
		if (serviceCache != null) {
			service = serviceCache.get(servicename);
		}
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
		if (serviceCache == null) {
			//创建接口服务缓存对象;最大缓存10000个接口服务对象,生命周期1年(525600分钟)
			CacheConfig cacheConfig = new CacheConfig("SERVICECACHE",10000,525600);
			serviceCache = createCache(cacheConfig,String.class,Service.class);
		}
		serviceCache.put(service.getServiceName(), service);
	}
	
	 public static void main(String[] args) {  
	        CacheManager cacheManager = CacheManagerBuilder  
	                .newCacheManagerBuilder()  
	                .withCache(  
	                        "preConfigured",  
	                        CacheConfigurationBuilder.newCacheConfigurationBuilder(  
	                                Long.class, String.class,  
	                                ResourcePoolsBuilder.heap(100)).build()) 
	                .build(true);  
	  
	        Cache<Long, String> preConfigured = cacheManager.getCache(  
	                "preConfigured", Long.class, String.class);  
	  
	        Cache<Long, String> myCache = cacheManager.createCache(  
	                "myCache",  
	                CacheConfigurationBuilder.newCacheConfigurationBuilder(  
	                        Long.class, String.class,  
	                        ResourcePoolsBuilder.heap(100)).build());  
	          
	        preConfigured.put(2L, "hello Ehcache");  
	        String value1=preConfigured.get(2L);  
	        System.out.println(value1);  
	        myCache.put(1L, "da one!");  
	        String value = myCache.get(1L);  
	        System.out.println(value);  
	        cacheManager.close();  
	    }  
}
