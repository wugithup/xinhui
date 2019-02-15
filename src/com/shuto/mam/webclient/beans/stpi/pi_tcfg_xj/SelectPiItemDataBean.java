package com.shuto.mam.webclient.beans.stpi.pi_tcfg_xj;

import java.rmi.RemoteException;
import java.util.Vector;

import com.shuto.mam.app.stpi.Toolkit;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

public class SelectPiItemDataBean extends DataBean
{
	  @Override
		protected void initialize() throws MXException, RemoteException {
			//得到当前dialog对话框中的数据集
			MboSetRemote mboSet=this.getMboSet();
			//得到当前主对象
			MboRemote mainMbo = app.getAppBean().getMbo();
			String sql = "1=2";
			MboSetRemote areaSet = mainMbo.getMboSet("ST_PI_TASKCFG_AREA");
			if (!areaSet.isEmpty()) {
				int line = app.getDataBean("1478072749328").getCurrentRow();
				MboRemote areaMbo = areaSet.getMbo(line);
				long pi_areaid = areaMbo.getLong("ST_PI_AREAID");
				MboSetRemote itemSet = areaMbo.getMboSet("ST_PI_TASKCFG_ITEM");
				if (!itemSet.isEmpty()) {
					String str = Toolkit.getConcat(itemSet, "ST_PI_ITEMID");
					sql = "ST_PI_AREAID=" + pi_areaid+" and ST_PI_ITEMID not in " + str + "";
				} else {
					sql = "ST_PI_AREAID=" + pi_areaid;
				}
			}
			mboSet.setWhere(sql);
			mboSet.reset();
			super.initialize();
		}
	  public synchronized int execute() throws MXException, RemoteException {
	    super.execute();
	    MboRemote mainMbo = app.getAppBean().getMbo();
	    MboSetRemote areaSet = mainMbo.getMboSet("ST_PI_TASKCFG_AREA");
	    int line = app.getDataBean("1478072749328").getCurrentRow();
		MboRemote areaMbo = areaSet.getMbo(line);
	    Vector selectLine = getMboSet().getSelection();
	    MboSetRemote PIAreaItemSet = areaMbo.getMboSet("ST_PI_TASKCFG_ITEM");
	    if (selectLine.size() > 0) {
	      for (int i = 0; i < selectLine.size(); i++) {
	        MboRemote selectMbo = (MboRemote)selectLine.elementAt(i);
	        MboRemote PIAreaItem = PIAreaItemSet.add();
	        PIAreaItem.setValue("ST_PI_TASKCFGID", String.valueOf(mainMbo.getUniqueIDValue()),11L);
	        PIAreaItem.setValue("ST_PI_TASKCFG_AREAID", String.valueOf(areaMbo.getUniqueIDValue()),11L);
	        PIAreaItem.setValue("ST_PI_ITEMID", String.valueOf(selectMbo.getLong("ST_PI_ITEMID")),11L);
	        PIAreaItem.setValue("TYPE", "点检");
	        PIAreaItem.setValue("SEQ", selectMbo.getString("SEQ"),11L);
	      }
	    }

	    this.app.getDataBean("ST_PI_TASKCFG_ITEM").reloadTable();
		this.app.getDataBean("ST_PI_TASKCFG_ITEM").refreshTable();
		sessionContext.queueEvent(new WebClientEvent("dialogcancel", sessionContext.getCurrentPageId(), "", sessionContext));
		
	    return 1;
	  }}
