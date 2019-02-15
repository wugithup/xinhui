/**  
 * <p> 标准操作票编号Fld类
 * @author       WangJX  WangJX@shuto.cn
 * @date         2012-8-1 下午04:17:49
 * 
 * @Copyright:   2012 Shuto版权所有
 * @version      V1.0  
 */
package com.shuto.mam.app.base;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 脚手架管理         JSJGL
 com.shuto.mam.app.base.FIdWorkorderName 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午9:07:53
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */

public class FIdWorkorderName extends MAXTableDomain {
	/**
	 * <p>构造方法
	 * @param 此参数为mbv
	 * @throws MXException
	 * @throws RemoteException
	 */

	public FIdWorkorderName(MboValue mbv) throws MXException, RemoteException {
		super(mbv);
		// 定义地点
		String siteid = getMboValue("siteid").getString();
		// 定义where条件
		String where = "worktype='工作票' and siteid=:siteid";
		// 根据where条件获取数据表名｜关系
		setRelationship("workorder", where);
		// 过滤条件
		setListCriteria(where);
			// 目标字段名变量
			String[] strTo = new String[] { "GZPBH"};
			// 获取值 字段名变更
			String[] strFrom = new String[] { "S_WOTKNUM"};
			// 此方法将获取到的值设置到目标字段 中
			setLookupKeyMapInOrder(strTo, strFrom);
		
		}
}
