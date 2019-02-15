package com.shuto.mam.app.reports;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**  
com.shuto.mam.app.reports.ReportsZBMboRemote 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 上午11:09:02
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public interface ReportsZBMboRemote extends MboRemote{
	
	public String jisuanValue(Date testDate) throws RemoteException, MXException;

}
