package com.shuto.mam.app.person;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @Title: FldProfession.java
 * @Description: TODO
 * @author: lull lull@shuto.cn
 * @date: 2017年4月21日 下午4:50:01
 * @version: V1.0.0
 */
public class FldProfession extends MAXTableDomain {
	// com.shuto.mam.app.person.FldProfession

	/**
	 * Title: Description:
	 * 
	 * @param mbv
	 */
	public FldProfession(MboValue mbv) {
		super(mbv);
		setRelationship("profession", "1=1");
		String[] strFrom = new String[] { "professionnum" };
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
		setListCriteria("status = '活动'  and siteid = '" + siteid + "' ");
		return super.getList();
	}

}
