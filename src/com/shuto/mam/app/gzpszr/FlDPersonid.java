package com.shuto.mam.app.gzpszr;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * com.shuto.mam.app.gzpszr.FlDPersonid 华东大区三种人选择
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年6月29日 上午9:50:23
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class FlDPersonid extends MAXTableDomain {
	public FlDPersonid(MboValue paramMboValue) {
		super(paramMboValue);
		String thisAtt = getMboValue().getName();
		// 设置要获取数据表的关系
		setRelationship("person", "personid=:" + thisAtt);
		// 目标字段名变量
		String[] strTo = { thisAtt };
		// 获取值 字段名变更
		String[] strFrom = { "personid" };
		// 此方法将获取到的值设置到目标字段 中
		setLookupKeyMapInOrder(strTo, strFrom);
	}

	public MboSetRemote getList() throws MXException, RemoteException {
		Mbo mbo = getMboValue().getMbo();
		String siteid = mbo.getString("siteid");
		if (mbo.isZombie()) {
			// 过滤器中的Mbo
			if (!"MAXADMIN".equalsIgnoreCase(mbo.getThisMboSet().getUserInfo()
					.getPersonId())) {
				siteid = mbo.getThisMboSet().getProfile().getDefaultSite();
				this.setListCriteria(" status  = '活动' and locationsite='"
						+ siteid
						+ "'   and   personid not in ('DEFLT','DEFLTREG','MAXADMIN','SYSADM','WORKFLOW','MXINTADM','MAXREG','RPTADMIN') ");
			}
		} else {
			this.setListCriteria(" status  = '活动' and locationsite='"
					+ siteid
					+ "'   and   personid not in ('DEFLT','DEFLTREG','MAXADMIN','SYSADM','WORKFLOW','MXINTADM','MAXREG','RPTADMIN') ");
		}
		return super.getList();
	}

	public void action() throws MXException, RemoteException {
		super.action();
		Mbo localMbo = getMboValue().getMbo();

		String str1 = "";
		String str2 = "";
		MboSetRemote localMboSetRemote = localMbo.getMboSet("person");
		if (!localMboSetRemote.isEmpty())
			str1 = localMboSetRemote.getMbo(0).getString("DEPARTMENT");
		str2 = localMboSetRemote.getMbo(0).getString("PROFESSION");
		localMbo.setValue("s_departmentsid", str1, 11L);
		localMbo.setValue("PROFESSION", str2, 11L);
		localMboSetRemote.close();
	}
}