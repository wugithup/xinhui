package com.shuto.mam.webclient.beans.safety;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.safety.SelectDeptDataBean隐患整改抄送部门databean
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月31日 上午11:46:32
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SelectDeptDataBean extends DataBean {
	@Override
	public synchronized int execute() throws MXException, RemoteException {
		int flag = super.execute();
		// 得到主对象
		MboRemote mainMbo = app.getAppBean().getMbo();
		// 得到选择的部门
		Vector<MboRemote> selectDept = getSelection();

		if (selectDept.size() > 0) {
			// 得到抄送部门
			MboSetRemote carbonLineSet = mainMbo.getMboSet("CARBONLINE2");
			for (int x = 0; x < selectDept.size(); x++) {

				MboRemote deptMbo = selectDept.get(x);

				// 添加一个新的抄送部门
				MboRemote carbonMbo = carbonLineSet.addAtEnd();
				// 设置序号
				carbonMbo.setValue("sn", (carbonLineSet.max("sn") + 1));
				// 设置父级编号
				carbonMbo.setValue("APPLYID", mainMbo.getString("SAFEJCTBNUM"));

				// 设置所属应用
				carbonMbo.setValue("app", "SAFEYHZGX");

				// 设置抄送部门
				carbonMbo.setValue("APPLYDEPT", deptMbo.getString("dept"));
			}
			// 刷新部门列表
			app.getDataBean("ccdept").refreshTable();
		}

		return flag;
	}

	@Override
	protected MboSetRemote getMboSetRemote() throws MXException,
			RemoteException {

		MboSetRemote set = super.getMboSetRemote();
		// 得到主对象
		MboRemote mainMbo = app.getAppBean().getMbo();
		// 得到应用程序名字
		String appName = mainMbo.getThisMboSet().getApp();

		// 得到已经选择的部门
		MboSetRemote adddeptSet = mainMbo.getMboSet("CARBONLINE2");

		if (adddeptSet.count() > 0) {
			StringBuffer wherein = new StringBuffer();
			wherein.append("(");
			for (int x = 0; x < adddeptSet.count(); x++) {
				// 得到部门
				String APPLYDEPT = adddeptSet.getMbo(x).getString("APPLYDEPT");
				if (x == adddeptSet.count() - 1) {
					wherein.append("'" + APPLYDEPT + "'");
				} else {
					wherein.append("'" + APPLYDEPT + "'").append(",");
				}
			}
			wherein.append(")");
			set.setWhere(" safeconfigid in (select safeconfigid from safeconfig where app= '"
					+ appName + "') and dept not in  " + wherein);
		} else {
			set.setWhere(" safeconfigid in (select safeconfigid from safeconfig where app= '"
					+ appName + "') ");
		}

		return set;
	}
}
