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
 * 缺陷（消缺专业）
 * 
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年5月22日 上午11:39:28
 * @since
 * @version 1.1
 * @Copyright: 2017 Shuto版权所有
 */
public class FldProfession extends MAXTableDomain {

	public FldProfession(MboValue mbv) {
		super(mbv);
		setRelationship("profession", "1=1");
		setListCriteria("1=1");
		String[] target = { this.getMboValue().getName(), "s_profession" };
		String[] source = { "professionnum", "DESCRIPTION" };
		setLookupKeyMapInOrder(target, source);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = this.getMboValue().getMbo();
		String siteid = mainMbo.getString("siteid");
		String attname = getMboValue().getName();
		String profession = mainMbo.getString("PROFESSION"); // 消缺专业
		if ("".equals(siteid)) {
			siteid = mainMbo.getInsertSite();
		}
		if ("S_ZZYTO".equalsIgnoreCase(attname) && !profession.isEmpty()) {
			setListCriteria(
					"SITEID = '" + siteid + "' and STATUS='活动'  and  professionnum not in ('" + profession + "') ");
		} else {
			setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
		}
		setListOrderBy("PROFESSIONNUM");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote mainMbo = this.getMboValue().getMbo();
		String attname = getMboValue().getName();
		String siteid = mainMbo.getString("siteid"); // 地点
		String profession = mainMbo.getString("PROFESSION"); // 消缺专业
		String S_ZZYTO = mainMbo.getString("S_ZZYTO"); // 消缺专业
		String s_seczzyto = mainMbo.getString("S_SECZZYTO");
		String s_thrzzyto = mainMbo.getString("S_THRZZYTO");
		if ("PROFESSION".equalsIgnoreCase(attname)) {
			MboSetRemote professionmst = mainMbo.getMboSet("$PROFESSION", "PROFESSION",
					"professionnum ='" + profession + "' and siteid='" + siteid + "'");
			if (!professionmst.isEmpty()) {
				String profession_parent = professionmst.getMbo(0).getString("parentnum"); // 父级专业
				mainMbo.setValue("PROFESSION_PARENT", profession_parent, 11l);
			}
		}
		if ("S_ZZYTO".equalsIgnoreCase(attname)) {
			MboSetRemote professionmst = mainMbo.getMboSet("$PROFESSION", "PROFESSION",
					"professionnum ='" + S_ZZYTO + "' and siteid='" + siteid + "'");
			if (!professionmst.isEmpty()) {
				String profession_parent = professionmst.getMbo(0).getString("professionnum"); // 父级专业
				mainMbo.setValue("PROFESSION", profession_parent, 11l);
				mainMbo.setValue("PROFESSION_PARENT", profession_parent, 11l);
			}
		}
		if ("S_SECZZYTO".equalsIgnoreCase(attname)) {
			MboSetRemote professionmst = mainMbo.getMboSet("$PROFESSION", "PROFESSION",
					"professionnum ='" + s_seczzyto + "' and siteid='" + siteid + "'");
			if (!professionmst.isEmpty()) {
				String profession_parent = professionmst.getMbo(0).getString("professionnum"); // 父级专业
				mainMbo.setValue("PROFESSION", profession_parent, 11l);
				mainMbo.setValue("PROFESSION_PARENT", profession_parent, 11l);
			}
		}
		if ("S_THRZZYTO".equalsIgnoreCase(attname)) {
			MboSetRemote professionmst = mainMbo.getMboSet("$PROFESSION", "PROFESSION",
					"professionnum ='" + s_thrzzyto + "' and siteid='" + siteid + "'");
			if (!professionmst.isEmpty()) {
				String profession_parent = professionmst.getMbo(0).getString("professionnum"); // 父级专业
				mainMbo.setValue("PROFESSION", profession_parent, 11l);
				mainMbo.setValue("PROFESSION_PARENT", profession_parent, 11l);
			}
		}
	}

	@Override
	public void validate() throws MXException, RemoteException {
		// TODO Auto-generated method stub
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
		if (count == 0) {
			throw new MXApplicationException("提示", "该值在选择列表中不存在");
		}
	}
}
