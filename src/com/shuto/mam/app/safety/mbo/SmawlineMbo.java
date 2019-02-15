package com.shuto.mam.app.safety.mbo;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.safety.mbo.SmawlineMbo华东大区 阜阳项目 安全处罚审批子表
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月31日 上午11:49:12
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SmawlineMbo extends Mbo implements MboRemote {

	public SmawlineMbo(MboSet ms) throws RemoteException {
		super(ms);
	}

	@Override
	public void init() throws MXException {
		super.init();
		// 得到主对象
		MboRemote mainMbo = this.getOwner();
		// 得到应用程序的名字
		String app = null;
		try {
			app = mainMbo.getThisMboSet().getApp();
			SafeMbo safeMbo = null;

			// 强制转换成safembo
			if (mainMbo instanceof SafeMbo) {
				safeMbo = (SafeMbo) mainMbo;
			}
			if (safeMbo != null) {
				// 隐患整改
				if ("SAFEYHZGX".equals(app)) {
					safeMbo.safeyhzgxInit(safeMbo);
					this.safeyhzgxInit(safeMbo);
				} else if ("SAFETBN".equals(app)) { // 不安全事件通报审批
					safeMbo.safetbnInit(safeMbo);
				}
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void safeyhzgxInit(SafeMbo mainMbo) throws RemoteException,
			MXException {
		String status = mainMbo.getString("status"); // 得到当前数据状态
		// 是新建的话，
		if (mainMbo.isNew() || "新建".equals(status)) {
			this.setFieldFlag("ZGWCQK", MboConstants.READONLY, true);
		} else {
			// 不是新的
			if ("部门整改".equals(status) && mainMbo.hasAuth(mainMbo)) {
				String readonlyattr[] = new String[] { "APPLYDEPT",
						"DESCRIPTION", "ZHUANYE", "REQUESTDATE", "ROBJECT",
						"ZGNR", "ZGYQ" };
				this.setFieldFlag(readonlyattr, MboConstants.READONLY, true);
				this.setFieldFlag("ZGWCQK", MboConstants.READONLY, false);
			}
			if ("待元素负责人审核".equals(status) && mainMbo.hasAuth(mainMbo)) {
				this.setFieldFlag("ZGWCQK", MboConstants.READONLY, false);

			}
		}
	}
}
