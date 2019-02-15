package com.shuto.mam.app.stpi.st_pi_user;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 人员选择
 * @author mabin
 * 2016年10月28日
 */
public class FldPersonid extends MAXTableDomain {
	public FldPersonid(MboValue mbovalue) throws MXException {
		super(mbovalue);
		String thisname = this.getMboValue().getName();
		setRelationship("PERSON", "");
		String[] strFrom = { "PERSONID" }; // 设置数据库中对象的列
		String[] strTo = { thisname }; // 设置要填充的界面对象上的列,该列要与数据库表列一一对应;
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		try {
			MboRemote mbo = getMboValue().getMbo();
			String siteid = mbo.getString("siteid");
			String orgid = mbo.getString("orgid");
			setListCriteria("LOCATIONORG = '"+orgid+"' and LOCATIONSITE = '"+siteid+"' and department is not null and status = '活动'");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return super.getList();
	}
}
