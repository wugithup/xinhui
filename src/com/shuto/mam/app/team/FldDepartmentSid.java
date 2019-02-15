package com.shuto.mam.app.team;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXAccessException;
import psdi.util.MXException;

/**
 * 
 * @ClassName: FldDepartmentSid
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年6月19日 下午10:37:41
 *
 */

public class FldDepartmentSid extends MAXTableDomain {
	public FldDepartmentSid(MboValue mbv) {
		super(mbv);

		setRelationship("department", "1=1");

		String[] strTo = { "departmentsid" };

		String[] strFrom = { "depnum" };

		setLookupKeyMapInOrder(strTo, strFrom);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psdi.mbo.MAXTableDomain#getList()
	 */
	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		String siteid = getMboValue("SITEID").getString();
		setListCriteria("status = '活动' and siteid = '" + siteid + "'");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		MboRemote mbo = getMboValue().getMbo();
		MboSetRemote personmboset = mbo.getMboSet("TEAMNUM_PERSON");
		if (personmboset.count() > 0) {
			throw new MXAccessException("team", "personisnotnull");
		}

		super.action();
	}
}