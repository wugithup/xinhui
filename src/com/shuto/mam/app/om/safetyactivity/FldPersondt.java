package com.shuto.mam.app.om.safetyactivity;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 选择人员  
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月15日 下午1:44:06
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldPersondt extends MAXTableDomain {
	public FldPersondt(MboValue mbv) {
		super(mbv);
		setRelationship("person", "");
		String[] strFrom = new String[] { "personid" };
		String thisAttr = getMboValue().getAttributeName();
		String strTo[] = { thisAttr };

		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo mbo = getMboValue().getMbo();
		String orgid = mbo.getString("orgid");
		String siteid = mbo.getString("siteid");

		String sql = " locationorg= '"
				+ orgid
				+ "' and  locationsite= '"
				+ siteid
				+ "'  and   personid not in   ('DEFLT',  'DEFLTREG',  'MAXADMIN', 'SYSADM', 'WORKFLOW', "
				+ "  'MXINTADM',   'MAXREG',  'RPTADMIN')";
		setListCriteria(sql);
		MboSetRemote mboSet = super.getList();

		return mboSet;
	}

	@Override
	public void action() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.action();

	}
}
