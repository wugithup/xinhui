package com.shuto.mam.app.wo.temele;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
/**
 * 临时用电审批表        TEMELE
 com.shuto.mam.app.wo.temele.FldWOTeamName 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:35:54
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldWOTeamName extends MAXTableDomain {
	String tableName = null;
	String attName = null;

	public FldWOTeamName(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
		attName = getMboValue().getName();
		tableName = getMboValue().getMbo().getName();
		setRelationship("CLASSTEAM", "1=1");
		String[] strTo = { attName };
		String[] strFrom = { "TEAMNAME" };
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("SITEID in ('徐州项目','江苏南热')" );
	
		return super.getList();
	}

}
