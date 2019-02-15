package com.shuto.mam.webclient.beans.reports;

import com.shuto.mam.app.reports.ReportsDataMboRemote;
import com.shuto.mam.webclient.beans.base.CAppBean;
import java.rmi.RemoteException;
import java.util.Date;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;


public class ReportsDataAppBean extends CAppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    super.SAVE();

    return 1;
  }

  public void makeReport() throws RemoteException, MXException {
    ReportsDataMboRemote mbo = (ReportsDataMboRemote)getMbo();
    Date mkdate = mbo.getDate("MKDATE");
    if ((mkdate == null) || (mbo.isNull("REPORTSID"))) {
      throw new MXApplicationException("", "请先选择报表和日期!");
    }
    mbo.makeReport(false);
    this.app.getAppBean().reloadTable();
    this.app.getAppBean().refreshTable();
  }

  public void jisuanReport() throws RemoteException, MXException {
    ReportsDataMboRemote mbo = (ReportsDataMboRemote)getMbo();
    Date mkdate = mbo.getDate("MKDATE");
    if ((mkdate == null) || (mbo.isNull("REPORTSID"))) {
      throw new MXApplicationException("", "请先选择报表和日期!");
    }
    mbo.makeReport(true);
    mbo.makeReport(true);

    this.app.getAppBean().reloadTable();
    this.app.getAppBean().refreshTable();
  }

  public void selectReport() throws RemoteException, MXException {
    DataBean dataBean = this.app.getDataBean("id_reportszbdata");
    dataBean.setAppWhere("");
    dataBean.reloadTable();
    dataBean.refreshTable();
  }

  public void allInsertReport() throws RemoteException, MXException {
    DataBean dataBean = this.app.getDataBean("id_reportszbdata");
    dataBean.setAppWhere("reportszbid in (select reportszbid from reportszb where reportszb.formula is null and reportszb.sqlstr is null)");
    dataBean.reloadTable();
    dataBean.refreshTable();
  }

  public void insertReport() throws RemoteException, MXException {
    DataBean dataBean = this.app.getDataBean("id_reportszbdata");
    dataBean.setAppWhere("reportszbid in (select reportszbid from reportszb where reportszb.formula is null and reportszb.sqlstr is null) and value is null");
    dataBean.reloadTable();
    dataBean.refreshTable();
  }
}