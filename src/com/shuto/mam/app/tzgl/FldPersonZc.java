package com.shuto.mam.app.tzgl;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;

public class FldPersonZc extends MAXTableDomain {

	public FldPersonZc(MboValue mbv) {
		super(mbv);
        // 获得当前字段名
        String thisAtt = getMboValue().getName();
        // 设置要获取数据表的关系
        setRelationship("PERSON", "");
		String[] strTo = { thisAtt};
		String[] strFrom = { "DISPLAYNAME" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

}
