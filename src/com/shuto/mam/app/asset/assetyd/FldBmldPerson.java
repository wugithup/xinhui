package com.shuto.mam.app.asset.assetyd;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.asset.assetyd.FldBmldPerson
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年5月10日 上午9:45:53
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldBmldPerson extends MAXTableDomain {

	public FldBmldPerson(MboValue mbv) {
		super(mbv);
		// 获得当前字段名
		String thisAtt = getMboValue().getName();
		// 设置要获取数据表的关系
		setRelationship("person", "personid=:" + thisAtt);
		// 目标字段名变量
		String[] strTo = { thisAtt };
		// 获取值 字段名变更
		String[] strFrom = { "personid" };
		// 此方法将获取到的值设置到目标字段 中
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo mbo = getMboValue().getMbo();
		String orgid = mbo.getString("orgid");
		String siteid = mbo.getString("siteid");
		String department = mbo.getString("CREATEPERSON_PERSON.department");

		this.setListCriteria(" status  = '活动' and locationorg='"
				+ orgid
				+ "' and locationsite='"
				+ siteid
				+ "'   and   personid not in ('DEFLT','DEFLTREG','MAXADMIN','SYSADM','WORKFLOW','MXINTADM','MAXREG','RPTADMIN')"
				+ "  and  department ='" + department + "'");

		return super.getList();
	}
}
