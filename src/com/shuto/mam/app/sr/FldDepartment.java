package com.shuto.mam.app.sr;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 缺陷（消缺班组）
 * 
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年5月22日 下午2:16:15
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldDepartment extends MAXTableDomain {

	public FldDepartment(MboValue mbv) {
		super(mbv);
		setRelationship("department", "1=1");
		setListCriteria("1=1");
		String[] target = { this.getMboValue().getName() };
		String[] source = { "depnum" };
		setLookupKeyMapInOrder(target, source);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = this.getMboValue().getMbo();
		String siteid = mainMbo.getString("SITEID");
		setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
	}

	@Override
	public void validate() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.validate();
		if (getMboValue().isNull()) {
			return;
		}
		MboSetRemote list = getList();
		SqlFormat sql = new SqlFormat("depnum=:1");
		sql.setObject(1, "department", "depnum", getMboValue().getString());
		String appWhere = list.getAppWhere();
		list.setAppWhere(sql.format());
		list.reset();
		int count = list.count();
		list.setAppWhere(appWhere);
		list.reset();
		if (count == 0) {
			throw new MXApplicationException("该值在选择列表中不存在", "");
		}
	}
}
