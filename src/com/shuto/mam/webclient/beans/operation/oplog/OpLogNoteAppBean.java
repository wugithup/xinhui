package com.shuto.mam.webclient.beans.operation.oplog;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import psdi.mbo.SqlFormat;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.BoundAttribute;
import psdi.webclient.system.controller.BoundComponentInstance;

public class OpLogNoteAppBean extends AppBean {
	public int CHAXUNJILUASUS() throws RemoteException, MXException {
		Map<String, BoundComponentInstance> attMap = new HashMap<String, BoundComponentInstance>();
		String happenDate1 = "";
		String happenDate2 = "";
		String oplogtype = "";
		for (String key : this.boundAttributes.keySet()) {
			BoundAttribute bb = (BoundAttribute) this.boundAttributes.get(key);
			BoundComponentInstance boundComponent = bb.getBoundComponent();
			if (boundComponent != null) {
				String bcId = boundComponent.getId();
				attMap.put(bcId, boundComponent);
				if ("1373543117324-tb".equals(bcId)) {
					happenDate1 = boundComponent.getString();
				} else if ("1373543104462-tb".equals(bcId)) {
					happenDate2 = boundComponent.getString();
				} else if ("1373543117325-tb".equals(bcId)) {
					oplogtype = boundComponent.getString();
				}
			}
		}
		StringBuffer sb = null;
		ArrayList<Map> al = new ArrayList<Map>();
		Map<String, String> map = null;
		if ((happenDate1 != null) && (!happenDate1.isEmpty())) {
			sb = new StringBuffer("HAPPENDATE <= :1");
			map = new HashMap<String, String>();
			map.put("TYPE", "Object");
			map.put("OBJECTNAME", "OPLOGNOTE");
			map.put("ATTRIBUTENAME", "HAPPENDATE");
			map.put("value", happenDate1);
			al.add(map);
		}
		if ((happenDate2 != null) && (!happenDate2.isEmpty())) {
			if (sb == null)
				sb = new StringBuffer("HAPPENDATE >= :1");
			else {
				sb = sb.append(" and HAPPENDATE >= :2");
			}
			map = new HashMap<String, String>();
			map.put("TYPE", "Object");
			map.put("OBJECTNAME", "OPLOGNOTE");
			map.put("ATTRIBUTENAME", "HAPPENDATE");
			map.put("value", happenDate2);
			al.add(map);
		}
		if ((oplogtype != null) && (!oplogtype.isEmpty())) {
			String[] oplogtype_sub = oplogtype.replace("=", "").split(","); // 获取页面上的oplogtype并把=号替换 并以，分割
			StringBuffer oplogtype_sb = new StringBuffer();
			for (String s : oplogtype_sub) {// 循环分割后的数组
				oplogtype_sb.append("'" + s + "',");// 取出数组中的值
			}
			oplogtype_sb = oplogtype_sb.deleteCharAt(oplogtype_sb.length() - 1); // 去除最后一个，号
			if (sb == null) {
				sb = new StringBuffer("OPLOGTYPE in (" + oplogtype_sb + ")");
			} else {
				sb = sb.append(" and OPLOGTYPE in (" + oplogtype_sb + ")");
			}
		}

		try {
			DataBean dataBean = this.app.getDataBean("results_showlist");
			if (sb != null) {
				SqlFormat sf = new SqlFormat(sb.toString());
				int i = 1;
				for (Map m : al) {
					if ("Object".equals(m.get("TYPE"))) {
						sf.setObject(i++, m.get("OBJECTNAME").toString(), m.get("ATTRIBUTENAME").toString(),
								m.get("value").toString());
					}
				}
				System.out.println("sf=" + sf.format());
				dataBean.setUserWhere(sf.format());
			} else {
				dataBean.setUserWhere("");
			}

			dataBean.resetQbe();

			dataBean.reset();

			dataBean.refreshTable();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return 1;
	}
}