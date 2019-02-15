package com.shuto.mam.webclient.beans.wo.woticket;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class WosafetylinkDialogDateBean extends DataBean
{
  protected MboSetRemote getMboSetRemote()
    throws MXException, RemoteException
  {
    MboRemote mbo = this.app.getAppBean().getMbo();
    MboSetRemote wosafetylink = mbo.getMboSet("HAZARD");
    String orgid = mbo.getString("ORGID");
    String sql = " ORGID = '" + orgid + "'";
    wosafetylink.setWhere(sql);
    return wosafetylink;
  }

  public int execute() throws MXException, RemoteException {
    super.execute();
    MboRemote mainMbo = this.app.getAppBean().getMbo();
    DataBean databean = this.app.getDataBean("WOHazPrec_table");
    MboSetRemote prowos = mainMbo.getMboSet("WOSLHAZPRECENABLED");

    MboSetRemote selectLines = getMboSet();

    selectLines.resetWithSelection();
    MboRemote prowo = null;
    for (int i = 0; i < selectLines.count(); ++i) {
      MboRemote wombo = selectLines.getMbo(i);

      prowo = prowos.add();

      prowo.setValue("wonum", mainMbo.getString("wonum"), 11L);
      prowo.setValue("HAZARDDESCRIPTION", wombo.getString("DESCRIPTION"), 
        11L);
      prowo.setValue("hazardid", wombo.getString("hazardid"), 11L);
      prowo.setValue("HAZARDTYPE", wombo.getString("HAZARDTYPE"), 11L);
    }

    prowos.save();
    databean.reloadTable();
    databean.refreshTable();
    return 1;
  }
}