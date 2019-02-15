package com.shuto.mam.webclient.beans.operation.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

import com.shuto.mam.webclient.beans.base.DialogBean;

/** 
* @author       lzq liuzq@shuoto.cn
* @Description  运行定期工作报表
* @date         2017-6-9 下午5:23:36
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class YxdqgzbbBean extends DialogBean{
	
	protected void initialize() throws MXException, RemoteException {
		super.initialize();
		String rqreportNum = (String) clientSession.getAttribute("rqreportnum");
	 	// 	运行定期工作 （大区）
		if(rqreportNum.equals("1094")){
			MboRemote mbo = this.getMbo();
			String orgid=mbo.getInsertOrganization();
			mbo.setValue("orgid", orgid);
		// 	运行定期工作 (项目公司)
		}else if(rqreportNum.equals("1095")){
			MboRemote mbo = this.getMbo();
			String siteid=mbo.getInsertSite();
			mbo.setValue("h_siteid", siteid);
		}
	}

}
