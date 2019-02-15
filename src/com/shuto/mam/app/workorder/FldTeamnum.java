package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.workorder.FldTeamnum 控股工作票选择班组类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年7月6日 下午4:10:09
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FldTeamnum extends MAXTableDomain {

	public FldTeamnum(MboValue mbv) {
		super(mbv);
		setRelationship("team", "1=1");
		String[] target = { this.getMboValue().getName() };
		String[] source = { "teamnum" };
		setLookupKeyMapInOrder(target, source);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainmbo = getMboValue().getMbo();
		String app = getMboValue().getMbo().getThisMboSet().getApp();
		String siteid = mainmbo.getString("siteid");
		if ("".equals(siteid)) {
			siteid = mainmbo.getInsertSite();
		}
		if ("HDWOTICKET".equals(app) || ("HZWOTICKET".equals(app))) {
			String S_ORDERTYPE = mainmbo.getString("S_ORDERTYPE");

			setListCriteria("     status='活动' and  siteid ='" + siteid
					+ "' and   TEAMnum in (select  Teamnum  from  teamperson where  siteid='" + siteid
					+ "'  and  personid  in  ( select  personid  from   WOTICKLINE where  description='" + S_ORDERTYPE
					+ "'" + " and  type ='负责人' and  siteid ='" + siteid + "'))");
		} else {
			setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");

		}
		setListOrderBy("TEAMID");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mainmbo = getMboValue().getMbo();
		mainmbo.setValueNull("LEAD", 11L);
		mainmbo.setValueNull("PHONE", 11L);

	}

}
