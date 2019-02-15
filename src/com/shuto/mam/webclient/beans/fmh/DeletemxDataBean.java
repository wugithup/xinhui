package com.shuto.mam.webclient.beans.fmh;

import java.rmi.RemoteException;

import psdi.mbo.MboData;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * com.shuto.mam.webclient.beans.fmh.DeletemxDataBean 华东大区 阜阳项目 副产品结算 结算明细列表类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月17日 下午2:56:26
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class DeletemxDataBean extends DataBean {

	public int toggledeleterow() throws MXException {
		WebClientEvent event = clientSession.getCurrentEvent();
		int row = getRowIndexFromEvent(event);
		MboData md = (MboData) tableData.get(getCacheRowIndex(row));
		try {
			MboSetRemote fmhjlmxmboset = getMboOrZombie().getMboSet("fmhjlmx");
			MboRemote smbo = fmhjlmxmboset.getMbo(0);
			if (md.toBeDeleted()) {
				// 撤消删除
				smbo.setValue("fmhjsnum", getMboOrZombie()
						.getString("fmhjsnum"));
				smbo.setValue("fmhjslinenum",
						getMboOrZombie().getString("fmhjslinenum"));
			} else {
				// 删除
				smbo.setValue("fmhjsnum", "");
				smbo.setValue("fmhjslinenum", "");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		super.toggledeleterow();
		return 1;
	}

	public void selectxsmx() throws MXException {
		app.getAppBean().save();
	}
}
