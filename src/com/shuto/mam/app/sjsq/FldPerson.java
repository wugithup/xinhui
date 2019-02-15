package com.shuto.mam.app.sjsq;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 选择人员 com.shuto.mam.app.sjsq.FldPerson
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月15日 下午1:44:06
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldPerson extends MAXTableDomain {
	public FldPerson(MboValue mbv) {
		super(mbv);
		setRelationship("person", "");
		String[] strFrom = new String[] { "personid" };
		String thisAttr = getMboValue().getAttributeName();
		String strTo[] = { thisAttr };

		setLookupKeyMapInOrder(strTo, strFrom);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo mbo = getMboValue().getMbo();
		String orgid = mbo.getString("orgid");
		String siteid = mbo.getString("siteid");
		String yetai = mbo.getString("site1.yetai");// 机构级别
		String sql = "";
		if ("控股".equals(yetai)) {
			sql = "status = '活动' and isbuyer = 0 ";
		} else if ("本部".equals(yetai)) {
			sql = "status = '活动' and personid not in ('DEFLT','DEFLTREG','MAXADMIN','SYSADM','WORKFLOW','MXINTADM','MAXREG','RPTADMIN') and locationorg = '"
					+ orgid + "'";
		} else {
			sql = "status = '活动' and personid not in ('DEFLT','DEFLTREG','MAXADMIN','SYSADM','WORKFLOW','MXINTADM','MAXREG','RPTADMIN') and locationsite = '"
					+ siteid + "'";
		}
		System.out.println("");
		setListCriteria(sql);
		return super.getList();
	}

	@Override
	public void action() throws MXException, RemoteException {
		Mbo mbo = getMboValue().getMbo();
		mbo.setValue("NEWORGID", "", 2L);
		String orgid = mbo.getString("orgid");
		String siteid = mbo.getString("siteid");
		String yetai = mbo.getString("site1.yetai");// 机构级别
		if ("控股".equals(yetai)) {

		} else if ("本部".equals(yetai)) {
			getMboValue("NEWORGID").setValue(orgid, 11l);
			// getMboValue("NEWORGID").setFlag(Mbo.READONLY, true);
		} else {
			getMboValue("NEWORGID").setValue(orgid, 11l);
			getMboValue("NEWSITEID").setValue(siteid, 11l);
			getMboValue("NEWORGID").setFlag(Mbo.READONLY, true);
			getMboValue("NEWSITEID").setFlag(Mbo.READONLY, true);
		}
		super.action();
	}

	/**
	 * <p>
	 * Title: init
	 * <p>
	 * Description:
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 * @see psdi.mbo.MboValueAdapter#init()
	 */
	@Override
	public void init() throws MXException, RemoteException {
		Mbo mbo = getMboValue().getMbo();
		String orgid = mbo.getString("orgid");
		String siteid = mbo.getString("siteid");
		String yetai = mbo.getString("site1.yetai");// 机构级别
		if ("控股".equals(yetai)) {

		} else if ("本部".equals(yetai)) {
			getMboValue("NEWORGID").setValue(orgid, 11l);
			// getMboValue("NEWORGID").setFlag(Mbo.READONLY, true);
		} else {
			getMboValue("NEWORGID").setValue(orgid, 11l);
			getMboValue("NEWSITEID").setValue(siteid, 11l);
			getMboValue("NEWORGID").setFlag(Mbo.READONLY, true);
			getMboValue("NEWSITEID").setFlag(Mbo.READONLY, true);
		}
		super.init();
	}
}
