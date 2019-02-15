package com.shuto.mam.webclient.beans.wo.wotrack;

import java.rmi.RemoteException;
import java.util.Arrays;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @Title: SerSelectDataBean.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-12 上午11:33:19 
 * @version V1.0 
 */
public class WomurItemDataBean extends DataBean {
	protected void initialize() throws MXException, RemoteException {
		super.initialize();
		String appname = this.app.getApp();
		MboRemote mainmbo = this.app.getAppBean().getMbo();
		String status = null;
		String[] statusarry = null;
		if (hasAuth()) {
			//工单
			if ("H_WOTRACK".equalsIgnoreCase(appname)) {
				status = mainmbo.getString("status");
				statusarry = new String[]{"工作进行中","待技术部专工验收","已关闭","已取消"};
				Arrays.sort(statusarry);
				//只读控制
				if (Arrays.binarySearch(statusarry, status) >= 0) {
					this.getMboSet().setFlag(7L, true);
				}
			//工单补料单
			} else if ("WO_MUR".equalsIgnoreCase(appname)) {
				status = mainmbo.getString("status");
				statusarry = new String[]{"已批准","已关闭","已取消"};
				Arrays.sort(statusarry);
				//只读控制
				if (Arrays.binarySearch(statusarry, status) >= 0) {
					this.getMboSet().setFlag(7L, true);
				}
			}
		} else {
			this.getMboSet().setFlag(7L, true);
		}
	}
	
	private boolean hasAuth() throws MXException, RemoteException {
		MboRemote mainmbo = this.app.getAppBean().getMbo();
		if (mainmbo == null) {
			return true;
		}
		String personid = mainmbo.getUserInfo().getPersonId();

		if (isSysuser()) {
			return true;
		}
		String OWNERTABLE = mainmbo.getName();
		long OWNERID = mainmbo.getUniqueIDValue();

		MboSetRemote wfinstance = mainmbo.getMboSet("instance", "WFINSTANCE",
				"ownertable='" + OWNERTABLE + "' and ownerid='"
						+ OWNERID + "' and active = 1");
		boolean noInstance = wfinstance.isEmpty();
		wfinstance.close();

		if (noInstance) {
			String createperson = mainmbo.getString("createperson");
			return personid.equals(createperson);
		}
		String sql = "ownerid='" + OWNERID + "'" + " and ownertable='"
				+ OWNERTABLE + "'" + " and assignstatus='活动'"
				+ " and assigncode='" + personid + "'";
		MboSetRemote mbosetremote = mainmbo.getMboSet("$assigncode",
				"WFASSIGNMENT", sql);
		boolean hasAssigncode = mbosetremote.isEmpty();
		mbosetremote.close();
		return !hasAssigncode;
	}
	
	public boolean isSysuser() throws MXException, RemoteException {
		MboRemote mainmbo = this.app.getAppBean().getMbo();
		String personid = mainmbo.getUserInfo().getPersonId();
		MboSetRemote users = mainmbo.getMboSet("$tmp_maxuser", "maxuser",
				"sysuser =1 and personid='" + personid + "'");
		boolean isEmpty = (users.getMbo(0) != null)
				&& (users.getMbo(1) == null);
		users.close();
		return isEmpty;
	}
}
