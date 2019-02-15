package com.shuto.mam.app.pm;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.pm.FldTeamnum 预防性维护选择班组类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年7月24日 下午4:18:45
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
		String siteid;
		String app = getMboValue().getMbo().getThisMboSet().getApp();
		siteid = mainmbo.getString("siteid");
		setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");

		setListOrderBy("TEAMNUM");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mainmbo = getMboValue().getMbo();
		mainmbo.setValueNull("LEAD", 11L);

	}

}
