package com.shuto.mam.webclient.beans.wo.wotrack;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年5月19日 上午9:46:00
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class SelectWoJobplan extends DataBean {
	public void initialize() throws MXException, RemoteException {
		super.initialize();
		DataBean dataBean = this.app.getDataBean("common_task_table");
		if (dataBean.getMboSet().count() != 0)
			throw new MXApplicationException("", "该工单已经拥有了作业任务,不能再选择");
	}

	protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		MboSetRemote mboSet = super.getMboSetRemote();
		if (mboSet != null && !mboSet.isEmpty()) {
			MboRemote mbo = app.getAppBean().getMbo();
			String siteid = mbo.getString("siteid");
			mboSet.setWhere("siteid='" + siteid + "'");
			mboSet.reset();
		}
		return mboSet;
	}

	public int selectrecord() throws MXException, RemoteException {
		DataBean dataBean = this.app.getDataBean("common_task_table");
		MboRemote mbo = getMbo();
		MboSetRemote jobtask = mbo.getMboSet("JOBTASK");
		jobtask.selectAll();

		for (int i = 0; i < jobtask.count(); i++) {
			MboRemote mbo1 = dataBean.getMboSet().add();
			mbo1.setValue("taskid", jobtask.getMbo(i).getInt("jptask"), 2L);
			mbo1.setValue("description", jobtask.getMbo(i).getString("description"), 2L);
			mbo1.setValue("estdur", jobtask.getMbo(i).getInt("taskduration"), 2L);
		}

		Utility.sendEvent(new WebClientEvent("dialogclose", this.app.getCurrentPageId(), null, this.sessionContext));
		dataBean.refreshTable();
		return 1;
	}
}