package com.shuto.mam.webclient.beans.stpi.pi_inlog;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class Pi_inlogAppBean extends AppBean {

	public Pi_inlogAppBean() {
		super();
	}

	@Override
	public int INSERT() throws MXException, RemoteException {
		int insert = super.INSERT();
		return insert;
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		return super.SAVE();
	}
	/**
	 * 重复执行纠正错误
	 * @return
	 * @throws MXException
	 * @throws RemoteException
	 */
	public int REPEAT() throws MXException, RemoteException {
		MboRemote mainMbo = app.getAppBean().getMbo();
		String status = mainMbo.getString("STATUS");
		if("异常".equals(status)){
			MboSetRemote detailSet = mainMbo.getMboSet("NOCORRECT");
			if(!detailSet.isEmpty()){
				//任务数据下载/任务数据上传/点检任务数据生成/巡检任务数据生成/任务数据备份
				String logtype = mainMbo.getString("LOGTYPE"); 
				for(int i=0;i<detailSet.count();i++){
					
				}
			}
		}
		return 1;
	}
}
