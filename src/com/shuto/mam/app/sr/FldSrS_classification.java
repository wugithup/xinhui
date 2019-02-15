package com.shuto.mam.app.sr;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * com.shuto.mam.app.sr.FldSrS_classification.java
 * 
 * Email:xiamy@shuto.cn 2017年8月28日 下午9:45:27
 *
 */
public class FldSrS_classification extends MboValueAdapter {

	public FldSrS_classification(MboValue mbv) {
		super(mbv);
		// TODO Auto-generated constructor stub
	}

	public void action() throws MXException, RemoteException {
		System.out.println("======================1=======================");
		super.action();
		MboRemote mbo = this.getMboValue().getMbo();
		String fl = mbo.getString("S_CLASSIFICATION");
		String type = mbo.getString("S_QXLB");

		String zlfl = mbo.getString("ZLCLASSIFICATION");
		String zltype = mbo.getString("S_ZLTO");
		String attname = getMboValue().getName();
		System.out.println("====================" + attname + "=====================");
		if ("S_CLASSIFICATION".equals(attname)) {
			if ("四类缺陷".equals(type) && "非生产".equals(fl)) {
				//mbo.setFieldFlag("yxjk", 128l, false);
			} else {
				//mbo.setFieldFlag("yxjk", 128l, true);
			}
		} else {
			if ("四类缺陷".equals(zltype) && "非生产".equals(zlfl)) {
				//mbo.setFieldFlag("yxjk", 128l, false);
			} else {
				//mbo.setFieldFlag("yxjk", 128l, true);
			}
		}
	}

}
