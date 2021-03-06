package com.shuto.mam.webclient.beans.wo.wotrack;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * 工单(批量选择物料)
 * 
 * @author 作者 E-mail: xiamy@shuto.cn
 * @date 创建时间：2017年5月19日 上午9:46:00
 * @since
 * @version 1.0
 * @Copyright: 2017 Shuto版权所有
 */
public class CMultiselectDataBean extends DataBean {
	public MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		MboRemote mbo = this.app.getAppBean().getMbo();
		String orgid = mbo.getString("orgid");
		String siteid = mbo.getString("siteid");
		MboSetRemote orgmaps = mbo.getMboSet("$orgmap", "ORGMAP", "maxorg=:orgid and maxsite=:siteid");
		if(orgmaps != null && orgmaps.count() == 1) {
			mbo.setValue("interfaceorgtype", orgmaps.getMbo(0).getString("interfaceorgtype"),2L);
		}
		String interfaceorgtype = mbo.getString("interfaceorgtype");
		if (interfaceorgtype == null || "".equals(interfaceorgtype)) {
			throw new MXApplicationException("workorder", "请先选择所属组织!");
		}
		
		MboSetRemote inventorys = mbo.getMboSet("#erpinventlist", "erpinventlist",
				"status = '活动' and (erpstatus='N' or erpstatus is null) and (upper(erpitemstatus)='ACTIVE' or erpitemstatus is null)");
		
		inventorys.setWhere("orgid='" + orgid + "' and siteid='"+siteid+"' and interfaceorgtype='"+interfaceorgtype+"'");
		return inventorys;
	}

	public int execute() throws MXException, RemoteException {
		MboSetRemote selectLines = getMboSet();
		selectLines.resetWithSelection();
		MboRemote mbo = this.app.getAppBean().getMbo();
		// 所属组织
		String orgtype = mbo.getString("interfaceorgtype");
		MboSetRemote targetdatas = this.app.getAppBean().getMbo().getMboSet("SHOWWOMURITEM");
		MboRemote targetdata = null;
		for (int i = 0; i < selectLines.count(); i++) {
			targetdata = targetdatas.add();
			targetdata.setValue("wonum", mbo.getString("wonum"));
			targetdata.setValue("itemnum", selectLines.getMbo(i).getString("itemnum"));
			targetdata.setValue("orgtype", orgtype, 11L);
			targetdata.setValue("erpid", selectLines.getMbo(i).getString("erpid"), 11L);
			// 物料单价
			double avgcost = targetdata.getDouble("erpinventcost.avgcost");
			// 物料预估价格
			double planunitcost = targetdata.getDouble("item.s_planunitcost");
			if (avgcost > 0) {
				targetdata.setValue("unitcost", avgcost, 2L);
			} else if (planunitcost > 0) {
				targetdata.setValue("unitcost", planunitcost, 2L);
			} else {
				targetdata.setValue("unitcost", 0, 2L);
			}
			targetdata.setValue("itemqty", 0, 2L);
			targetdata.setValue("remark", mbo.getString("description"));
		}
		app.getAppBean().save();
		return super.execute();
	}
}
