package com.shuto.mam.app.kaizen;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.kaizen.FldDepartment 华东大区 appname:KAIZENITEM 菏泽KAIZEN项目
 * 创建人字段类 插入创建人部门专业
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年8月17日 下午2:14:20
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldDepartment extends MAXTableDomain {
	public FldDepartment(MboValue mbv) {
		super(mbv);

		String thisAtt = getMboValue().getName();

		setRelationship("person", "");

		String[] strFrom = { "personid" };

		String[] strTo = { thisAtt };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public void action() throws MXException, RemoteException {
		super.action();

		String createPerson = getMboValue().getString();

		MboSetRemote cpSet = getMboValue().getMbo().getMboSet("$createperson",
				"person", "personid = '" + createPerson + "'");

		if (!cpSet.isEmpty()) {
			String depnum = cpSet.getMbo(0).getString("department");

			getMboValue("rtp_dept").setValue(depnum);

			getMboValue("rtp_zy").setValue(
					cpSet.getMbo(0).getString("PROFESSION"));
		}
		cpSet.close();
	}
}