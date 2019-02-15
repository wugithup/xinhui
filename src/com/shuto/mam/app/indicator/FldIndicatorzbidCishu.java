package com.shuto.mam.app.indicator;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;


/**  
com.shuto.mam.app.indicator.FldIndicatorzbidCishu 华东大区 阜阳项目 小指标管理 统计次数的节点名称
* @author       shanbh  shanbh@shuoto.cn
* @date         2017年4月18日 下午2:34:28
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class FldIndicatorzbidCishu extends MAXTableDomain{

	public FldIndicatorzbidCishu(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
		String attributeName = getMboValue().getAttributeName();
		setRelationship("INDICATORZB", "");
		
		String[] strFrom = { "INDICATORZBID" };
		String[] strTo = { attributeName };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

}
