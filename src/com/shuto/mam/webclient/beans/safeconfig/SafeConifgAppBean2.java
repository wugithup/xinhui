package com.shuto.mam.webclient.beans.safeconfig;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 华东大区 阜阳项目安健环流程配置 主appbean
 * com.shuto.mam.webclient.beans.safeconfig.SafeConifgAppBean2
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月30日 下午8:27:31
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SafeConifgAppBean2 extends AppBean {
	@Override
	public int SAVE() throws MXException, RemoteException {
		// 得到主对象
		MboRemote mainMbo = getMbo();
		// 得到所属应用
		String app = mainMbo.getString("app");

		// 得到编号
		long SAFECONFIGID = mainMbo.getLong("SAFECONFIGID");

		// 查询是否存在了
		MboSetRemote configSet = mainMbo.getMboSet("$SAFECONFIG", "SAFECONFIG",
				"app ='" + app + "' and SAFECONFIGID <> " + SAFECONFIGID + "");
		configSet.setWhere("app ='" + app + "'");

		int count = configSet.count();

		// 如果存在
		if (count > 0) {
			throw new MXApplicationException("safeconig", "exists");
		} else {
			// com.shuto.mam.webclient.beans.safety.YhzgDataBean
			return super.SAVE();
		}

	}
}
