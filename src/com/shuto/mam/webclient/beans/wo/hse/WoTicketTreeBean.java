package com.shuto.mam.webclient.beans.wo.hse;

import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.text.ParseException;

import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.beans.common.DrilldownTreeBean;
import psdi.webclient.system.beans.DataBean;

/**
 * com.shuto.mam.webclient.beans.wo.hse.WoTicketTreeBean 选择隔离点的树形结构类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年6月9日 下午3:54:04
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class WoTicketTreeBean extends DrilldownTreeBean {
	public int selectnode() throws MXException, RemoteException {
		long id = 0L;
		String idStr = super.getuniqueidvalue();
		if (!"".equals(idStr)) {
			try {
				id = NumberFormat.getInstance().parse(idStr).longValue();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String systemid = getString("systemid");
			String siteid = getString("siteid");

			MboSetRemote woHazardMsr = this.app.getAppBean().getMbo().getMboSet("WOSATAGOUT");
			StringBuffer locations = new StringBuffer();
			locations.setLength(0);
			if (!woHazardMsr.isEmpty()) {
				for (int i = 0; i < woHazardMsr.count(); ++i) {
					locations.append("'").append(woHazardMsr.getMbo(i).getString("location")).append("',");
				}
			}
			DataBean locatioinBean = this.app.getDataBean("childloc_table");

			locatioinBean.setAppWhere(
					"location in (select location from lochierarchy where  parent=(select location from locations where LOCATIONSID ="
							+ id + ") and systemid='" + systemid + "' and siteid='" + siteid + "')   and siteid='"
							+ siteid + "'");

			locatioinBean.reloadTable();
			locatioinBean.refreshTable();
			locatioinBean.reset();
		}

		return super.selectnode();
	}

	public int selectrecord() throws MXException, RemoteException {
		long id = 0L;
		String idStr = super.getuniqueidvalue();
		if (!"".equals(idStr)) {
			try {
				id = NumberFormat.getInstance().parse(idStr).longValue();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String systemid = getString("systemid");
			String siteid = getString("siteid");

			MboSetRemote woHazardMsr = this.app.getAppBean().getMbo().getMboSet("WOSATAGOUT");
			StringBuffer locations = new StringBuffer();
			locations.setLength(0);
			if (!woHazardMsr.isEmpty()) {
				for (int i = 0; i < woHazardMsr.count(); ++i) {
					locations.append("'").append(woHazardMsr.getMbo(i).getString("location")).append("',");
				}
			}
			DataBean locatioinBean = this.app.getDataBean("childloc_table");
			System.out.println(locatioinBean + "   666666");
			locatioinBean.setAppWhere(
					"location in (select location from lochierarchy where  parent=(select location from locations where LOCATIONSID ="
							+ id + ") and systemid='" + systemid + "' and siteid='" + siteid + "')   and siteid='"
							+ siteid + "'");

			locatioinBean.reloadTable();
			locatioinBean.refreshTable();
			locatioinBean.reset();
		}

		return super.selectrecord();
	}
}