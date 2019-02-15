package com.shuto.mam.webclient.beans.fmh;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * com.shuto.mam.webclient.beans.fmh.FmhjsAppBean华东大区 阜阳项目 副产品结算 页面类
 *
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年4月7日 下午2:50:19
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FmhjsAppBean extends CAppBean {

	@Override
    public int DELETE() throws MXException, RemoteException {
		MboRemote fmhjsmbo = getMbo();
		String fmhjsnum = fmhjsmbo.getString("fmhjsnum");
		String fmhjssql = "fmhjsnum='" + fmhjsnum + "'";
		MboSetRemote fmhjslineset = fmhjsmbo.getMboSet("$fmhjsline",
				"fmhjsline", fmhjssql);
		fmhjslineset.deleteAll();
		fmhjslineset.save();
		fmhjslineset.close();
		return super.DELETE();
	}
}
