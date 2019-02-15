package com.shuto.mam.app.person;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @ClassName: FldGzpszrProfession
 * @Description: 工作票三种人维护 选择专业 字段类
 * @author zhenglb@shuto.cn
 * @date 2017年12月3日
 *
 */
// / com.shuto.mam.app.person.FldGzpszrProfession
public class FldGzpszrProfession extends MAXTableDomain {
	// com.shuto.mam.app.workorder.FldS_profession

	public FldGzpszrProfession(MboValue mbv) throws RemoteException, MXException {
		super(mbv);
		setRelationship("profession", "1=1");
		String[] target = { this.getMboValue().getName()};
		String[] source = { "professionnum" };
		setLookupKeyMapInOrder(target, source);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainmbo = getMboValue().getMbo();
		String siteid;
		siteid = mainmbo.getString("siteid");
		if (siteid.isEmpty()) {
			siteid = mainmbo.getInsertSite();
		}
		setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
		setListOrderBy("PROFESSIONNUM");
		return super.getList();
	}

}
