package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * 
 * @ClassName: FldWoTeamName
 * @Description: 工作票 作业部门 字段类
 * @author lull lull@shuto.cn
 * @date 2017年6月6日 下午3:20:28
 *
 */
public class FldWoTeamName extends MAXTableDomain {
	// com.shuto.mam.app.workorder.FldWoTeamName

	public FldWoTeamName(MboValue mbv) throws RemoteException, MXException {
		super(mbv);
		setRelationship("department", "1=1");
		String[] target = { this.getMboValue().getName() };
		String[] source = { "depnum" };
		setLookupKeyMapInOrder(target, source);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainmbo = getMboValue().getMbo();
		String siteid;
		MboSetRemote MboSet = mainmbo.getMboSet("$MAXUSER", "MAXUSER",
				" personid='" + mainmbo.getUserName() + "' ");
		if (!MboSet.isEmpty()) {
			siteid = MboSet.getMbo(0).getString("defsite");
		} else {
			siteid = mainmbo.getString("siteid");
		}
		setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
		setListOrderBy("depnum asc");
		MboSet.close();
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
	}

}