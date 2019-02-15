package com.shuto.mam.app.sr;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @Title: FldS_xqperson.java
 * @Package com.shuto.crne.mam.app.sr
 * @Description: TODO(消缺负责人过滤)
 * @author wuqi
 * @date 2017-7-4 下午02:39:05
 * @version V1.0
 */
public class FldS_xqperson extends MAXTableDomain {
	public FldS_xqperson(MboValue mbv) {
		super(mbv);
		setRelationship("person", "1=1");
		setListCriteria("1=1");
		String[] target = { getMboValue().getName() };
		String[] source = { "personid" };
		setLookupKeyMapInOrder(target, source);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = getMboValue().getMbo();
		String siteid = mainMbo.getString("SITEID");
		String TEAMNAME = mainMbo.getString("TEAMNAME");
		String profession = mainMbo.getString("PROFESSION");
		// setListCriteria(" banzu = '" + TEAMNAME + "' and PROFESSION in (
		// select parentnum from PROFESSION where professionnum= '"
		// + profession + "' and siteid ='"+siteid+"' and status ='活动' ) and
		// LOCATIONSITE = '" + siteid
		// + "' and STATUS='活动' ");
		setListCriteria(" banzu = '" + TEAMNAME + "'  and LOCATIONSITE = '" + siteid + "' and STATUS='活动' ");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mbo = getMboValue().getMbo();
		String xqPerson = getMboValue().getString();
		String siteid = mbo.getString("SITEID");
		String TEAMNAME = mbo.getString("TEAMNAME");
		String profession = mbo.getString("PROFESSION");
		MboSetRemote mbs = mbo.getMboSet("$person", "person",
				" personid='" + xqPerson + "' and banzu = '" + TEAMNAME + "' and  LOCATIONSITE = '" + siteid + "' and STATUS='活动' ");
		if (mbs.getMbo(0) == null) {
			throw new MXApplicationException("提示", "人员填写错误，请重新选择！");
		}
	}
	//
	// public void validate() throws MXException, RemoteException {
	// super.validate();
	// if (getMboValue().isNull()) {
	// return;
	// }
	// MboSetRemote list = getList();
	// SqlFormat sql = new SqlFormat("personid=:1");
	// sql.setObject(1, "PERSON", "PERSONID", getMboValue().getString());
	// String appWhere = list.getAppWhere();
	// list.setAppWhere(sql.format());
	// list.reset();
	// int count = list.count();
	// list.setAppWhere(appWhere);
	// list.reset();
	// if (count == 0)
	// throw new MXApplicationException("该值在选择列表中不存在", "");
	// }
}