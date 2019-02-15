package com.shuto.mam.webclient.beans.asset.assetyd;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.asset.assetyd.SelectSqZyPersonDataBean
 * 设备异动选择会签人员 申请 专业会签人员
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年5月10日 下午3:06:13
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SelectSqZyPersonDataBean extends DataBean {
	MboRemote currMbo = null;
	MboSetRemote lines = null;
	MboRemote currLine = null;

	/**
	 * execute: 此方法是获得当前Mbo的对应的 mboset
	 * 
	 * @return MboSetRemote 返回 MboSetRemote类型值
	 * @throws MXException
	 * @throws RemoteException
	 */
	public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
		// 执行父类方法，并接收结果集
		MboSetRemote retpersons = super.getMboSetRemote();
		// 设置当前要显示的结果集的条件
		String orgid = this.app.getAppBean().getMbo().getString("orgid");
		String siteid = this.app.getAppBean().getMbo().getString("siteid");
		String asset_ydnum = this.app.getAppBean().getMbo()
				.getString("asset_ydnum");
		if (!asset_ydnum.isEmpty() && !orgid.isEmpty() && !siteid.isEmpty()) {
			retpersons
					.setWhere(" locationorg='"
							+ orgid
							+ "' and  locationsite ='"
							+ siteid
							+ "' and  status='活动' and  personid not in (select personid from ASSET_YDLINE where ASSET_YDNUM ='"
							+ asset_ydnum + "'  and  type='申请专业会签' )");
		} else {
			retpersons.setWhere("   status='活动' ");
		}

		// 重置结果集
		retpersons.reset();
		// 返回结果集
		return retpersons;
	}

	/**
	 * execute: 此方法是确定时触发（例如：dialog控件中的“确定”邦定的事件为dialogok,触发的方法就是此方法）
	 * 
	 * @return int 返回 int类型值
	 * @throws MXException
	 * @throws RemoteException
	 */
	public int execute() throws MXException, RemoteException {
		super.execute();
		// 获得当前dialog中的结果集
		MboSetRemote selectLines = getMboSet();
		// 获得dialog中选中的数据的结果集
		selectLines.resetWithSelection();
		// 获得当前主对象的记录编号的值
		String asset_ydNum = this.app.getAppBean().getMbo()
				.getString("asset_ydnum");
		// 循环遍历dialog中所选中的记录
		for (int i = 0; i < selectLines.count(); i++) {
			// 得到结果集中的一个mbo
			MboRemote personGroup = (MboRemote) selectLines.getMbo(i);
			// 得到ASSET_YDLINE表的mboest
			MboSetRemote asset_ydLineSet = app.getAppBean().getMbo()
					.getMboSet("ASSET_YDNUM_ASSET_YDLINE");
			//
			MboRemote SbydRemote = asset_ydLineSet.addAtEnd();
			// 将dialog中的人员id(personid)字段插入到子表asset_ydline中
			SbydRemote.setValue("personid", personGroup.getString("personid"),
					11L);
			// 将dialog中的部门字段(department)字段插入到子表asset_ydline中
			SbydRemote.setValue("type", "申请专业会签", 11L);
			// 将主表的编号字段(asset_ydNum)字段插入到子表asset_ydline中
			SbydRemote.setValue("asset_ydnum", asset_ydNum, 11L);

		}
		app.getAppBean().save();
		// 重新加载整个页面
		app.getAppBean().reloadTable();
		// 刷新整个页面
		app.getAppBean().refreshTable();

		return 1;
	}
}