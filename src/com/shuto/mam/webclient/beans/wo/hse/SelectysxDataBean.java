package com.shuto.mam.webclient.beans.wo.hse;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class SelectysxDataBean extends DataBean
{
  protected MboSetRemote getMboSetRemote()
    throws MXException, RemoteException
  {
    MboRemote thismbo = this.app.getAppBean().getMbo();

    String wonum = thismbo.getString("wonum");

    String siteid = thismbo.getString("siteid");

    MboSetRemote mboset = MXServer.getMXServer().getMboSet("HSE_MAINTAINLIST", MXServer.getMXServer().getUserInfo("maxadmin"));

    mboset.setWhere("BARCODEID in (select ysxid from v_activity_wo where wonum='"+wonum+"' and ysxid is not null group by ysxid)");
    return mboset;
  }
}