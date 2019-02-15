package com.shuto.mam.webclient.beans.reportparameter;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import com.shuto.mam.webclient.beans.base.DialogBean;
/** 
* @author       lzq liuzq@shuoto.cn
* @Description  报表控制    h_wotrack/oplog/sr/h_opticket
* @date         2017-7-13 上午09:50:28
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class ReportDialogBean extends DialogBean{

	protected void initialize() throws MXException, RemoteException {
		super.initialize();
		String remark = (String) clientSession.getAttribute("remark");
		System.out.println(remark);
		MboRemote mbo = this.getMbo();
		if("大区".equals(remark)){
			String orgid=mbo.getInsertOrganization();
			mbo.setValue("orgid", orgid);
		}else if("项目公司".equals(remark)){
			String siteid=mbo.getInsertSite();
			mbo.setValue("h_siteid", siteid);
		}
	}
}
