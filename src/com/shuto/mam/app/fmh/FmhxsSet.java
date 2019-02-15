package com.shuto.mam.app.fmh;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.fmh.FmhxsSet 华东大区 阜阳项目 粉煤灰售灰对象类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月17日 下午4:27:20
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FmhxsSet extends MboSet implements FmhxsSetRemote {
	public FmhxsSet(MboServerInterface mboServerInterface)
			throws RemoteException {
		super(mboServerInterface);
	}

	protected Mbo getMboInstance(MboSet mboSet) throws MXException,
			RemoteException {
		return new Fmhxs(mboSet);

	}
}
