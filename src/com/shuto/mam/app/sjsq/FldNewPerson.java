package com.shuto.mam.app.sjsq;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @Title: FldNewPerson.java
 * @Description: TODO
 * @author: lull lull@shuto.cn
 * @date: 2017年4月19日 下午11:16:52
 * @version: V1.0.0
 */
public class FldNewPerson extends MAXTableDomain {
	// com.shuto.mam.app.sjsq.FldNewPerson

	/**
	 * Title: Description:
	 * 
	 * @param mbv
	 */
	public FldNewPerson(MboValue mbv) {
		super(mbv);
		setRelationship("person", "");
		String[] strFrom = new String[] { "personid" };
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
		String siteid = getMboValue("siteid").getString();
		setListCriteria("status = '活动' and locationsite = '" + siteid + "'  and personid != '"
				+ getMboValue().getString() + "'");
		return super.getList();
	}

}
