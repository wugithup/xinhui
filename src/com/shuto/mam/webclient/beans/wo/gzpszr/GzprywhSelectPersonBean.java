package com.shuto.mam.webclient.beans.wo.gzpszr;

import com.shuto.mam.app.expand.StringExpand;
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.MultiselectDataBean;
import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.controller.ControlInstance;
import psdi.webclient.system.controller.SessionContext;
import psdi.webclient.system.controller.WebClientEvent;

public class GzprywhSelectPersonBean extends MultiselectDataBean
{
  protected void initialize()
    throws MXException, RemoteException
  {
    super.initialize();

    if ((this.mboSetRemote != null) && (this.mboSetRemote.getApp() == null)) {
      String appName = this.app.getId();
      if ((appName != null) && 
        (this.mboSetRemote.isBasedOn("PERSON")))
      {
        this.mboSetRemote.setApp(appName.toUpperCase());
      }
    }
  }

  public int okUsers()
    throws MXException, RemoteException
  {
    String dataSrc = this.creatingEvent.getSourceControlInstance().getProperty("datasrc");
    DataBean dataBean = this.app.getDataBean(dataSrc);
    MboSetRemote msr = dataBean.getMboSet();

    MboRemote mbo = null;
    for (int xx = 0; (mbo = getMboSet().getMbo(xx)) != null; xx++) {
      if (!mbo.isSelected())
        continue;
      MboRemote greMbo = msr.add();
      greMbo.setValue("PERSONID", mbo.getString("personid"), 11L);
      greMbo.setValue("S_DEPARTMENTSID", mbo.getString("DEPARTMENT"), 11L);
      greMbo.setValue("DESCRIPTION", this.app.getAppBean().getMbo().getString("DESCRIPTION"), 11L);
    }

    this.app.getDataBean(dataSrc).refreshTable();
    this.sessionContext.queueEvent(new WebClientEvent("dialogok", this.sessionContext.getCurrentPageId(), "", this.sessionContext));

    return 1;
  }

  protected MboSetRemote getMboSetRemote()
    throws MXException, RemoteException
  {
    MboRemote mbo = this.app.getAppBean().getMbo();

    String dataSrc = this.creatingEvent.getSourceControlInstance().getProperty("datasrc");

    String value = this.creatingEvent.getSourceControlInstance().getProperty("value");
    DataBean dataBean = this.app.getDataBean(dataSrc);
    MboSetRemote mboSet = null;
    if (dataBean.getMboSet().count() > 0)
    {
      if (StringExpand.binarySearch(new String[] { "FZR", "QFR", "XKR" ,"AJR"}, value)) {
        String sql = " personid not in (select PERSONID from  WOTICKLINE where TYPE = :1 and SITEID=:2 and DESCRIPTION=:3)  and status  = '活动' and LOCATIONSITE=:4";
        SqlFormat sqf = new SqlFormat(mbo.getUserInfo(), sql);
        sqf.setObject(1, "WOTICKLINE", "TYPE", dataBean.getMbo(0).getString("TYPE"));
        sqf.setObject(2, "WOTICKLINE", "SITEID", dataBean.getMbo(0).getString("SITEID"));
        sqf.setObject(3, "WOTICKLINE", "DESCRIPTION", dataBean.getMbo(0).getString("DESCRIPTION"));
        sqf.setObject(4, "WOTICKLINE", "SITEID", dataBean.getMbo(0).getString("SITEID"));
        mboSet = mbo.getMboSet("$PERSON_SELECT", "PERSON", sqf.format());
      } else {
        String sql = " personid not in (select PERSONID from  WOTICKLINE where TYPE = :1 and SITEID=:2)  and status  = '活动' and LOCATIONSITE=:3";
        SqlFormat sqf = new SqlFormat(mbo.getUserInfo(), sql);
        sqf.setObject(1, "WOTICKLINE", "TYPE", dataBean.getMbo(0).getString("TYPE"));
        sqf.setObject(2, "WOTICKLINE", "SITEID", dataBean.getMbo(0).getString("SITEID"));
        sqf.setObject(3, "WOTICKLINE", "SITEID", dataBean.getMbo(0).getString("SITEID"));
        mboSet = mbo.getMboSet("$PERSON_SELECT", "PERSON", sqf.format());
      }
    }
    else {
      String sql = "  status  = '活动' and LOCATIONSITE=:1";
      SqlFormat sqf = new SqlFormat(mbo.getUserInfo(), sql);
      sqf.setObject(1, "PERSON", "LOCATIONSITE", mbo.getString("SITEID"));
      mboSet = mbo.getMboSet("$PERSON_SELECT", "PERSON", sqf.format());
    }

    return mboSet;
  }
}