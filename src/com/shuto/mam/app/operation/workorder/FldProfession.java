package com.shuto.mam.app.operation.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年7月22日 下午10:22:11
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */

public class FldProfession extends MAXTableDomain {
	public FldProfession(MboValue mbv) {
		super(mbv);
		setRelationship("profession", "1=1");
		setListCriteria("1=1");
		String[] target = { getMboValue().getName() };
		String[] source = { "professionnum" };
		setLookupKeyMapInOrder(target, source);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = getMboValue().getMbo();
		String siteid = mainMbo.getInsertSite();
		setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mainMbo = this.getMboValue().getMbo();
		String siteid = mainMbo.getString("siteid"); // 地点
		String profession = mainMbo.getString("OPLOG_PROFESSION"); // 消缺专业

		MboSetRemote professionmst = mainMbo.getMboSet("$PROFESSION", "PROFESSION",
				"professionnum ='" + profession + "' and siteid='" + siteid + "'");
		if (!professionmst.isEmpty()) {
			String profession_parent = professionmst.getMbo(0).getString("parentnum"); // 父级专业
			mainMbo.setValue("PROFESSION_PARENT", profession_parent, 11l);
		}
	}

	public void validate() throws MXException, RemoteException {
		super.validate();
		if (getMboValue().isNull()) {
			return;
		}
		MboSetRemote list = getList();
		SqlFormat sql = new SqlFormat("PROFESSIONNUM=:1");
		sql.setObject(1, "PROFESSION", "PROFESSIONNUM", getMboValue().getString());
		String appWhere = list.getAppWhere();
		list.setAppWhere(sql.format());
		list.reset();
		int count = list.count();
		list.setAppWhere(appWhere);
		list.reset();
		if (count == 0)
			throw new MXApplicationException("该值在选择列表中不存在", "");
	}
}