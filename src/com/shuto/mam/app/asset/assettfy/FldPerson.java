package com.shuto.mam.app.asset.assettfy;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**  
com.shuto.mam.app.asset.assettfy.FldFZR 河南分公司  登封电厂
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月10日 上午11:40:44
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
public class FldPerson extends MAXTableDomain {

	public FldPerson(MboValue mbv) {
		super(mbv);
		// 获得当前字段名
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
		//获取申请单位
		String squnit=mbo.getString("SQUNIT");
		if (mbo.isZombie()) {
			// 过滤器中的Mbo
			if (!"MAXADMIN".equalsIgnoreCase(mbo.getThisMboSet().getUserInfo()
					.getPersonId())) {
				siteid = mbo.getThisMboSet().getProfile().getDefaultSite();
				this.setListCriteria(" status  = '活动' and locationsite='"
						+ siteid
						+ "' and DEPARTMENT='"+squnit+"'  and   personid not in ('DEFLT','DEFLTREG','MAXADMIN','SYSADM','WORKFLOW','MXINTADM','MAXREG','RPTADMIN') ");
			}
		} else {
			this.setListCriteria(" status  = '活动' and locationsite='"
					+ siteid
					+ "' and DEPARTMENT='"+squnit+"'  and   personid not in ('DEFLT','DEFLTREG','MAXADMIN','SYSADM','WORKFLOW','MXINTADM','MAXREG','RPTADMIN') ");
		}
		return super.getList();
	}
}
