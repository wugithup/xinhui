/**
 * 
 */
package com.shuto.mam.app.statistics;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @author Administrator
 *
 */
public class FldReportTargetNum extends MAXTableDomain {

	public FldReportTargetNum(MboValue mbv) {
		super(mbv);
		String thisAtt = getMboValue().getName();
		setRelationship("DAILYREPORTTARGET", "1=1");
		String[] strTo = {thisAtt};
		String[] strFrom = {"num"};
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		String personid = this.getMboSet().getUserInfo().getPersonId();
		MboSetRemote personSet = getMboValue().getMbo().getMboSet("#PERSON","PERSON", "personid='"+personid+"'");
		if (personSet.getMbo(0) != null) {
			setListCriteria("orgid = '"+personSet.getMbo(0).getString("locationorg")+"'" );
			this.setListOrderBy("reportnum,ordernum");
		}
	    return super.getList();
	}
}
