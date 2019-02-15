package com.shuto.mam.workflow;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Properties;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 * @Title: XXXAction.java
 * @Description: TODO
 * @author: lull lull@shuto.cn
 * @date: 2017年3月8日 下午1:58:23
 * @version: V1.0.0
 */
// 流程类
public class XXXAction implements ActionCustomClass {

	/**
	 * <p>
	 * 
	 * @Title: getProperty
	 * 
	 * @Description: 读取配置文件内容
	 * @param properName
	 * @return
	 */
	public String getProperty(String properName) {
		Properties proper = new Properties();
		InputStream read = getClass().getResourceAsStream("/shuto.properties");
		try {
			proper.load(read);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return proper.getProperty(properName);
	}

	/**
	 * <p>
	 * Title: applyCustomAction
	 * <p>
	 * Description:
	 * 
	 * @param mbo
	 * @param arg1
	 * @throws MXException
	 * @throws RemoteException
	 * @see psdi.common.action.ActionCustomClass#applyCustomAction(psdi.mbo.MboRemote,
	 *      java.lang.Object[])
	 */
	@Override
	public void applyCustomAction(MboRemote mbo, Object[] arg1) throws MXException, RemoteException {
		// TODO Auto-generated method stub

	}

}
