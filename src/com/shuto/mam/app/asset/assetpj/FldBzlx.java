package com.shuto.mam.app.asset.assetpj;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldBzlx extends MAXTableDomain {
	public FldBzlx(MboValue paramMboValue) throws MXException {
		super(paramMboValue);

		setRelationship("ASSETBZFL", "1=1");

		String str = getMboValue().getName();
		String[] arrayOfString1 = { str };

		String[] arrayOfString2 = { "assetbzflnum" };

		setLookupKeyMapInOrder(arrayOfString1, arrayOfString2);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo localMbo = getMboValue().getMbo();
		String str = localMbo.getString("siteid");
		setListCriteria("siteid='" + str + "' and fltype='评级标准分类' and fljb=1");
		setListOrderBy("ordernum");
		return super.getList();
	}
}