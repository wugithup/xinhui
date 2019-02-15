package com.shuto.mam.webclient.beans.wo.woticket;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * com.shuto.mam.webclient.beans.wo.woticket.SelectGldDataBean 选择隔离点的 隔离点列表类
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年6月9日 下午5:25:20
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class SelectGldDataBean extends DataBean {

	protected MboSetRemote getMboSetRemote() throws MXException,
			RemoteException {
		// 得到当前的住对象的mbo
		MboRemote Mbo = this.app.getAppBean().getMbo();
		String wonum = Mbo.getString("wonum");
		// 通过关系得到工作票集合
		String orgid = Mbo.getString("orgid");
		String siteid = Mbo.getString("siteid");

		MboSetRemote wos = Mbo
				.getMboSet(
						"$selwo",
						"TAGOUT",
						"tag01='隔离点' and orgid='"
								+ orgid
								+ "'    and  siteid='"
								+ siteid
								+ "' and tagoutid not in (select tagoutid from WOSAFETYLINK where wonum='"
								+ wonum + "' and Tagoutid is not null) ");
		return wos;
	}

	/**
	 * <p>
	 * 并将勾选隔离点带回，然后将数据保存。
	 * 
	 * @return int 返回int类型值
	 * @throws MXException
	 * @throws RemoteException
	 */

	public int execute() throws MXException, RemoteException {
		super.execute();

		String value = creatingEvent.getSourceControlInstance().getProperty(
				"value");

		// 得到当前住对象的mbo
		MboRemote mainMbo = this.app.getAppBean().getMbo();
		MboSetRemote wos = mainMbo.getMboSet("WOSAFETYLINK");
		// 获得当前dialog中的结果集
		MboSetRemote selectLines = getMboSet();
		// 获得dialog中选中的数据的结果集
		selectLines.resetWithSelection();
		MboRemote prowo = null;
		for (int i = 0; i < selectLines.count(); ++i) {
			MboRemote wombo = (MboRemote) selectLines.getMbo(i);
			prowo = wos.add();

			if (value.equalsIgnoreCase("DQ1A001")) {
				prowo.setValue("hazardid", "DQ1A001", 2L);
			}
			if (value.equalsIgnoreCase("DQ1A002")) {
				prowo.setValue("hazardid", "DQ1A002", 2L);
			}
			if (value.equalsIgnoreCase("DQ1A003")) {
				prowo.setValue("hazardid", "DQ1A003", 2L);
			}
			if (value.equalsIgnoreCase("DQ1A004")) {
				prowo.setValue("hazardid", "DQ1A004", 2L);
			}
			if (value.equalsIgnoreCase("DQ1A005")) {
				prowo.setValue("hazardid", "DQ1A005", 2L);
			}
			if (value.equalsIgnoreCase("DQ2A001")) {
				prowo.setValue("hazardid", "DQ2A001", 2L);
			}
			if (value.equalsIgnoreCase("DQ2A002")) {
				prowo.setValue("hazardid", "DQ2A002", 2L);
			}
			if (value.equalsIgnoreCase("JXA001")) {
				prowo.setValue("hazardid", "JXA001", 2L);
			}
			if (value.equalsIgnoreCase("JXA002")) {
				prowo.setValue("hazardid", "JXA002", 2L);
			}
			if (value.equalsIgnoreCase("JXA003")) {
				prowo.setValue("hazardid", "JXA003", 2L);
			}
			if (value.equalsIgnoreCase("JXA004")) {
				prowo.setValue("hazardid", "JXA004", 2L);
			}
			if (value.equalsIgnoreCase("JXA005")) {
				prowo.setValue("hazardid", "JXA005", 2L);
			}
			if (value.equalsIgnoreCase("JXA006")) {
				prowo.setValue("hazardid", "JXA006", 2L);
			}
			if (value.equalsIgnoreCase("RKA001")) {
				prowo.setValue("hazardid", "RKA001", 2L);
			}
			if (value.equalsIgnoreCase("RKA002")) {
				prowo.setValue("hazardid", "RKA002", 2L);
			}
			if (value.equalsIgnoreCase("RKA003")) {
				prowo.setValue("hazardid", "RKA003", 2L);
			}
			prowo.setValue("wonum", mainMbo.getString("wonum"), 11L);
			prowo.setValue("TAGOUTID", wombo.getString("TAGOUTID"), 11L);
			prowo.setValue("location", wombo.getString("location"), 11L);
			prowo.setValue("REQUIREDSTATE2", wombo.getString("REQUIREDSTATE"),
					11L);// 设备隔离状态
			prowo.setValue("TAGMETHOD", wombo.getString("TAGMETHOD"), 11L);
			prowo.setValue("ISKG", wombo.getString("ISKG"), 11L);
			prowo.setValue("ISDX", wombo.getString("ISDX"), 11L);
			prowo.setValue("ISBSP", wombo.getString("ISBSP"), 11L);

			MboSetRemote locationMsr = wombo.getMboSet("&LOCATIONS",
					"locations", "location='" + wombo.getString("location")
							+ "' and siteid='" + wombo.getString("siteid")
							+ "'");
			if (!locationMsr.isEmpty()) {
				prowo.setValue("TAGOUTDESCRIPTION2", locationMsr.getMbo(0)
						.getString("description"), 2L);
			}
			locationMsr.close();
		}

		sessionContext.queueEvent(new WebClientEvent("dialogok", sessionContext
				.getCurrentPageId(), "", sessionContext));
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
		return 1;
	}

	public void acrefreshTable(String sjy) throws MXException {
		if (!sjy.isEmpty()) {
			sessionContext.queueEvent(new WebClientEvent("dialogok",
					sessionContext.getCurrentPageId(), "", sessionContext));
			DataBean databean = this.app.getDataBean(sjy);
			databean.save();
			databean.reloadTable();
			databean.refreshTable();
			this.app.getAppBean().refreshTable();
		}

	}

}