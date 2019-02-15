package com.shuto.mam.app.operation.oplog;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldBanCi extends MAXTableDomain {
	public FldBanCi(MboValue mbv) {
		super(mbv);

		String thisAtt = getMboValue().getName();

		setRelationship("shift", "1=1");

		String[] strTo = { thisAtt };

		String[] strFrom = { "shiftnum" };

		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		String siteid = getMboValue().getMbo().getString("siteid");
		if ("".equals(siteid)) {
			siteid = getMboValue().getMbo().getInsertSite();
		}
		setListCriteria("shifttype = '班次' and siteid='" + siteid + "'");

		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		MboRemote mbo = getMboValue().getMbo();

		MboSetRemote bancis = mbo.getMboSet("$banci", "shift",
				"shiftnum='" + getMboValue().getString() + "' and orgid='" + mbo.getString("orgid") + "'");
		if ((bancis.count() <= 0) || (!(mbo instanceof OpLogRemote)))
			return;
		mbo.setValue("zbstartdate", sdf.format(bancis.getMbo(0).getDate("starttime")));
		mbo.setValue("zbenddate", sdf.format(bancis.getMbo(0).getDate("endtime")));
	}
}