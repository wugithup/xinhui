package com.shuto.mam.app.wpmaterial;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2017-11-28 18:31
 * @desc WPMATERIAL数量字段类
 * @class com.shuto.mam.app.wpmaterial.FldItemqty
 * @Copyright: 2017 Shuto版权所有
 **/

public class FldItemqty extends MboValueAdapter {

	public FldItemqty(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		Mbo mainMbo = this.getMboValue().getMbo();
		// 当前数量
		double itemqty = mainMbo.getDouble("ITEMQTY");
		// 当前单价
		double avgcost = mainMbo.getDouble("UNITCOST");
		// 行成本价格
		double linecost = itemqty * avgcost;
		mainMbo.setValue("LINECOST", linecost,11L);

	}
}
