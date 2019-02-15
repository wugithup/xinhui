package com.shuto.mam.webclient.beans.workplan;

import java.rmi.RemoteException;
import java.util.Vector;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class PersonMultiDataBean extends DataBean
{
  MboRemote currMbo = null;
  MboSetRemote lines = null;
  MboRemote currLine = null;

  public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
    MboSetRemote retpersons = super.getMboSetRemote();
    this.currMbo = this.app.getAppBean().getMbo();
    this.lines = this.currMbo.getMboSet("workplanline");
    this.currLine = this.lines.getMbo(this.lines.getCurrentPosition());

    String redept = this.currLine.getString("REDEPARTMENT");
    String reperson = this.currLine.getString("REPERSON");

    MboSetRemote respersons = this.currLine.getMboSet("RESOURCESPERSON");
    MboSetRemote resdepts = this.currLine.getMboSet("RESOURCESDEPARTMENT");
    String sql = "1=1";
    String depSql = "";
    if (!redept.equals("")) {
      depSql = " AND (Department ='" + redept + "'";
    }

    MboRemote resource = null;
    if (!resdepts.isEmpty()) {
      for (int i = 0; i < resdepts.count(); ++i) {
        resource = resdepts.getMbo(i);
        if (!resource.getString("department").equals("")) {
          if (i == 0)
            if (!redept.equals(""))
              depSql = depSql + " or department ='" + resource.getString("department") + "' ";
            else
              depSql = depSql + " and (department ='" + resource.getString("department") + "' ";
          else {
            depSql = depSql + " or department ='" + resource.getString("department") + "' ";
          }
        }
      }
    }
    if (!depSql.equals("")) {
      sql = sql + depSql + ")";
    }

    if (!reperson.equals("")) {
      sql = sql + " and personid <>'" + reperson + "'";
    }

    String person = "";
    for (int j = 0; j < respersons.count(); ++j) {
      person = respersons.getMbo(j).getString("personid");
      if (!person.equals(""))
        sql = sql + " AND personid<>'" + person + "'";
    }
    retpersons.setWhere(sql);
    retpersons.reset();
    retpersons.save();
    this.app.getAppBean().save();
    return retpersons;
  }

  public int execute() throws MXException, RemoteException {
    super.execute();
    Vector selectedpersonLines = getMboSet().getSelection();

    for (int i = 0; i < selectedpersonLines.size(); ++i) {
      MboRemote maxuser = (MboRemote)selectedpersonLines.elementAt(i);
      MboSetRemote workplanlineSetRemote = this.currLine.getMboSet("RESOURCESPERSON");

      MboRemote personline = workplanlineSetRemote.add();
      personline.setValue("personid", maxuser.getString("personid"), 11L);
    }
    this.app.getDataBean("1275707774125").reloadTable();
    this.app.getDataBean("1275707774125").refreshTable();
    this.app.getDataBean("1275707774125").save();
    this.app.getAppBean().save();
    return 1;
  }
}