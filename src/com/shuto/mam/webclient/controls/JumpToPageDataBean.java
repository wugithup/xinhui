package com.shuto.mam.webclient.controls;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.ControlInstance;

/**
    *  文件名： com.shuto.mam.webclient.controls.JumpToPageDataBean.java
    *  说明：TODO
    *  创建日期： 2017年10月10日
    *  修改历史 :   
    *     1. [2017年10月10日下午12:55:42] 创建文件 by lull lull@shuto.cn
   */
public class JumpToPageDataBean extends DataBean {
	private int displayrowsperpage = -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see psdi.webclient.system.beans.DataBean#initialize()
	 */
	@Override
	protected void initialize() throws MXException, RemoteException {
		super.initialize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see psdi.webclient.system.beans.DataBean#execute()
	 */
	@Override
	public synchronized int execute() throws MXException, RemoteException {
		super.execute();
		ControlInstance ci = this.creatingEvent.getSourceControlInstance();
		if (ci.getDataBean() == null) {
			return 1;
		}

		DataBean dataBean = ci.getDataBean();
		displayrowsperpage = getDisplayRowsPerPage();

		if (getMbo() != null) {
			int pagenum = getMbo().getInt("PAGENUM")-1;
			int rownum = pagenum * this.displayrowsperpage;
			DataBeanUtils.movetorow(rownum, dataBean);
		}
		return 1;
	}

	private int getDisplayRowsPerPage() {
		if (displayrowsperpage != -1) {
			return displayrowsperpage;
		}
		ControlInstance ci = this.creatingEvent.getSourceControlInstance();
		DataBean dataBean = ci.getDataBean();
		displayrowsperpage = dataBean.getPageRowCount();
		return displayrowsperpage;
	}
}
