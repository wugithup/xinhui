package com.shuto.mam.app.autobsh;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;
/**
 * 奖励通知单   JLTZD,  脚手架管理   JSJGL, 班组安全活动记录    TZ_BZAQHD, 消防水启/停     XFSQT   临时用电审批表    TEMELE
 com.shuto.mam.app.autobsh.AutoBeanShell 江苏
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月16日 下午9:06:42
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class AutoBeanShell extends Mbo implements AutoBeanShellRemote{
	private RunMboBeanShellClassFunction rmb=null;
	public AutoBeanShell(MboSet ms) throws RemoteException  {
		super(ms);
		// TODO Auto-generated constructor stub
		rmb=new RunMboBeanShellClassFunction(this);
	}
	
	@Override
	public void add() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		rmb.runBeanShellClassFunction("add", "Before",true);
		super.add();
		rmb.runBeanShellClassFunction("add", "After",true);
	}
	
	@Override
	public void init() throws MXException {
		// TODO Auto-generated method stub
		
		try {
			rmb.runBeanShellClassFunction("init", "Before",false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		super.init();
		try {
			rmb.runBeanShellClassFunction("init", "After",false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
	}
}
