package com.shuto.mam.app.sr;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年7月4日 下午3:50:39
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class FldSrAsset extends MAXTableDomain {

	public FldSrAsset(MboValue mbv) {
		super(mbv);
		setRelationship("ASSET", "1=1");
		setListCriteria("1=1");
		String[] target = { this.getMboValue().getName() };
		String[] source = { "ASSETNUM" };
		setLookupKeyMapInOrder(target, source);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = this.getMboValue().getMbo();
		String siteid = mainMbo.getString("SITEID");
		setListCriteria(" siteid = '" + siteid + "'");
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
		MboRemote zmbo = getMboValue().getMbo();
		String siteid = getMboValue("SITEID").getString();
		String assetnum = getMboValue("ASSETNUM").getString();// 位置
		MboSetRemote assetSet = zmbo.getMboSet("$ASSET", "ASSET", "ASSETNUM = '" + assetnum + "' AND siteid = '" + siteid + "'");
		if (!assetSet.isEmpty()) {
			zmbo.setValue("LOCATION", assetSet.getMbo(0).getString("LOCATION"), 11l);
			assetSet.close();
		}
	}

}
