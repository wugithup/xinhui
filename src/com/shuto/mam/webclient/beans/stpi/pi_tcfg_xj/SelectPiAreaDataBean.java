package com.shuto.mam.webclient.beans.stpi.pi_tcfg_xj;

import java.rmi.RemoteException;
import java.util.Vector;

import com.shuto.mam.app.stpi.Toolkit;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

public class SelectPiAreaDataBean extends DataBean
{
	  @Override
		protected void initialize() throws MXException, RemoteException {
			//得到当前dialog对话框中的数据集
			MboSetRemote mboSet=this.getMboSet();
			//得到当前主对象
			MboRemote mainMbo = app.getAppBean().getMbo();
			String sql="type = '巡检'";
			MboSetRemote areaSet = mainMbo.getMboSet("ST_PI_TASKCFG_AREA");
			if ((mainMbo != null)) {
				if (!areaSet.isEmpty()) {
					String str = Toolkit.getConcatLong(areaSet, "ST_PI_AREAID");
					sql = sql+" and ST_PI_AREAID not in "+str+"";
				}
			}
			mboSet.setWhere(sql);
			mboSet.reset();
			super.initialize();
		}
	  public synchronized int execute() throws MXException, RemoteException {
	    super.execute();
	    MboRemote mbo = this.app.getAppBean().getMbo();
	    String type = mbo.getString("type");
	    Vector selectLine = getMboSet().getSelection();
	    MboSetRemote PIAreaSet = mbo.getMboSet("ST_PI_TASKCFG_AREA");
	    int seq = PIAreaSet.count();
	    if (selectLine.size() > 0) {
	      for (int i = 0; i < selectLine.size(); i++) {
	    	seq++;
	        MboRemote selectMbo = (MboRemote)selectLine.elementAt(i);
	        MboRemote PIArea = PIAreaSet.add();
	        PIArea.setValue("ST_PI_TASKCFGID", String.valueOf(mbo.getUniqueIDValue()),11L);
	        PIArea.setValue("ST_PI_AREAID", String.valueOf(selectMbo.getUniqueIDValue()),11L);
	        PIArea.setValue("TYPE", type);
	        PIArea.setValue("SEQ", seq*5,11L);
	        
	        //插入点检项目
	        MboSetRemote itemSet = PIArea.getMboSet("ST_PI_AREA_ITEM");
	        MboSetRemote PIAreaItemSet = PIArea.getMboSet("ST_PI_TASKCFG_ITEM");
	        for(int j=0;j<itemSet.count();j++){
	        	MboRemote itemMbo = itemSet.getMbo(j);
	        	MboRemote PIAreaItem = PIAreaItemSet.add();
		        PIAreaItem.setValue("ST_PI_TASKCFGID", String.valueOf(mbo.getUniqueIDValue()),11L);
		        PIAreaItem.setValue("ST_PI_TASKCFG_AREAID", String.valueOf(PIArea.getUniqueIDValue()),11L);
		        PIAreaItem.setValue("ST_PI_ITEMID", String.valueOf(itemMbo.getLong("ST_PI_ITEMID")),11L);
		        PIAreaItem.setValue("TYPE", type);
		        PIAreaItem.setValue("SEQ", itemMbo.getString("SEQ"),11L);
	        }
	      }
	    }

	    this.app.getDataBean("1478072749328").reloadTable();
		this.app.getDataBean("1478072749328").refreshTable();
		this.app.getDataBean("ST_PI_TASKCFG_ITEM").reloadTable();
		this.app.getDataBean("ST_PI_TASKCFG_ITEM").refreshTable();
		sessionContext.queueEvent(new WebClientEvent("dialogcancel", sessionContext.getCurrentPageId(), "", sessionContext));
	    return 1;
	  }}