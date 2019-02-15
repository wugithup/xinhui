package com.shuto.mam.app.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 
 * 
 * @ClassName: FldJiZhu
 * 
 * @Description: TODO(机组内容)
 * 
 * 
 * @author NMK
 * 
 * 
 * @date 2018年4月8日
 *
 * 
 * 
 */

public class FldJiZhu extends MAXTableDomain {
	public FldJiZhu(MboValue mbovalue) {
		super(mbovalue);
		// 获得当前字段名
		String thisAtt = getMboValue().getName();
		// 设置要获取数据表的关系
		setRelationship("ALNDOMAIN", "1=1");
		// 目标字段名变量
		String[] strTo = { thisAtt };
		// 获取值 字段名变更
		String[] strFrom = { "description" };
		// 此方法将获取到的值设置到目标字段 中
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo mbo = getMboValue().getMbo();
		String oplogtype = mbo.getString("OPLOGTYPE");
		if ("分系统投运日志".equals(oplogtype)) {
			this.setListCriteria("DOMAINID = 'XHJZ' and  maxvalue = '0' ");
		} else {
			this.setListCriteria(" DOMAINID = 'XHJZ' and     maxvalue = '1' ");
		}
		return super.getList();
	}

}
