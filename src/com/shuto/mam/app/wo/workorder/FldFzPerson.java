package com.shuto.mam.app.wo.workorder;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * com.shuto.mam.app.wo.workorder.FldPerson 华东大区根据部门选择负责人 监护人
 *
 * @author shanbh shanbh@shuoto.cn
 * @version V1.0
 * @date 2017年6月29日 下午9:55:08
 * @Copyright: 2017 Shuto版权所有
 */
public class FldFzPerson extends MAXTableDomain {
	public FldFzPerson(MboValue mbv) {
		super(mbv);
		setRelationship("WOTICKLINE", "1=1");
		String[] target = { getMboValue().getName() };
		String[] source = { "PERSON.DISPLAYNAME" };
		setLookupKeyMapInOrder(target, source);
	}

	@Override
	public MboSetRemote getList() throws MXException, RemoteException {

		MboRemote mainMbo = getMboValue().getMbo();
		String siteid = mainMbo.getString("SITEID");
		String s_ordertype = mainMbo.getString("S_ORDERTYPE");
		String wheresql = "type='负责人' and  description='" + s_ordertype + "' and  siteid='" + siteid + "'";
		setListCriteria(wheresql);
		return super.getList();
	}

	//	public void action() throws MXException, RemoteException {
	//		super.action();
	//		MboRemote mbo = getMboValue().getMbo();
	//		String app = getMboValue().getMbo().getThisMboSet().getApp();
	//		if (("HDWOTICKET".equals(app)) || ("HZWOTICKET".equals(app))) {
	//			String lead = mbo.getString("LEAD");
	//			String wonum1 = mbo.getString("WONUM");
	//
	//			String where = "LEAD ='" + lead + "' and wonum <> '" + wonum1 + "' and "
	//					+ "status not in('等待批准','退回修改','待外委单位签发','待签发人签发','待值班负责人接票',"
	//					+ "'待值长审批','待许可开工','已关闭','已押票' ,'取消','已作废','已取消') and S_ORDERTYPE not in('一级动火工作票','二级动火工作票')  ";
	//			MboSetRemote sjset = mbo.getMboSet("$WORKORDER1122", "WORKORDER", where);
	//			if (sjset.count() > 0) {
	//				String wonum = sjset.getMbo(0).getString("WONUM");
	//				if (wonum != null) {
	//					UserInfo userInfo = mbo.getUserInfo();
	//					int userInput = MXApplicationYesNoCancelException.getUserInput("FldPerson", MXServer.getMXServer(),
	//							userInfo);
	//					if (userInput < 0) {
	//						Object[] paramwo = { wonum };
	//						throw new MXApplicationYesNoCancelException("FldPerson", "workorder", "lead", paramwo);
	//					}
	//
	//					return;
	//				}
	//			}
	//			sjset.close();
	//			if (getMboValue().getName().equals("LEAD")) {
	//				String siteid = mbo.getString("SITEID");
	//				String LEAD = mbo.getString("LEAD");
	//				MboSetRemote mbs = mbo.getMboSet("$person", "person",
	//						" personid='" + LEAD + "'   and  LOCATIONSITE = '" + siteid + "' and STATUS='活动' ");
	//				if (!(mbs.isEmpty())) {
	//					mbo.setValue("PHONE", mbs.getMbo(0).getString("MOBILEPHONE"));
	//				}
	//				mbs.close();
	//			}
	//		}
	//	}
}