package com.shuto.mam.workflow.asset;

import java.rmi.RemoteException;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
/**
 * com.shuto.mam.workflow.asset.Ydsqjszgyj 
 * 异动竣工发电部长审批意见
 * @author huangpf huangpf@shuoto.cn
 * @date 2017年9月17日 下午16:04:29
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class Ydjgfdbzyj implements ActionCustomClass {

	@Override
	public void applyCustomAction(MboRemote arg0, Object[] arg1)
			throws MXException, RemoteException {
		String spyj = arg0.getString("JG_FDBBZYJ");
		if (spyj == null || spyj.length() <= 0) {
			arg0.setValue("JG_FDBBZYJ", "同意", 11L);
		}
	}

}
