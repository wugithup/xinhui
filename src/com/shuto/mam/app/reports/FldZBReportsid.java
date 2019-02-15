package com.shuto.mam.app.reports;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;

/**
 * com.shuto.mam.app.reports.FldZBReportsid 华东大区 阜阳项目
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月21日 下午3:25:38
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldZBReportsid extends MAXTableDomain {

	public FldZBReportsid(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
		setRelationship("REPORTS", ":REPORTSID=REPORTSID");

		String strFrom[] = { "REPORTSID" };

		String strTo[] = { "REPORTSID" };

		setLookupKeyMapInOrder(strTo, strFrom);// 选中后将strFrom的列内容写到strTo中
	}

}
