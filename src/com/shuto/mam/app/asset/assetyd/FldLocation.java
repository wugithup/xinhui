package com.shuto.mam.app.asset.assetyd;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.asset.assetyd.FldLocation 设备异动选择位置
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年5月10日 上午11:59:16
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldLocation extends MAXTableDomain {
	/**
	 * <p>
	 * 此构造方法必须添加，在此构造方法中实现页面中的放大镜选择Location表数据的功能
	 * 
	 * @param mbovalue
	 *            maximo封装好的获得属性值的类
	 */
	public FldLocation(MboValue mbv) throws MXException, RemoteException {
		super(mbv);
		// 此变量为locations表中获取数据时的限制条件
		String where = "STATUS='操作中' and orgid=:orgid  and siteid=:siteid";
		// 设置要获取数据表的关系
		setRelationship("LOCATIONS", where);
		// 设置条件
		setListCriteria(where);
		// 此方法将获取到的值设置到目标字段 中
		setLookupKeyMapInOrder(new String[] { "LOCATION" },
				new String[] { "LOCATION" });
	}
}
