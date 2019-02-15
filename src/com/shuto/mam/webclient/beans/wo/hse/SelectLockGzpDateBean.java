package com.shuto.mam.webclient.beans.wo.hse;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * 锁具信息相关工作票查询
 * 
 * @author shanbh
 * @date 2015-10-28下午11:26:52
 */
public class SelectLockGzpDateBean extends DataBean {
	protected MboSetRemote getMboSetRemote() throws MXException,
			RemoteException {
		MboRemote thismbo = this.app.getAppBean().getMbo();

		String keyid = thismbo.getString("keyid");
		MboSetRemote mboset = MXServer.getMXServer().getMboSet("workorder",
				MXServer.getMXServer().getUserInfo("maxadmin"));

		mboset.setWhere(" wonum in (select wonum  from hse_tagout where glysid='"
				+ keyid + "'    or  aqsid='" + keyid + "')");

		return mboset;
	}
}