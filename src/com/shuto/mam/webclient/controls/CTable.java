package com.shuto.mam.webclient.controls;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import psdi.webclient.controls.Table;

/**
    *  文件名： com.shuto.mam.webclient.controls.Table.java
    *  说明：TODO
    *  创建日期： 2017年10月10日
    *  修改历史 :   
    *     1. [2017年10月10日下午12:41:59] 创建文件 by lull lull@shuto.cn
   */
public class CTable extends Table {
	// com.shuto.mam.webclient.controls.CTable
	DataBean tableBean = null;

	public CTable() {
		super();

	}

	@Override
	public int nextpage() throws MXException {
		return super.nextpage();
	}

	public void initialize() {
		super.initialize();

	}

	public int startpage() throws Exception {
		this.forceFocusRow(-1);
		this.tableBean = this.getDataBean();
		int count = tableBean.getMboSet().count();
		if (count >= 1) {
			DataBeanUtils.movetorow(1, tableBean);
		}
		return 1;
	}

	public int endpage() throws Exception {
		this.forceFocusRow(-1);
		this.tableBean = this.getDataBean();
		int count = tableBean.getMboSet().count();
		if (count >= 1) {
			DataBeanUtils.movetorow(count, tableBean);
		}
		return 1;
	}

}
