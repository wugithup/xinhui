package com.shuto.mam.webclient.beans.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

import com.shuto.mam.webclient.beans.base.DialogBean;
/**
 * 
* @author       lzq liuzq@shuoto.cn
* @Description  运行记事报表
* @date         2017-6-6 上午11:28:28
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class YxjsbbBean extends DialogBean{

		protected void initialize() throws MXException, RemoteException {
			super.initialize();
			String rqreportNum = (String) clientSession.getAttribute("rqreportnum");
			MboRemote mbo = this.getMbo();
			if(rqreportNum.equals("1010")){//运行记事报表 大区				
				String orgid=mbo.getInsertOrganization();
				mbo.setValue("orgid", orgid);
			}else if(rqreportNum.equals("1011")){//运行记事报表 项目公司
				String siteid=mbo.getInsertSite();
				mbo.setValue("h_siteid", siteid);
			}else if(rqreportNum.equals("1020")){//运行设备工况报表
				String siteid=mbo.getInsertSite();
				mbo.setValue("h_siteid", siteid);
			}else if(rqreportNum.equals("1021")){//运行重要参数报表 
				String siteid=mbo.getInsertSite();
				mbo.setValue("h_siteid", siteid);
			}
		}
		
}
