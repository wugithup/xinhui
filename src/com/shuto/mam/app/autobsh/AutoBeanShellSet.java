package com.shuto.mam.app.autobsh;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * 奖励通知单   JLTZD,  脚手架管理   JSJGL, 班组安全活动记录    TZ_BZAQHD, 消防水启/停     XFSQT   临时用电审批表    TEMELE
 com.shuto.mam.app.autobsh.AutoBeanShellSet 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月16日 下午8:02:27
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class AutoBeanShellSet extends MboSet implements AutoBeanShellSetRemote{

	public AutoBeanShellSet(MboServerInterface ms) throws RemoteException {
		super(ms);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void save() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.save();
	}

	@Override
	protected Mbo getMboInstance(MboSet mboset) throws MXException,
			RemoteException {
		// TODO Auto-generated method stub
		return new AutoBeanShell(mboset);
	}

}
