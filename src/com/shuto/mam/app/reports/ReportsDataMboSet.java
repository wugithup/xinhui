package com.shuto.mam.app.reports;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.reports.ReportsDataMboSet 华东大区 阜阳项目 综合统计管理 对象类
 * 
 * @author lzq liuzq@shuoto.cn
 * @date 2017-4-14 上午11:05:37
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class ReportsDataMboSet extends MboSet implements
		ReportsDataMboSetRemote {

	public ReportsDataMboSet(MboServerInterface ms) throws RemoteException {
		super(ms);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Mbo getMboInstance(MboSet ms) throws MXException, RemoteException {
		// TODO Auto-generated method stub
		return new ReportsDataMbo(ms);
	}

}
