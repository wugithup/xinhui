package com.shuto.mam.webclient.beans.fmh;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.fmh.XsjlmxDataBean 华东大区 阜阳项目副产品销售 计量明细列表类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月17日 下午3:06:12
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class XsjlmxDataBean extends DataBean {
	public int toggledeleterow() throws MXException {
		super.toggledeleterow();

		return 1;
	}

	public void seljlmx() throws MXException {
		this.app.getAppBean().save();
	}
}