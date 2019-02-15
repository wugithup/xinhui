package com.shuto.mam.webclient.beans.safeconfig;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.safeconfig.DeptTaleDataBean 华东大区 阜阳项目 安健环流程配置
 * 部门表上的databean（这里控制删除部门数据级联删除人员子表）
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月30日 下午8:23:45
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class DeptTaleDataBean extends DataBean {
	/**
	 * 删除部门数据，级联删除部门下的子表
	 */
	@Override
	public int toggledeleterow() throws MXException {
		int flag = super.toggledeleterow();

		try {
			MboRemote currentMbo = getMbo();

			// 获取部门下的人员子表
			MboSetRemote personSet = currentMbo.getMboSet("CONFIGUSER");

			// 如果标记了删除
			if (currentMbo.toBeDeleted()) {

				// 标记人员子表删除
				personSet.deleteAll();
			} else {
				// 撤销删除
				personSet.undeleteAll();
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
