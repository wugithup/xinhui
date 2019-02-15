package com.shuto.mam.app.wo.workorder;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * com.shuto.mam.app.wo.workorder.FldS_RepWonum 动火票选择
 *
 * @author shanbh shanbh@shuoto.cn
 * @version V1.0
 * @date 2017年7月6日 下午9:52:29
 * @Copyright: 2017 Shuto版权所有
 */
public class FldS_RepWonum extends MAXTableDomain {
	/**
	 * 该字段数据集从workorder表中取
	 **/
	public FldS_RepWonum(MboValue mbv) {
		super(mbv);
		setRelationship("workorder", "");
		// 用于转到时目标字段和源字段的关联
		setLookupKeyMapInOrder(new String[] { "s_repwonum" }, new String[] { "wonum" });
	}

	/**
	 * 返回数据的过滤，只取工作票类型为电气和热机且状态有效的工作票
	 **/
	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		setListCriteria("siteid=:siteid and s_ordertype in('一级动火工作票','二级动火工作票')");
		MboSetRemote listset = super.getList();
		return listset;
	}

	/**
	 * 返回查找名称
	 **/
	@Override
	public String getLookupName() throws MXException, RemoteException {
		return "workorder";
	}

}
