package com.shuto.mam.webclient.beans.om.safetya;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.workflow.DirectorInput;
import psdi.workflow.WorkflowDirector;

/**
 * com.shuto.mam.webclient.beans.om.safetya.SafetyaAppBean 华东大区 阜阳项目 安健环安全活动 页面类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月6日 下午9:03:09
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SafetyaAppBean extends AppBean {

	public int INSERT() throws MXException, RemoteException {
		super.INSERT();
		String appName = getMbo().getThisMboSet().getApp();
		getMbo().setValue("APP", appName, 11L);
		return 1;
	}

	public int SAVE() throws MXException, RemoteException {
		return super.SAVE();
	}

	public int ROUTEWF() throws MXException, RemoteException {
		WorkflowDirector director = clientSession.getWorkflowDirector();
		director.preventAutoInit();
		SAVE();
		director.allowAutoInit();
		director.setProcessName(getMbo().getThisMboSet().getApp());// 锁定流程名
		director.startInput(clientSession.getCurrentApp().getId(), getMbo(),
				DirectorInput.workflow);
		getWorkflowDirections(director);
		return 1;
	}

}
