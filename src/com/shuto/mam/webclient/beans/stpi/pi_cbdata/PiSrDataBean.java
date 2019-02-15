package com.shuto.mam.webclient.beans.stpi.pi_cbdata;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class PiSrDataBean extends DataBean
{
  public int addrow()
    throws MXException
  {
    try
    {
      MboRemote mainMbo = this.app.getAppBean().getMbo();
      MboSetRemote set = mainMbo.getMboSet("SR");
      if (set.isEmpty())
      {
        super.addrow();
        String pi_description = mainMbo.getString("description");
        String remark = mainMbo.getString("remark");
        String check_method = mainMbo.getString("CHECK_METHOD");
        int doubleret = mainMbo.getInt("DOUBLERET");
        String point_unit = mainMbo.getString("POINT_UNIT");
        pi_description = pi_description.replace("_", "");

        String createby = mainMbo.getUserInfo().getPersonId();
        MboRemote srMbo = set.addAtEnd();
        String ticketid = srMbo.getString("TICKETID");

        if (remark.equals("null")) {
          remark = "";
        }
        if (check_method.equals("观察"))
          srMbo.setValue("DESCRIPTION", pi_description + "异常" + " " + remark, 2L);
        else {
          srMbo.setValue("DESCRIPTION", pi_description + " " + doubleret + point_unit + "异常" + " " + remark, 2L);
        }
        srMbo.setValue("REPORTEDBY", createby, 2L);
        srMbo.setValue("SOURCE", "缺陷", 2L);
        srMbo.setValue("siteid", mainMbo.getString("siteid"), 2L);
        srMbo.setValue("orgid", mainMbo.getString("orgid"), 2L);
        srMbo.setValue("status", "新建", 2L);

        mainMbo.setValue("TICKETID", ticketid);

        set.save();
        mainMbo.getThisMboSet().save();
      } else {
        throw new MXApplicationException("xdj", "缺陷已存在!");
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new MXApplicationException("xdj", e.getMessage());
    }

    return 1;
  }
}