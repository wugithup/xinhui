package com.shuto.mam.app.foodrep;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldPriceCount extends MboValueAdapter {

	public FldPriceCount(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.action();
		MboRemote mbo = getMboValue().getMbo();
		float price = mbo.getFloat("PRICE");
		int count = mbo.getInt("COUNT");
		float sum = count * price;
		mbo.setValue("AMOUNT", sum, 2L);// 2L会触发action方法   11L 不会触发
		MboRemote owner = mbo.getOwner();
		MboSetRemote foodlines = owner.getMboSet("FOODREPLINE");
		float foodsum = 0F;
		int size = foodlines.count();
		for (int i = 0; i < size; i++) {
			MboRemote foodline = foodlines.getMbo(i);
			float total = foodline.getFloat("AMOUNT");
			foodsum += total;
		}
		owner.setValue("COST", foodsum);
		foodlines.close();
	}

}
