package com.shuto.mam.app.safety.mbo;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.safety.mbo.SafeMbo华东大区阜阳项目隐患整改和不安全事件通报审批Mbo
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月31日 上午11:49:33
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SafeMbo extends Mbo implements MboRemote {

	public SafeMbo(MboSet ms) throws RemoteException {
		super(ms);
	}

	@Override
	public void init() throws MXException {
		super.init();
		try {
			String app = this.getThisMboSet().getApp().toUpperCase(); // 得到当前应用程序名称
			if ("SAFEYHZGX".equals(app)) {
				safeyhzgxInit(this);
			} else if ("SAFETBN".equals(app)) {
				safetbnInit(this);
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 不安全事件通报审批
	 * 
	 * @throws MXException
	 * @throws RemoteException
	 */
	public void safetbnInit(MboRemote mainMbo) throws RemoteException,
			MXException {
		String status = mainMbo.getString("status"); // 得到当前数据状态

		if (("退回申请人".equals(status) && hasAuth(mainMbo))
				|| (mainMbo.isNew() || "新建".equals(status))) {
			mainMbo.setFlag(MboConstants.READONLY, false);
		} else {
			mainMbo.setFlag(MboConstants.READONLY, true);
		}
	}

	/**
	 * 隐患整改
	 */
	public void safeyhzgxInit(MboRemote mainMbo) throws RemoteException,
			MXException {

		String status = mainMbo.getString("status"); // 得到当前数据状态
		String attr[] = new String[] { "DATETIME", "VIOLATIONSREGULATIONS",
				"YSNUM" };
		if (!mainMbo.isNew() && !"新建".equals(status)) {
			// 不是部门整改节点或者没有权限，设置只读
			if ("退回申请人".equals(status) && hasAuth(mainMbo)) {
				mainMbo.setFieldFlag(attr, MboConstants.READONLY, false);
			} else {
				mainMbo.setFieldFlag(attr, MboConstants.READONLY, true); // 设置主表指定的字段为只读
			}
		}
	}

	// 只有当前接收到任务的人可以进行对应的操作
	public boolean hasAuth(MboRemote mainMbo) throws MXException,
			RemoteException {
		long l = mainMbo.getUniqueIDValue();
		String ownertable = mainMbo.getName().toUpperCase();
		// 获取当前登录用户
		String s1 = mainMbo.getUserInfo().getPersonId();

		MboSetRemote mbosetremote = mainMbo.getMboSet("$instance",
				"WFINSTANCE", "ownertable='" + ownertable + "' and ownerid='"
						+ l + "' and active = 1");
		if (!mbosetremote.isEmpty()) {
			String s2 = "ownerid='" + l + "' and ownertable='" + ownertable
					+ "' and assignstatus='活动' and assigncode='" + s1 + "'";
			MboSetRemote mbosetremote1 = mainMbo.getMboSet("$assigncode",
					"WFASSIGNMENT", s2);
			boolean bool = !mbosetremote1.isEmpty();
			mbosetremote.close();
			return bool;
		} else {
			mbosetremote.close();
			return false;
		}
	}
}
