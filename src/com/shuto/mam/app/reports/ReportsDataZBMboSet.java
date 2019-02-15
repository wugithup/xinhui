package com.shuto.mam.app.reports;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.reports.ReportsDataZBMboSet 华东大区 阜阳项目
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月21日 下午3:20:16
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class ReportsDataZBMboSet extends MboSet implements
		ReportsDataZBMboSetRemote {

	public ReportsDataZBMboSet(MboServerInterface ms) throws RemoteException {
		super(ms);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Mbo getMboInstance(MboSet ms) throws MXException, RemoteException {
		// TODO Auto-generated method stub
		return new ReportsDataZBMbo(ms);
	}

}
