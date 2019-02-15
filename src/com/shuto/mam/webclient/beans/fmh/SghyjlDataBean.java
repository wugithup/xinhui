package com.shuto.mam.webclient.beans.fmh;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;

/**
 * com.shuto.mam.webclient.beans.fmh.SghyjlDataBean 华东大区 阜阳项目石膏化验记录列表类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月7日 下午3:16:55
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SghyjlDataBean extends DataBean {

	@Override
	public int addrow() throws MXException {
		// TODO Auto-generated method stub
		try {
			MboRemote mbo = this.app.getAppBean().getMbo();
			String status = mbo.getString("status");
			if (!"新建".equals(status)) {
				Utility.showMessageBox(this.clientSession.getCurrentEvent(),
						"waring", "只有新建状态才允许新建。", 1);
				return 0;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.addrow();
	}
}
