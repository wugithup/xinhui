package com.shuto.mam.webclient.beans.stpi.pi_area;

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
			String sql="type = '点检' and IS_ENABLE = 1";
			MboSetRemote itemSet = mainMbo.getMboSet("ST_PI_AREA_ITEM");
			if ((mainMbo != null)) {
				if (!itemSet.isEmpty()) {
					String str = Toolkit.getConcat(itemSet, "ST_PI_ITEMID");
					sql = sql+" and ST_PI_ITEMID not in "+str+"";
				}
			}
			mboSet.setWhere(sql);
			mboSet.reset();
			super.initialize();
		}
	  public synchronized int execute() throws MXException, RemoteException {
	    super.execute();
	    MboRemote mbo = this.app.getAppBean().getMbo();
	    Vector selectLine = getMboSet().getSelection();
	    MboSetRemote PIAreaItemSet = mbo.getMboSet("ST_PI_AREA_ITEM");
	    if (selectLine.size() > 0) {
	      for (int i = 0; i < selectLine.size(); i++) {
	    	int seq = i+1;
	        MboRemote selectMbo = (MboRemote)selectLine.elementAt(i);
	        MboRemote PIAreaItem = PIAreaItemSet.add();
	        PIAreaItem.setValue("ST_PI_AREAID", String.valueOf(mbo.getUniqueIDValue()),11L);
	        PIAreaItem.setValue("ST_PI_ITEMID", String.valueOf(selectMbo.getUniqueIDValue()),11L);
	        PIAreaItem.setValue("TYPE", "点检");
	        PIAreaItem.setValue("SEQ", seq*5 ,11L);
	      }
	    }

	    this.app.getDataBean("1477988805255").reloadTable();
		this.app.getDataBean("1477988805255").refreshTable();
		sessionContext.queueEvent(new WebClientEvent("dialogcancel", sessionContext.getCurrentPageId(), "", sessionContext));
		
	    return 1;
	  }}