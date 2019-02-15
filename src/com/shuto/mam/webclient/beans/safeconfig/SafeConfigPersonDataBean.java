package com.shuto.mam.webclient.beans.safeconfig;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.safeconfig.SafeConfigPersonDataBean华东大区 阜阳项目
 * 安健环流程配置 选择人员
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月30日 下午8:25:26
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SafeConfigPersonDataBean extends DataBean {

	@Override
	protected MboSetRemote getMboSetRemote() throws MXException,
			RemoteException {

		MboSetRemote personSet = super.getMboSetRemote();

		// 得到部门bean
		DataBean deptBean = app.getDataBean("deptlist");
		// 得到选择的部门Mbo
		int currentRow = deptBean.getCurrentRow();
		// 根据选择的行的到mbo
		MboRemote deptMbo = deptBean.getMbo(currentRow);

		if (deptMbo != null) {
			// 得到部门编号
			String depnum = deptMbo.getString("dept");

			// 得到已经选择的人员
			MboSetRemote addpersonSet = deptMbo.getMboSet("CONFIGUSER");
			if (addpersonSet.count() > 0) {
				StringBuffer wherein = new StringBuffer();
				wherein.append("(");
				for (int x = 0; x < addpersonSet.count(); x++) {
					// 得到personid
					String personid = addpersonSet.getMbo(x).getString(
							"personid");
					if (x == addpersonSet.count() - 1) {
						wherein.append("'" + personid + "'");
					} else {
						wherein.append("'" + personid + "'").append(",");
					}
				}
				wherein.append(")");
				personSet
						.setWhere(" status ='活动' and  department = '"
								+ depnum
								+ "'  and length(personid) = Lengthb(personid) and personid not in  "
								+ wherein.toString());
			} else {
				personSet.setWhere(" status ='活动' and  department = '" + depnum
						+ "' and length(personid) = Lengthb(personid)");
			}
		} else {
			personSet.setWhere("1=2");
		}

		return personSet;
	}

	@Override
	public synchronized int execute() throws MXException, RemoteException {
		int flag = super.execute();

		// 得到选择的人员
		Vector<MboRemote> personSet = getSelection();

		// 得到主对象
		MboRemote mainMbo = app.getAppBean().getMbo();

		// 得到主表编号
		long safeconfigid = mainMbo.getLong("safeconfigid");

		// 得到部门bean
		DataBean deptBean = app.getDataBean("deptlist");
		// 得到选择的部门Mbo
		int currentRow = deptBean.getCurrentRow();
		// 根据选择的行的到mbo
		MboRemote deptMbo = deptBean.getMbo(currentRow);

		if (deptMbo != null && personSet.size() > 0) {

			// 得到选择的部门唯一id
			long configlineid = deptMbo.getLong("configlineid");

			// 得到关联的部门
			MboSetRemote userSet = deptMbo.getMboSet("CONFIGUSER");

			// 循环插入
			for (int x = 0; x < personSet.size(); x++) {
				MboRemote personMbo = personSet.get(x);

				MboRemote newPersonMbo = userSet.addAtEnd();
				// 设置主表关联
				newPersonMbo.setValue("parent", configlineid);
				// 设置序号
				newPersonMbo.setValue("sn", (userSet.max("sn") + 1));

				newPersonMbo.setValue("personid",
						personMbo.getString("personid"));

				// 设置部门
				newPersonMbo.setValue("dept", deptMbo.getString("dept"));

				// 主表编号
				newPersonMbo.setValue("safeconfigid", safeconfigid);
			}

			// 刷新部门表数据
			app.getDataBean("personlist").refreshTable();
		}

		return flag;
	}

}
