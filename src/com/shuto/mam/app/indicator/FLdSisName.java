package com.shuto.mam.app.indicator;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.indicator.FLdSisName 华东大区 阜阳项目 小指标管理 取的sis点名称
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月18日 下午2:35:04
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FLdSisName extends MAXTableDomain {

	public FLdSisName(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
		String thisAttr = getMboValue().getAttributeName();
		setRelationship("indicatorSisName", "");

		String strFrom[] = { "sisname" };

		String strTo[] = { thisAttr };

		setLookupKeyMapInOrder(strTo, strFrom);// 选中后将strFrom的列内容写到strTo中
	}

	public void validate() throws MXException, RemoteException {
		if (getMboValue().isNull())
			return;
		Mbo mbo = getMboValue().getMbo();
		String mboName = mbo.getName();
		if (needSisName(mboName, mbo.getString("TYPE"))) {
			String sisDescription = null;
			MboSetRemote list = getList();
			String where = list.getWhere();
			list.setWhere("sisname='" + getMboValue().getString() + "'");
			list.reset();
			int count = list.count();
			if (count > 0) {
				sisDescription = list.getMbo(0).getString("DESCRIPTION");
			}
			list.setWhere(where);
			list.reset();
			if (count == 0) {
				throw new MXApplicationException("", "该sis指标不存在,请先创建!");
			} else {
				if (sisDescription != null) {
					mbo.setValue("NAME", sisDescription);
				}
			}
		}

	}

	/**
	 * @param mboName
	 * @param Type
	 *            节点类型
	 * @return 需要sisName的返回true
	 */
	public boolean needSisName(String mboName, String Type) {
		if ("INDICATORZB".equalsIgnoreCase(mboName)) {
			if ("超限次数".equalsIgnoreCase(Type)) {
				return false;
			}
			if ("Jeval公式".equalsIgnoreCase(Type)) {
				return false;
			}
		} else if ("INDICATORJK".equalsIgnoreCase(mboName)) {
		}
		return true;
	}
}
