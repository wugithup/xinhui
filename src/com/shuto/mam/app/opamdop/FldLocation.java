package com.shuto.mam.app.opamdop;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年5月12日 下午8:42:42
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldLocation extends MAXTableDomain {
	public FldLocation(MboValue mbv) throws MXException {
		super(mbv);
		setRelationship("LOCATIONS", "1=1");
		setListCriteria("1=1");
		String[] target = { this.getMboValue().getName() };
		String[] source = { "LOCATION" };
		setLookupKeyMapInOrder(target, source);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = this.getMboValue().getMbo();
		String siteid = mainMbo.getString("siteid");
		if ("".equals(siteid)) {
			siteid = mainMbo.getInsertSite();
		}
		setListCriteria("TYPE in ('操作中','操作') and siteid = '" + siteid + "'");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote zmbo = getMboValue().getMbo();
		String siteid = getMboValue("SITEID").getString();
		String LOCATION = getMboValue("LOCATION").getString();// 位置
		MboSetRemote level1set = zmbo.getMboSet("$ASSET", "ASSET",
				"LOCATION = '" + LOCATION + "' AND siteid = '" + siteid + "'");
		if (!level1set.isEmpty()) {
			zmbo.setValue("ASSETNUM", level1set.getMbo(0).getString("ASSETNUM"), 11l);
			level1set.close();
		}
	}
}
