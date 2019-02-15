/**
 * 参数字段类接口
 */
package com.shuto.mam.webservice.common.service;

import java.sql.Connection;

import org.dom4j.Element;

import com.shuto.mam.webservice.common.bean.Parameter;

/**
 * @author ITROBOT
 *
 */
public interface BaseParameter {
	/**
	 * 初始化参数值
	 * @param elm xml节点元素
	 * @param conn 数据库链接
	 * @return
	 */
	String initValue(Parameter p,Element elm,Connection conn);
}
