package com.shuto.mam.webservice.ebs.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.shuto.mam.webservice.ebs.bean.OrgMap;


/**
 * @Title: JcacheUtil.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-13 下午09:03:10 
 * @version V1.0 
 */
public class JcacheUtil {
	
	/**
	 * 接口组织缓存对象
	 */
	public static Map<String, OrgMap> interfaceOrgCache = new HashMap<String, OrgMap>();
	
	/**
	 * 根据接口组织ID,接口站点ID从缓存中 获取接口组织映射信息
	 * @param interorg
	 * @param intersite
	 * @param intersite
	 * @return
	 */
	public static OrgMap getOrgFromCache(String interorg,String intersite,Connection conn) {
		OrgMap orgmap = interfaceOrgCache.get(interorg+"|"+intersite);
		if (orgmap == null) {
			orgmap = new OrgMap();
			PreparedStatement spstatement = null;
			ResultSet rs = null;
			String sql = "select maxorg,maxsite,interorg,intersite,interfaceorgtype,partnerid from orgmap where partnerid='EBS' and interorg=? and intersite=?";
			try {
				spstatement = conn.prepareStatement(sql);
				spstatement.setString(1, interorg);
				spstatement.setString(2, intersite);
				rs = spstatement.executeQuery();
				while (rs.next()) {
					orgmap.setMaxorg(rs.getString("maxorg"));
					orgmap.setMaxsite(rs.getString("maxsite"));
					orgmap.setInterorg(rs.getString("interorg"));
					orgmap.setIntersite(rs.getString("intersite"));
					orgmap.setInterfaceorgtype(rs.getString("interfaceorgtype"));
					orgmap.setPartnerid(rs.getString("partnerid"));
				}
				putOrgToCache(orgmap);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (spstatement != null) {
						spstatement.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return orgmap;
	}
	
	/**
	 * 将接口组织映射信息加载到缓存中
	 * @param orgmap
	 */
	public static void putOrgToCache(OrgMap orgmap) {
		interfaceOrgCache.put(orgmap.getInterorg()+"|"+orgmap.getIntersite(), orgmap);
	}
}
