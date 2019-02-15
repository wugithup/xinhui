package com.shuto.mam.app.asset.assetpj;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldBzlx2 extends MAXTableDomain {
	public FldBzlx2(MboValue paramMboValue) throws MXException {
		super(paramMboValue);

		setRelationship("ASSETBZFL", "1=1");

		String str = getMboValue().getName();
		String[] arrayOfString1 = { str };

		String[] arrayOfString2 = { "assetbzflnum" };

		setLookupKeyMapInOrder(arrayOfString1, arrayOfString2);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo localMbo = getMboValue().getMbo();
		String str1 = localMbo.getString("siteid");
		String str2 = localMbo.getString("bzlx");
		setListCriteria("siteid='" + str1
				+ "' and fltype='评级标准分类' and fljb=2 and parentfl='" + str2
				+ "'");
		setListOrderBy("ordernum");
		return super.getList();
	}
}