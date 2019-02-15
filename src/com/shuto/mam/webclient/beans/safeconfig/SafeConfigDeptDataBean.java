package com.shuto.mam.webclient.beans.safeconfig;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.safeconfig.SafeConfigDeptDataBean 华东大区 阜阳项目
 * 安健环流程配置选择部门
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月30日 下午8:26:46
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SafeConfigDeptDataBean extends DataBean {

	@Override
	protected MboSetRemote getMboSetRemote() throws MXException,
			RemoteException {
		// 得到主对象
		MboRemote mainMbo = app.getAppBean().getMbo();
		String orgid = mainMbo.getString("orgid");
		String siteid = mainMbo.getString("siteid");

		// 获得已经选择的部门
		MboSetRemote addeddeptSet = mainMbo.getMboSet("CONFIGLINE");

		// 过滤掉表中已经有的部门
		MboSetRemote deptSet = super.getMboSetRemote();
		if (addeddeptSet.count() > 0) {
			StringBuffer wherein = new StringBuffer();

			wherein.append("(");
			for (int x = 0; x < addeddeptSet.count(); x++) {
				String dept = addeddeptSet.getMbo(x).getString("dept");
				if (x == addeddeptSet.count() - 1) {
					wherein.append("'" + dept + "'");
				} else {
					wherein.append("'" + dept + "'").append(",");
				}
			}
			wherein.append(")");
			deptSet.setWhere(" orgid='" + orgid + "' and  siteid='" + siteid
					+ "' and   status ='活动' and depnum not in "
					+ wherein.toString());
		} else {
			deptSet.setWhere("  orgid='" + orgid + "' and  siteid='" + siteid
					+ "'  and   status ='活动'");
		}
		return deptSet;
	}

	@Override
	public synchronized int execute() throws MXException, RemoteException {
		int flag = super.execute();

		// 得到选择的部门
		Vector<MboRemote> deptSet = getSelection();

		// 得到主对象
		MboRemote mainMbo = app.getAppBean().getMbo();
		// 获得编号
		long SAFECONFIGID = mainMbo.getLong("SAFECONFIGID");

		// 得到关联的部门
		MboSetRemote lineset = mainMbo.getMboSet("CONFIGLINE");

		// 循环插入
		for (int x = 0; x < deptSet.size(); x++) {
			MboRemote deptMbo = deptSet.get(x);

			MboRemote newLineMbo = lineset.addAtEnd();
			// 设置主表关联
			newLineMbo.setValue("SAFECONFIGID", SAFECONFIGID);
			// 设置序号
			newLineMbo.setValue("sn", (lineset.max("sn") + 1));
			// 设置部门编号
			newLineMbo.setValue("dept", deptMbo.getString("depnum"));
		}

		// 刷新部门表数据
		app.getDataBean("deptlist").refreshTable();

		return flag;
	}
}
