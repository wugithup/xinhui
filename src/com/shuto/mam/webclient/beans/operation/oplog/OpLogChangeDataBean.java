/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.webclient.beans.operation.oplog;

import com.shuto.mam.app.operation.oplog.OpLog;
import psdi.app.signature.HiddenValueSet;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Date;

public class OpLogChangeDataBean extends DataBean
{
  MboRemote person1 = null;

  MboRemote person2 = null;
  HiddenValueSet hiddenValues = null;

  boolean icanCarryon = true;

    public OpLogChangeDataBean() {
  }

  public int execute()
    throws MXException, RemoteException
  {
    OpLog oplog = (OpLog)this.parent.getMbo();

    Date sysdate = new Date();

    String login1 = oplog.getOnDutyPerson().getMboSet("USER").getMbo(0).getString("loginid");

    String login2 = getMbo().getUserInfo().getLoginID();

    MboSetRemote login1s = getMbo().getMboSet("$login1", "maxuser", "loginid = '" + login1 + "'");
    this.person1 = login1s.getMbo(0).getMboSet("person").getMbo(0);

    MboSetRemote login2s = getMbo().getMboSet("$login2", "maxuser", "loginid='" + login2 + "'");
    this.person2 = login2s.getMbo(0).getMboSet("person").getMbo(0);
    try
    {
      this.icanCarryon = oplog.canCarryon(this.person2);
    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    }

    if (this.icanCarryon)
    {
      MboRemote nextperiod = oplog.nextPeriod();

      MboRemote nextshift = oplog.getShift(nextperiod);

      String JBPERSON = this.person1.getString("personid");
      String JIEBPERSON = this.person2.getString("personid");

        MboRemote newoplog = oplog.getThisMboSet().addAtIndex(0);

      newoplog.setValue("oplogtype", oplog.getString("oplogtype"));

      newoplog.setValue("zqdate", nextperiod.getString("workdate"));

      newoplog.setValue("zbstartdate", nextperiod.getDate("starttime"));
      newoplog.setValue("zbenddate", nextperiod.getDate("endtime"));
      newoplog.getDate("zbstartdate");

      newoplog.setValue("dbrperson", this.person2.getString("personid"));

      newoplog.setValue("zhibie", nextperiod.getString("SHIFTNUM"));

      newoplog.setValue("status", "ONDUTY");

      newoplog.setValue("banci", nextshift.getString("SHIFTNUM"), 11L);
      try
      {
          oplog.copyAssetAndPointToNewOplog(oplog, newoplog);
        oplog.getThisMboSet().save();

        oplog = (OpLog)this.parent.getMbo();

        oplog.setFieldFlag("jiebperson", 7L, false);
        oplog.setValue("jiebperson", JIEBPERSON);

        oplog.setFieldFlag("jiebdate", 7L, false);
        oplog.setValue("jiebdate", sysdate);

        oplog.setValue("status", "CLOSE", 11L);

        DataBean appBean = this.app.getAppBean();
        if (appBean instanceof AppBean) {
          ((AppBean)appBean).SAVE();
        }

        Utility.showMessageBox(this.clientSession.getCurrentEvent(), "", "     接班完成!。", 0);
        this.sessionContext.queueRefreshEvent();

        ((AppBean)appBean).moveToUniqueId(newoplog.getLong("oplogid"));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return 1;
  }
}