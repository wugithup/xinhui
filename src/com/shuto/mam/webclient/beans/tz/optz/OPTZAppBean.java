package com.shuto.mam.webclient.beans.tz.optz;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * @author lzq
 * @date 创建时间：2017-3-24 上午9:31:27
 * @since 原华南台账相关类
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
public class OPTZAppBean extends AppBean {
	@Override
	public int DELETE() throws MXException, RemoteException {
		getMbo().getMboSet("tz_optzline").deleteAll();
		return super.DELETE();
	}
}