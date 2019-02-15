package com.shuto.mam.app.tzgl;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FldBqjg extends MboValueAdapter {
	public FldBqjg(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() throws MXException, RemoteException {
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long diff;
		long hour = 0;
		long day = 0;
		MboRemote mbo = getMboValue().getMbo();
		MboSetRemote msr = mbo.getMboSet("$TZ_BQJL", "TZ_BQJL",
				"lqlv is not null order by tz_bqjlid desc");
		System.out
				.println("-------------------------------------***************");
		MboRemote prevMbo = msr.getMbo(0);
		// 上一条记录补氢完成时间
		long scFinishDate = prevMbo.getDate("FINISHDATE").getTime();
		// 本次补氢开始时间
		long bcStartDate = mbo.getDate("STARTDATE").getTime();
		diff = bcStartDate - scFinishDate;
		day = diff / nd;// 计算差多少天
		hour = diff % nd / nh + day * 24;// 计算差多少小时
		mbo.setValue("BQJG", hour, 11L);
		super.action();
	}

}
