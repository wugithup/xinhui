package com.shuto.mam.app.person;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @Title: FldDepartment.java
 * @Description: 人员表 选择部门
 * @author: lull lull@shuto.cn
 * @date: 2017年4月20日 下午11:10:16
 * @version: V1.0.0
 */
public class FldDepartment extends MAXTableDomain {
	// com.shuto.mam.app.person.FldDepartment

	/**
	 * Title: Description:
	 * 
	 * @param mbv
	 */
	public FldDepartment(MboValue mbv) {
		super(mbv);
		setRelationship("DEPARTMENT", "1=1");
		String[] strFrom = new String[] { "depnum" };
		String thisAttr = getMboValue().getAttributeName();
		String strTo[] = { thisAttr };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	/**
	 * <p>
	 * Title: getList
	 * <p>
	 * Description:
	 * 
	 * @return
	 * @throws MXException
	 * @throws RemoteException
	 * @see psdi.mbo.MAXTableDomain#getList()
	 */
	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		String siteid = getMboValue("locationsite").getString();
		setListCriteria("status = '活动' and siteid = '" + siteid + "'");
		return super.getList();
	}

	/**
	 * <p>
	 * Title: action
	 * <p>
	 * Description:
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 * @see psdi.mbo.MboValueAdapter#action()
	 */
	@Override
	public void action() throws MXException, RemoteException {
		getMboValue("post").setValue("", 2l);
		super.action();
	}

}
