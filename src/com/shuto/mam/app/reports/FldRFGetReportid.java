package com.shuto.mam.app.reports;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.reports.FldRFGetReportid 华东大区 阜阳项目
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月21日 下午3:25:01
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldRFGetReportid extends MAXTableDomain {

	public FldRFGetReportid(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
		setRelationship("REPORTSZB",
				":GETREPORTID = REPORTSID and :GETINDICATORID=REPORTSZBID");

		String strFrom[] = { "REPORTSID", "REPORTSZBID" };

		String strTo[] = { "GETREPORTID", "GETINDICATORID" };

		setLookupKeyMapInOrder(strTo, strFrom);// 选中后将strFrom的列内容写到strTo中

	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		MboSetRemote list = super.getList();
		list.setQbe("REPORTSID", "");
		list.setQbe("REPORTSZBID", "");
		return list;
	}

}
