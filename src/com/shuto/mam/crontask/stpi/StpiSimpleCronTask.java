package com.shuto.mam.crontask.stpi;

import java.rmi.RemoteException;

import psdi.app.system.CrontaskParamInfo;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;


public class StpiSimpleCronTask extends SimpleCronTask {
	//定义参数
	private static CrontaskParamInfo[] params = null;
	@Override
	
	public void cronAction() {
		
	}
	//定时任务创建时使用反射通过此方法获取当前定时任务类的参数
	public CrontaskParamInfo[] getParameters() throws MXException,
			RemoteException {
		return params;
	}
	//静态代码块初始化参数
	static {
		params = new CrontaskParamInfo[2];
		params[0] = new CrontaskParamInfo();
		params[0].setName("siteid");
		params[1] = new CrontaskParamInfo();
		params[1].setName("orgid");
	}
}
