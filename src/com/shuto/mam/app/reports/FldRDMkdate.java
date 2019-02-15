package com.shuto.mam.app.reports;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

 
/**  
com.shuto.mam.app.reports.FldRDMkdate 华东大区 阜阳项目 综合统计管理 创建日期字段类
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 上午9:43:31
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class FldRDMkdate extends MboValueAdapter{

	public FldRDMkdate(MboValue mbv){
		super(mbv);
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.action();
		
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.validate();
		if(( (ReportsDataMboRemote)getMboValue().getMbo()).hasReport()){
			throw new MXApplicationException("", "该日期报表已存在,不能重复创建!");
		}
	}
	
}
