package com.shuto.mam.app.opma;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * com.shuto.mam.app.opma.FldProfession.java
 * 
 * Email:xiamy@shuto.cn 2017年8月29日 下午10:17:14
 *
 */
public class FldProfession extends MAXTableDomain {

	public FldProfession(MboValue mbv) {
		super(mbv);
		setRelationship("profession", "1=1");
		setListCriteria("1=1");
		String[] target = { this.getMboValue().getName() };
		String[] source = { "professionnum" };
		setLookupKeyMapInOrder(target, source);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = this.getMboValue().getMbo();
		String siteid = mainMbo.getString("siteid");
		if ("".equals(siteid)) {
			siteid = mainMbo.getInsertSite();
		}
		setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
		return super.getList();
	}

	@Override
	public void validate() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.validate();
		if (getMboValue().isNull()) {
			return;
		}
		MboSetRemote list = getList();
		SqlFormat sql = new SqlFormat("PROFESSIONNUM=:1");
		sql.setObject(1, "PROFESSION", "PROFESSIONNUM", getMboValue().getString());
		String appWhere = list.getAppWhere();
		list.setAppWhere(sql.format());
		list.reset();
		int count = list.count();
		list.setAppWhere(appWhere);
		list.reset();
		if (count == 0) {
			throw new MXApplicationException("提示", "该专业在选择列表中不存在");
		}
	}
}
