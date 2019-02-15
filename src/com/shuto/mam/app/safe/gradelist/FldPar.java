package com.shuto.mam.app.safe.gradelist;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.safe.gradelist.FldPar 华东大区阜阳项目 等级选项管理维护 上级编号
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月30日 下午7:44:46
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldPar extends MAXTableDomain {

	public FldPar(MboValue mboValue) {
		super(mboValue);
		String s = getMboValue().getAttributeName();
		setRelationship("gradelist", "gradelistnum= :" + s);
		setLookupKeyMapInOrder(new String[] { s },
				new String[] { "gradelistnum" });
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria(" GRADESTATUS = '启用' ");
		return super.getList();
	}

}
