package com.shuto.mam.app.workorder;

import java.rmi.RemoteException;

import psdi.app.workorder.FldWoSLTagoutId;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.workorder.MyFldWoSLTagoutId 动火 任务单 选择安全措施华东大区
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年8月26日 下午5:19:26
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class MyFldWoSLTagoutId extends FldWoSLTagoutId {
	// com.shuto.mam.app.workorder.MyFldWoSLTagoutId
	public MyFldWoSLTagoutId(MboValue mbv) throws MXException {
		super(mbv);
		// setRelationship("TAGOUT", "siteid= :siteid");
		// setLookupKeyMapInOrder(new String[] { "TAGOUTID",
		// "TAGOUTDESCRIPTION2",
		// "TAGOUTDESCRIPTION" }, new String[] { "TAGOUTID",
		// "description", "description" });
		setRelationship("LOCATIONS", "");
		setLookupKeyMapInOrder(
				new String[] { "TAGOUTID", "TAGOUTDESCRIPTION2","TAGMETHOD" },
				new String[] { "location", "description","TAGMETHOD" });
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		// MboRemote mainMbo = getMboValue().getMbo().getOwner();
		MboRemote mbo = getMboValue().getMbo();
		MboRemote woMbo = mbo.getMboSet("WORKORDER").getMbo(0);
		// String type = woMbo.getString("S_ORDERTYPE");
		// if("电气第一种工作票".equals(type)){
		// setListCriteria("ISJDX=1");
		// }else{
		// setListCriteria("sfgld != 0");
		// }
		setListCriteria("sfgld != 0");
		return super.getList();
	}

	@Override
	public void action() throws MXException, RemoteException {
		super.action();

	}
}