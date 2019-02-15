package com.shuto.mam.app.reports;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.jngl.FldRDReportsid 华东大区 阜阳项目 综合统计管理
 * 
 * @author lzq liuzq@shuoto.cn
 * @date 2017-4-14 上午9:43:59
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldRDReportsid extends MAXTableDomain {

	public FldRDReportsid(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
		setRelationship("REPORTS", ":REPORTSID=REPORTSID");

		String strFrom[] = { "REPORTSID" };

		String strTo[] = { "REPORTSID" };

		setLookupKeyMapInOrder(strTo, strFrom);// 选中后将strFrom的列内容写到strTo中
	}

	@Override
	public void action() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.action();
	}

	@Override
	public void validate() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.validate();
		if (((ReportsDataMboRemote) getMboValue().getMbo()).hasReport()) {
			throw new MXApplicationException("", "该日期报表已存在,不能重复创建!");
		}
	}

}
