package com.shuto.mam.app.asset.assetpj;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPjjg extends MAXTableDomain {
	public FldPjjg(MboValue paramMboValue) throws MXException {
		super(paramMboValue);

		setRelationship("ASSETBZFL", "1=1");

		String str = getMboValue().getName();
		String[] arrayOfString1 = { str };

		String[] arrayOfString2 = { "bzfl" };

		setLookupKeyMapInOrder(arrayOfString1, arrayOfString2);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo localMbo = getMboValue().getMbo();
		String str1 = localMbo.getString("siteid");
		String str2 = localMbo.getString("bzlx");
		setListCriteria("siteid='"
				+ str1
				+ "' and fltype='评级结果分类' and fljb=2 "
				+ "and parentfl=(select assetbzflnum from assetbzfl where fltype='评级结果分类' and siteid=:siteid and bzfl='"
				+ str2 + "')");

		setListOrderBy("ordernum");
		return super.getList();
	}
}