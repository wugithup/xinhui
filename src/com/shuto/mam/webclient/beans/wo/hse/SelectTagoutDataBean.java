package com.shuto.mam.webclient.beans.wo.hse;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * com.shuto.mam.webclient.beans.wo.hse.SelectTagoutDataBean 选择隔离点 安措固化方式
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年6月12日 上午10:45:44
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SelectTagoutDataBean extends DataBean {

	@Override
	public synchronized int execute() throws MXException, RemoteException {
		MboRemote mainMbo = this.app.getAppBean().getMbo();// 获取主单Mbo//主单Mbo
		String wonum = mainMbo.getString("wonum");
		String GLDJC = null;
		String value = creatingEvent.getSourceControlInstance().getProperty(
				"value");
		Vector<MboRemote> v = this.getSelection();// 获取用户选择的设备信息
		MboSetRemote HSEWOWHMSR = mainMbo.getMboSet("HSEWOWH");// 获取隔离锁工作票使用维护
		if (!HSEWOWHMSR.isEmpty()) {
			GLDJC = HSEWOWHMSR.getMbo(0).getString("GLDJC");
		}
		MboSetRemote safetyMsr = mainMbo.getMboSet("WOSAFETYLINK");// 安措列表
		for (int i = 0; i < v.size(); i++) {
			MboRemote selectMbo = v.get(i);
			MboRemote safetyMbo = safetyMsr.add();
			safetyMbo.setValue("wonum", wonum, 2L);
			safetyMbo.setValue("wosafetydatasource", "WO", 2L);
			if (value.equalsIgnoreCase("DQ1A001")) {
				safetyMbo.setValue("hazardid", "DQ1A001", 2L);
			}
			if (value.equalsIgnoreCase("DQ1A002")) {
				safetyMbo.setValue("hazardid", "DQ1A002", 2L);
			}
			if (value.equalsIgnoreCase("DQ1A003")) {
				safetyMbo.setValue("hazardid", "DQ1A003", 2L);
			}
			if (value.equalsIgnoreCase("DQ1A004")) {
				safetyMbo.setValue("hazardid", "DQ1A004", 2L);
			}
			if (value.equalsIgnoreCase("DQ1A005")) {
				safetyMbo.setValue("hazardid", "DQ1A005", 2L);
			}
			if (value.equalsIgnoreCase("DQ2A001")) {
				safetyMbo.setValue("hazardid", "DQ2A001", 2L);
			}
			if (value.equalsIgnoreCase("DQ2A002")) {
				safetyMbo.setValue("hazardid", "DQ2A002", 2L);
			}
			if (value.equalsIgnoreCase("JXA001")) {
				safetyMbo.setValue("hazardid", "JXA001", 2L);
			}
			if (value.equalsIgnoreCase("JXA002")) {
				safetyMbo.setValue("hazardid", "JXA002", 2L);
			}
			if (value.equalsIgnoreCase("JXA003")) {
				safetyMbo.setValue("hazardid", "JXA003", 2L);
			}
			if (value.equalsIgnoreCase("JXA004")) {
				safetyMbo.setValue("hazardid", "JXA004", 2L);
			}
			if (value.equalsIgnoreCase("JXA005")) {
				safetyMbo.setValue("hazardid", "JXA005", 2L);
			}
			if (value.equalsIgnoreCase("JXA006")) {
				safetyMbo.setValue("hazardid", "JXA006", 2L);
			}
			if (value.equalsIgnoreCase("RKA001")) {
				safetyMbo.setValue("hazardid", "RKA001", 2L);
			}
			if (value.equalsIgnoreCase("RKA002")) {
				safetyMbo.setValue("hazardid", "RKA002", 2L);
			}
			if (value.equalsIgnoreCase("RKA003")) {
				safetyMbo.setValue("hazardid", "RKA003", 2L);
			}
			MboSetRemote tagoutMsr = selectMbo.getMboSet("TAGOUT");
			MboRemote tagoutMbo = null;
			if (!tagoutMsr.isEmpty()) {
				tagoutMbo = tagoutMsr.getMbo(0);
				safetyMbo.setValue("tagoutid", tagoutMbo.getString("tagoutid"),
						2L);
				safetyMbo.setValue("TAGMETHOD",
						tagoutMbo.getString("TAGMETHOD"), 2L);
				safetyMbo.setValue("REQUIREDSTATE2",
						tagoutMbo.getString("REQUIREDSTATE"), 2L);
				safetyMbo.setValue("ISKG", tagoutMbo.getString("ISKG"), 2L);
				safetyMbo.setValue("ISDX", tagoutMbo.getString("ISDX"), 2L);
				safetyMbo.setValue("ISBSP", tagoutMbo.getString("ISBSP"), 2L);

				MboSetRemote locationMsr = tagoutMbo.getMboSet(
						"&LOCATIONS",
						"locations",
						"location='" + tagoutMbo.getString("location")
								+ "' and siteid='"
								+ tagoutMbo.getString("siteid") + "'");
				if (!locationMsr.isEmpty()) {
					safetyMbo.setValue("TAGOUTDESCRIPTION2", locationMsr
							.getMbo(0).getString("description"), 2L);
				}
				locationMsr.close();
			} else {
				safetyMbo.setValue("TAGOUTID",
						GLDJC + "_" + selectMbo.getString("location"), 2L);
				safetyMbo.setValue("TAGOUTDESCRIPTION2",
						selectMbo.getString("description"), 2L);
				safetyMbo.setValue("TAGOUTLOCATION",
						selectMbo.getString("location"), 2L);
				safetyMbo.setValue("location", selectMbo.getString("location"),
						2L);
				// safetyMbo.setValue("APPLYSEQ", i + 1, 2L);
			}
		}
		this.sessionContext
				.queueEvent(new WebClientEvent("dialogok", this.sessionContext
						.getCurrentPageId(), "", this.sessionContext));

		if (mainMbo.getString("S_ORDERTYPE").endsWith("电气第一种工作票")) {
			DataBean WOSAFETYLINK001 = this.app.getDataBean("WOSAFETYLINK001");
			WOSAFETYLINK001.save();
			WOSAFETYLINK001.reloadTable();
			WOSAFETYLINK001.refreshTable();

			DataBean WOSAFETYLINK002 = this.app.getDataBean("WOSAFETYLINK002");
			WOSAFETYLINK002.save();
			WOSAFETYLINK002.reloadTable();
			WOSAFETYLINK002.refreshTable();

			DataBean WOSAFETYLINK003 = this.app.getDataBean("WOSAFETYLINK003");
			WOSAFETYLINK003.save();
			WOSAFETYLINK003.reloadTable();
			WOSAFETYLINK003.refreshTable();

			DataBean WOSAFETYLINK004 = this.app.getDataBean("WOSAFETYLINK004");
			WOSAFETYLINK004.save();
			WOSAFETYLINK004.reloadTable();
			WOSAFETYLINK004.refreshTable();

			DataBean WOSAFETYLINK024 = this.app.getDataBean("WOSAFETYLINK024");
			WOSAFETYLINK024.save();
			WOSAFETYLINK024.reloadTable();
			WOSAFETYLINK024.refreshTable();
		}
		if (mainMbo.getString("S_ORDERTYPE").endsWith("电气第二种工作票")) {
			DataBean WOSAFETYLINK005 = this.app.getDataBean("WOSAFETYLINK005");
			WOSAFETYLINK005.save();
			WOSAFETYLINK005.reloadTable();
			WOSAFETYLINK005.refreshTable();

			DataBean WOSAFETYLINK006 = this.app.getDataBean("WOSAFETYLINK006");
			WOSAFETYLINK006.save();
			WOSAFETYLINK006.reloadTable();
			WOSAFETYLINK006.refreshTable();
		}

		if (mainMbo.getString("S_ORDERTYPE").endsWith("热力机械工作票")) {

			DataBean WOSAFETYLINK007 = this.app.getDataBean("WOSAFETYLINK007");
			WOSAFETYLINK007.save();
			WOSAFETYLINK007.reloadTable();
			WOSAFETYLINK007.refreshTable();

			DataBean WOSAFETYLINK008 = this.app.getDataBean("WOSAFETYLINK008");
			WOSAFETYLINK008.save();
			WOSAFETYLINK008.reloadTable();
			WOSAFETYLINK008.refreshTable();

			DataBean WOSAFETYLINK009 = this.app.getDataBean("WOSAFETYLINK009");
			WOSAFETYLINK009.save();
			WOSAFETYLINK009.reloadTable();
			WOSAFETYLINK009.refreshTable();

			DataBean WOSAFETYLINK010 = this.app.getDataBean("WOSAFETYLINK010");
			WOSAFETYLINK010.save();
			WOSAFETYLINK010.reloadTable();
			WOSAFETYLINK010.refreshTable();

			DataBean WOSAFETYLINK012 = this.app.getDataBean("WOSAFETYLINK012");
			WOSAFETYLINK012.save();
			WOSAFETYLINK012.reloadTable();
			WOSAFETYLINK012.refreshTable();
		}

		if (mainMbo.getString("S_ORDERTYPE").endsWith("热控工作票")) {

			DataBean WOSAFETYLINK013 = this.app.getDataBean("WOSAFETYLINK013");
			WOSAFETYLINK013.save();
			WOSAFETYLINK013.reloadTable();
			WOSAFETYLINK013.refreshTable();

			DataBean WOSAFETYLINK014 = this.app.getDataBean("WOSAFETYLINK014");
			WOSAFETYLINK014.save();
			WOSAFETYLINK014.reloadTable();
			WOSAFETYLINK014.refreshTable();

			DataBean WOSAFETYLINK015 = this.app.getDataBean("WOSAFETYLINK015");
			WOSAFETYLINK015.save();
			WOSAFETYLINK015.reloadTable();
			WOSAFETYLINK015.refreshTable();
		}

		// acrefreshTable(sjy);
		return super.execute();
	}

	public void acrefreshTable(String sjy) throws MXException {
		if (!sjy.isEmpty()) {
			DataBean databean = this.app.getDataBean(sjy);
			databean.save();
			databean.reloadTable();
			databean.refreshTable();

		}

	}
}
