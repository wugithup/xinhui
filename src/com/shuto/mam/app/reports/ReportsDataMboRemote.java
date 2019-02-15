package com.shuto.mam.app.reports;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**  
com.shuto.mam.app.reports.ReportsDataMboRemote 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 上午11:05:12
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public interface ReportsDataMboRemote extends MboRemote{
	
	/**
	 * @param write	true:设置VALUE字段的值,false:不设置VALUE字段的值
	 * @throws RemoteException
	 * @throws MXException
	 */
	public void makeReport(boolean write)throws RemoteException, MXException;
	
	/**
	 * @param write	true:设置VALUE字段的值,false:不设置VALUE字段的值
	 * @throws RemoteException
	 * @throws MXException
	 * @return 是否已经创建同日期报表 
	 */
	public boolean hasReport() throws RemoteException, MXException;

}
