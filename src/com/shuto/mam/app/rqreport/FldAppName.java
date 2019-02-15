/**   
* @Title: FldAppName.java 
* @Package com.shuto.mam.app.rqreport 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lull lull@shuto.cn
* @date 2017年5月31日 上午10:34:19 
* @version V1.0.0
*/
package com.shuto.mam.app.rqreport;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;

/**
 * @ClassName: FldAppName
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年5月31日 上午10:34:19
 * 
 */
/// FldUserGroup
public class FldAppName extends MAXTableDomain {
	/// com.shuto.mam.app.rqreport.FldAppName
	public FldAppName(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
		setRelationship("MAXAPPS", "");
		String strFrom[] = { "APP" };
		String strTo[] = { "APPNAME" };
		setLookupKeyMapInOrder(strTo, strFrom);// 选中后将strFrom的列内容写到strTo中

	}

}
