package com.shuto.mam.webclient.beans.workorder;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.beans.workorder.WOAppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;

public class DtWorkorderAppBean extends WOAppBean {
	@SuppressWarnings("deprecation")
	public int CreateWoTkDHYI() throws MXException, RemoteException {
		MboRemote thisMbo = this.app.getAppBean().getMbo();
		WebClientEvent event = this.sessionContext.getCurrentEvent();
		DataBean appBean = Utility.getDataSource(this.sessionContext, this.app.getAppHandler());
		try {
			appBean.save();
			MboSetRemote woSet = thisMbo.getMboSet("showorder");
			MboRemote wo = woSet.add();
			wo.setValue("S_ORDERTYPE", "一级动火工作票", 11L);
			wo.setValue("WORKTYPE", "工作票");
			wo.setValue("parent", thisMbo.getString("wonum"), 11L);
			wo.setValue("S_PROFESSION", thisMbo.getString("S_PROFESSION"), 11L);
			wo.setValue("WORKSITE", thisMbo.getString("WORKSITE"));
			wo.setValue("DESCRIPTION", thisMbo.getString("DESCRIPTION"));
			woSet.save();
			MboSetRemote relaMboSet = thisMbo.getMboSet("RELATEDWO");
			MboRemote rela = relaMboSet.add();
			rela.setValue("recordkey", thisMbo.getString("wonum"), 11L);
			rela.setValue("class", thisMbo.getString("woclass"), 11L);
			rela.setValue("RELATEDRECKEY", wo.getString("wonum"), 11L);
			rela.setValue("relatedrecWonum", wo.getString("wonum"), 11L);
			rela.setValue("relatedrecclass", wo.getString("woclass"), 11L);
			rela.setValue("RELATEDRECWOCLASS", thisMbo.getString("woclass"), 11L);
			rela.setValue("relatedrecsiteid", thisMbo.getString("siteid"), 11L);
			rela.setValue("relatedrecorgid", thisMbo.getString("orgid"), 11L);
			rela.setValue("relatetype", "后续", 11L);
			relaMboSet.save();
			appBean.save();
			appBean.reset();
			appBean.next();
			this.sessionContext.queueRefreshEvent();
			this.sessionContext.queueRefreshEvent();
			WebClientSession wcs = event.getWebClientSession();
			String additionalEvent = event.getAdditionalEvent();
			String additionalEventValue = event.getAdditionalEventValue();
			String queryString = "?event=gotoapp&value=HDWOTICKET";
			if (!WebClientRuntime.isNull(additionalEvent)) {
				queryString = queryString + "&additionalevent=" + additionalEvent;
				if (!WebClientRuntime.isNull(additionalEventValue))
					queryString = queryString + "&additionaleventvalue=" + additionalEventValue;
			}
			queryString = queryString + "&uniqueid=" + wo.getUniqueIDValue();
			wcs.getCurrentApp().put("forcereload", "true");
			wcs.gotoApplink(queryString);
			event.cancelRender();
		} catch (MXException mxe) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), mxe);
		}
		return 1;
	}

	@SuppressWarnings("deprecation")
	public int CreateWoTkDHER() throws MXException, RemoteException {
		MboRemote thisMbo = this.app.getAppBean().getMbo();
		WebClientEvent event = this.sessionContext.getCurrentEvent();
		DataBean appBean = Utility.getDataSource(this.sessionContext, this.app.getAppHandler());
		try {
			appBean.save();
			MboSetRemote woSet = thisMbo.getMboSet("showorder");
			MboRemote wo = woSet.add();
			wo.setValue("S_ORDERTYPE", "二级动火工作票", 11L);
			wo.setValue("WORKTYPE", "工作票");
			wo.setValue("parent", thisMbo.getString("wonum"), 11L);
			wo.setValue("S_PROFESSION", thisMbo.getString("S_PROFESSION"), 11L);
			wo.setValue("WORKSITE", thisMbo.getString("WORKSITE"));
			wo.setValue("DESCRIPTION", thisMbo.getString("DESCRIPTION"));
			woSet.save();
			MboSetRemote relaMboSet = thisMbo.getMboSet("RELATEDWO");
			MboRemote rela = relaMboSet.add();
			rela.setValue("recordkey", thisMbo.getString("wonum"), 11L);
			rela.setValue("class", thisMbo.getString("woclass"), 11L);
			rela.setValue("RELATEDRECKEY", wo.getString("wonum"), 11L);
			rela.setValue("relatedrecWonum", wo.getString("wonum"), 11L);
			rela.setValue("relatedrecclass", wo.getString("woclass"), 11L);
			rela.setValue("RELATEDRECWOCLASS", thisMbo.getString("woclass"), 11L);
			rela.setValue("relatedrecsiteid", thisMbo.getString("siteid"), 11L);
			rela.setValue("relatedrecorgid", thisMbo.getString("orgid"), 11L);
			rela.setValue("relatetype", "后续", 11L);
			relaMboSet.save();
			appBean.save();
			appBean.reset();
			appBean.next();
			this.sessionContext.queueRefreshEvent();
			this.sessionContext.queueRefreshEvent();
			WebClientSession wcs = event.getWebClientSession();
			String additionalEvent = event.getAdditionalEvent();
			String additionalEventValue = event.getAdditionalEventValue();
			String queryString = "?event=gotoapp&value=HDWOTICKET";
			if (!WebClientRuntime.isNull(additionalEvent)) {
				queryString = queryString + "&additionalevent=" + additionalEvent;
				if (!WebClientRuntime.isNull(additionalEventValue))
					queryString = queryString + "&additionaleventvalue=" + additionalEventValue;
			}
			queryString = queryString + "&uniqueid=" + wo.getUniqueIDValue();
			wcs.getCurrentApp().put("forcereload", "true");
			wcs.gotoApplink(queryString);
			event.cancelRender();
		} catch (MXException mxe) {
			Utility.showMessageBox(this.sessionContext.getCurrentEvent(), mxe);
		}
		return 1;
	}
}
