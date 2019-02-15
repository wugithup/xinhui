package com.shuto.mam.webclient.beans.jsoplog;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.jsoplog.JsOplogDataBean2 华中大区技术部工作日志 app:
 * JSOPLOG
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年6月6日 下午4:27:28
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class JsOplogDataBean2 extends DataBean {
	public int addrow() throws MXException {
		super.addrow();
		setValue("type", "领导事项", 11L);
		try {
			setValue("jsoplognum", getMbo().getOwner().getString("jsoplognum"),
					11L);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 1;
	}
}