package com.shuto.mam.webclient.beans.ticket;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Date;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.util.MXException;
import psdi.webclient.beans.servicedesk.TicketAppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.controller.SessionContext;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;

public class TicketsAppBean extends TicketAppBean
{
  protected void setCurrentRecordData(MboRemote mbo)
    throws MXException, RemoteException
  {
    String status = mbo.getString("status");
    System.out.println(status);
    String[] attrs = { "TICKETID", "LOCATION", "REPORTEDPRIORITY", "DESCRIPTION", "S_PROFESSION", "S_YXJK", 
      "S_WWZY", "S_SRFXR", "S_YXZB", "S_FINISHREPORT", "S_GZZJ", "S_YXYS", 
      "S_XQPERSON", "TEAMNAME", "FAILURECODE", "PERSON.PRIMARYPHONE", 
      "S_YQTODATE", "S_YQMEMO", "GUAQITYPE", "S_GQMEMO" };
    mbo.setFieldFlag(attrs, 7L, true);
    if (status.equals("新建")) {
      String[] attr = { "LOCATION", "REPORTEDPRIORITY", "DESCRIPTION", "S_PROFESSION", "S_YXJK", 
        "S_SRFXR", "S_YXZB", "FAILURECODE", "PERSON.PRIMARYPHONE" };
      String[] rqattr = { "LOCATION", "REPORTEDPRIORITY", "DESCRIPTION", "S_PROFESSION", "S_YXJK" };
      mbo.setFieldFlag(attr, 7L, false);
      mbo.setFieldFlag(rqattr, 128L, true);
    }
    super.setCurrentRecordData(mbo);
  }

  public int CREATEWOT()
    throws MXException, RemoteException
  {
    WebClientEvent event = this.sessionContext.getCurrentEvent();
    DataBean appBean = Utility.getDataSource(this.sessionContext, 
      this.app.getAppHandler());
    try {
      appBean.save();
      MboRemote wo = null;
      String ticketId = "";
      MboRemote mbo = this.app.getAppBean().getMbo();
      mbo.setValue("S_JSDATE", new Date());
      mbo.setValue("S_JSPERSON", mbo.getUserInfo().getPersonId(), 2L);
      MboSetRemote mboSet = mbo.getMboSet("MAXWO");
      String wtdescription = "由缺陷" + mbo.getString("TICKETID") + "创建的工单" + mbo.getString("DESCRIPTION");
      wo = mboSet.add();
      wo.setValue("worktype", "缺陷工单");
      wo.setValue("description", wtdescription, 2L);
      wo.setValue("location", getString("location"), 2L);
      wo.setValue("assetnum", getString("assetnum"), 2L);
      wo.setValue("ORIGRECORDID", mbo.getString("TICKETID"), 2L);
      wo.setValue("ORIGRECORDCLASS", "SR", 2L);
      wo.setValue("teamname", getString("teamname"), 11L);

      wo.setValue("parentchgsstatus", false);
      wo.setValue("s_profession", getString("s_profession"), 11L);
      wo.setValue("s_jizu", getString("S_YXJK"), 11L);

      ticketId = String.valueOf(wo.getUniqueIDValue());
      mboSet.save();

      appBean.save();
      appBean.reset();
      appBean.next();

      this.sessionContext.queueRefreshEvent();
      this.sessionContext.queueRefreshEvent();

      WebClientSession wcs = event.getWebClientSession();
      String additionalEvent = event.getAdditionalEvent();
      String additionalEventValue = event.getAdditionalEventValue();
      String queryString = "?event=gotoapp&value=WOTRACK";

      if (!WebClientRuntime.isNull(additionalEvent)) {
        queryString = queryString + "&additionalevent=" + 
          additionalEvent;
        if (!WebClientRuntime.isNull(additionalEventValue))
          queryString = queryString + "&additionaleventvalue=" + 
            additionalEventValue;
      }
      queryString = queryString + "&uniqueid=" + ticketId;

      wcs.getCurrentApp().put("forcereload", "true");
      wcs.gotoApplink(queryString);
      event.cancelRender();
    } catch (MXException mxe) {
      Utility.showMessageBox(this.sessionContext.getCurrentEvent(), mxe);
    }

    return 1;
  }
}