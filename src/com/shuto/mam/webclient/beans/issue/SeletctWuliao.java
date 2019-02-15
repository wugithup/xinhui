package com.shuto.mam.webclient.beans.issue;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.beans.workorder.PlanMaterialBean;

/**
 * @author xiamy
 * @email xiamy@shuto.cn
 * @create 2017-11-28 10:12
 * @desc 工单选择物料
 * @class com.shuto.mam.webclient.beans.issue.SeletctWuliao
 * @Copyright: 2017 Shuto版权所有
 **/

public class SeletctWuliao extends PlanMaterialBean {
	@Override
	public MboSetRemote getMboSetRemote() throws MXException, RemoteException {
		boolean showing_no_inventory_item = this.app.hasSigOptionAccess("SNII");
		MboRemote mbo = this.app.getAppBean().getMbo();
		String siteid = mbo.getString("siteid");
		MboSetRemote inventorys = mbo.getMboSet("#item_mur", "INVENTORY", "");
		MboSetRemote prlineset = mbo.getMboSet("SHOWPLANMATERIAL");
		StringBuffer buf = null;
		if (!prlineset.isEmpty()) {
			for (int i = 0; i < prlineset.count(); i++) {
				if (!prlineset.getMbo(i).getString("itemnum").isEmpty()) {
					String itemnum = prlineset.getMbo(i).getString("itemnum");
					if (buf == null) {
						buf = new StringBuffer("itemnum not in ('").append(
								itemnum).append("'");
					} else {
						buf.append(",'").append(itemnum).append("'");
					}
				}
			}
		}
		if (buf != null) {
			buf.append(") and   ");
		} else {
			buf = new StringBuffer();
		}
		buf.append(" status = '活动'");
		if (!showing_no_inventory_item) {
			buf.append(" and itemnum in (select itemnum from inventory where siteid = '"
					+ siteid
					+ "' and itemnum in (select itemnum from invbalances where  siteid = '"
					+ siteid + "' and curbal >0))");
		} else {
			buf.append(" and itemnum in (select itemnum from inventory where siteid = '"
					+ siteid + "')");
		}

		inventorys.setWhere(buf.toString());
		inventorys.reset();
		return inventorys;
	}

	// @Override
	public int execute() throws MXException, RemoteException {
		super.execute();
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		MboSetRemote thisSet = getMboSet();
		MboRemote inventory = null;
		MboSetRemote curPerSet = mainMbo.getMboSet("SHOWPLANMATERIAL");
		MboRemote addwpmaterial = null;
		thisSet.resetWithSelection();
		String wonum = mainMbo.getString("wonum");
		String itemnum = "";
		String itemname = "";
		String location = "";
		MboSetRemote inventoryset = null;
		for (int i = 0; i < thisSet.count(); ++i) {
			inventory = thisSet.getMbo(i);
			MboSetRemote invcostSet = inventory.getMboSet("INVCOST");
			System.out.println("平均价格：=="+invcostSet.getMbo(0).getDouble("AVGCOST"));
			addwpmaterial = curPerSet.add();
			itemnum = inventory.getString("itemnum");
			location = inventory.getString("location");
			inventoryset = inventory.getMboSet("$_wuliaoitem", "item",
					"itemnum = '" + itemnum + "'");
			itemname = inventoryset.getMbo(0).getString("description");
			addwpmaterial.setValue("itemnum", itemnum, 2L);
			addwpmaterial.setValue("description", itemname, 2L);
			addwpmaterial.setValue("LOCATION", location, 2L);
			addwpmaterial.setValue("wonum", wonum, 2L);
			//单价
			double avgcost = invcostSet.getMbo(0).getDouble("AVGCOST");
			addwpmaterial.setValue("UNITCOST", invcostSet.getMbo(0).getDouble("AVGCOST"),
					2L);
			//数量
			addwpmaterial.setValue("ITEMQTY",1,11L);
			double itemqty = addwpmaterial.getDouble("ITEMQTY");
			//行成本
			double linecost = avgcost*itemqty;
			addwpmaterial.setValue("LINECOST", linecost,11L);
		}
		this.app.getAppBean().reloadTable();
		inventoryset.close();
		return 1;

	}

	// public int execute() throws MXException, RemoteException {
	// MboRemote thismbo = this.app.getAppBean().getMbo();
	// // String siteid = thismbo.getString("siteid");
	// String wonum = thismbo.getString("wonum");
	// String loaction = thismbo.getString("loaction");
	// DataBean SeletctWuliaoBean = this.app.getDataBean("SeletctWuliao");
	// MboSetRemote diaSet = SeletctWuliaoBean.getMboSet();
	// Vector selSet = diaSet.getSelection();
	// MboRemote itemMbo = null;
	// MboRemote wpmaterialmbo = null;
	// MboSetRemote itemls = thismbo.getMboSet("SHOWPLANMATERIAL");
	// for (int i = 0; i < selSet.size(); i++) {
	// itemMbo = (MboRemote) selSet.get(i);
	// wpmaterialmbo = itemls.add();
	//
	// System.out.println(itemMbo.getString("itemnum"));
	// System.out.println(itemMbo.getString("description"));
	// wpmaterialmbo.setValue("itemnum", itemMbo.getString("itemnum"),11L);
	// wpmaterialmbo.setValue("description",
	// itemMbo.getString("description"),11L);
	// wpmaterialmbo.setValue("wonum", wonum,11L);
	// wpmaterialmbo.setValue("loaction", loaction,11L);
	// }
	// this.app.getAppBean().reloadTable();
	// return 1;
	// }
}
