package com.shuto.mam.app.fmh;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;

/**
 * com.shuto.mam.app.fmh.FldContract 华东大区 阜阳项目 副产品销售 选择合同
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月17日 下午4:06:40
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldContract extends MAXTableDomain {

	public FldContract(MboValue mbv) {
		super(mbv);
		setRelationship("CONTRACT", "1=1");
		String[] strTo = new String[] { "CONTRACTNUM" };
		String[] strFrom = new String[] { "CONTRACTNUM" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

}
