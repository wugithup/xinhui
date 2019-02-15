package com.shuto.mam.webclient.beans.operation.opticket;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

import com.shuto.mam.webclient.beans.base.DialogBean;

/**
* @author       lzq liuzq@shuoto.cn
* @Description  操作票报表
* @date         2017-6-6 上午11:30:02
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class CzpbbBean extends DialogBean{
	protected void initialize() throws MXException, RemoteException {
		super.initialize();
		String rqreportNum = (String) clientSession.getAttribute("rqreportnum");
		//操作票月度对标统计_按专业_公司	（控股）				 	操作票月度对标统计_按专业_公司（大区）
		if(rqreportNum.equals("1030")||rqreportNum.equals("1031")){
			MboRemote mbo = this.getMbo();
			String orgid=mbo.getInsertOrganization();
			mbo.setValue("orgid", orgid);
		// 	发电部操作票月度统计_按人员						发电部操作票月度统计_按专业_值别 	
		}else if(rqreportNum.equals("1036")||rqreportNum.equals("1039")){
			MboRemote mbo = this.getMbo();
			String siteid=mbo.getInsertSite();
			mbo.setValue("h_siteid", siteid);
		}
	}
}
