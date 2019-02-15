package com.shuto.mam.app.operation.speoplog;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;
/**
 * 航运交接班     SPEOPLOG
 com.shuto.mam.app.operation.speoplog.SpeOpLogSet 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月17日 上午10:59:28
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class SpeOpLogSet extends MboSet implements SpeOpLogSetRemote {
	public SpeOpLogSet(MboServerInterface ms) throws RemoteException {
		super(ms);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Mbo getMboInstance(MboSet arg0) throws MXException,
			RemoteException {
		// TODO Auto-generated method stub
		return new SpeOpLog(arg0);
	}

}