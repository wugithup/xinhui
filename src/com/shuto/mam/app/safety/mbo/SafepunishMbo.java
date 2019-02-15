package com.shuto.mam.app.safety.mbo;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * 安全处罚审批mbo
 * 
 * @author zhangjq
 *
 */
public class SafepunishMbo extends Mbo implements MboRemote {

	public SafepunishMbo(MboSet ms) throws RemoteException {
		super(ms);
	}

	@Override
	public void init() throws MXException {
		super.init();
		try {
			String app = this.getThisMboSet().getApp(); // 得到当前应用程序名称
			if ("SAFEPUNISH".equals(app)) {
				safepunishInit(this);
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void safepunishInit(MboRemote mainMbo) throws RemoteException, MXException {
		String status = mainMbo.getString("status"); // 得到当前数据状态
		if ((("退回申请人".equals(status)) && hasAuth(mainMbo)) || ("新建".equals(status) || mainMbo.isNew())) {
			mainMbo.setFlag(MboConstants.READONLY, false);
		} else {
			mainMbo.setFlag(MboConstants.READONLY, true);
		}
	}

	// 只有当前接收到任务的人可以进行对应的操作
	public boolean hasAuth(MboRemote mainMbo) throws MXException, RemoteException {
		long l = mainMbo.getUniqueIDValue();
		String ownertable = mainMbo.getName().toUpperCase();
		// 获取当前登录用户
		String s1 = mainMbo.getUserInfo().getPersonId();
		MboSetRemote mbosetremote = mainMbo.getMboSet("$instance", "WFINSTANCE",
				"ownertable='" + ownertable + "' and ownerid='" + l + "' and active = 1");
		if (!mbosetremote.isEmpty()) {
			String s2 = "ownerid='" + l + "' and ownertable='" + ownertable + "' and assignstatus='活动' and assigncode='"
					+ s1 + "'";
			MboSetRemote mbosetremote1 = mainMbo.getMboSet("$assigncode", "WFASSIGNMENT", s2);
			boolean bool = !mbosetremote1.isEmpty();
			mbosetremote.close();
			return bool;
		} else {
			mbosetremote.close();
			return false;
		}
	}

}
