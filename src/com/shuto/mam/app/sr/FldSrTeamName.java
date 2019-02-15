package com.shuto.mam.app.sr;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @Title: FldSrTeamName.java
 * @Package com.shuto.crne.mam.app.sr
 * @Description: TODO(消缺班组过滤)
 * @author wuqi
 * @date 2017-7-4 下午02:39:59
 * @version V1.0
 */
public class FldSrTeamName extends MAXTableDomain {
	public FldSrTeamName(MboValue mbv) {
		super(mbv);
		setRelationship("team", "1=1");
		String[] target = { getMboValue().getName() };
		String[] source = { "teamnum" };
		setLookupKeyMapInOrder(target, source);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = getMboValue().getMbo();
		String siteid = mainMbo.getString("siteid");
		if ("".equals(siteid)) {
			siteid = mainMbo.getInsertSite();
		}
		setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
		setListOrderBy("TEAMID");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mainMbo = getMboValue().getMbo();
		mainMbo.setValueNull("S_XQPERSON", 11L);
	}
}