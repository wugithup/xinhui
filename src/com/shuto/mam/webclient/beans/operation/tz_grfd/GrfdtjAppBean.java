package com.shuto.mam.webclient.beans.operation.tz_grfd;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class GrfdtjAppBean extends AppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    return super.INSERT();
  }

  public int SAVE() throws MXException, RemoteException
  {
    MboRemote localMboRemote = this.app.getAppBean().getMbo();

    MboSetRemote localMboSetRemote = localMboRemote.getMboSet("TZ_GRFDTJLINE");

    double d1 = localMboSetRemote.getDouble("FDYQ");
    double d2 = localMboSetRemote.getDouble("FDEQ");
    double d3 = d1 + d2;
    localMboSetRemote.setValue("FDZFDL", d3, 11L);

    double d4 = localMboSetRemote.getDouble("GRYWMYQ");
    double d5 = localMboSetRemote.getDouble("GRYWEQ");
    double d6 = d4 + d5;
    localMboSetRemote.setValue("GRYWZJ", d6, 11L);

    double d7 = localMboSetRemote.getDouble("GRSSM");
    double d8 = d7 + d4;
    localMboSetRemote.setValue("GRYQ", d8, 11L);

    double d9 = localMboSetRemote.getDouble("GREWM");
    double d10 = d9 + d5;
    localMboSetRemote.setValue("GREQ", d10, 11L);

    double d11 = d8 + d10;
    localMboSetRemote.setValue("GRZJ", d11, 11L);

    double d12 = d11 / 24.0D;
    localMboSetRemote.setValue("SHIJUN", d12, 11L);

    double d13 = localMboSetRemote.sum("FDYQ");
    localMboRemote.setValue("Y_FDYQ", d13);

    double d14 = localMboSetRemote.sum("FDEQ");
    localMboRemote.setValue("Y_FDEQ", d14);

    double d15 = localMboSetRemote.sum("FDZFDL");
    localMboRemote.setValue("Y_FDZFDL", d15);

    double d16 = localMboSetRemote.sum("GRSSM");
    localMboRemote.setValue("Y_GRSSM", d16);

    double d17 = localMboSetRemote.sum("GREWM");
    localMboRemote.setValue("y_GREWM", d17);

    double d18 = localMboSetRemote.sum("GRYWMYQ");
    localMboRemote.setValue("y_GRYWMYQ", d18);

    double d19 = localMboSetRemote.sum("GRYWEQ");
    localMboRemote.setValue("y_GRYWEQ", d19);

    double d20 = localMboSetRemote.sum("GRYWZJ");
    localMboRemote.setValue("y_GRYWZJ", d20);

    double d21 = localMboSetRemote.sum("GRYQ");
    localMboRemote.setValue("y_GRYQ", d21);

    double d22 = localMboSetRemote.sum("GREQ");
    localMboRemote.setValue("y_GREQ", d22);

    double d23 = localMboSetRemote.sum("GRZJ");
    localMboRemote.setValue("y_GRZJ", d23);

    double d24 = localMboRemote.getDouble("Y_FDYQ");
    localMboRemote.setValue("Y_SJ_FDYQ", d24 / (localMboSetRemote.count() * 24));

    double d25 = localMboRemote.getDouble("Y_FDEQ");
    localMboRemote.setValue("Y_SJ_FDEQ", d25 / (localMboSetRemote.count() * 24));

    double d26 = localMboRemote.getDouble("Y_FDZFDL");
    localMboRemote.setValue("Y_SJ_FDZFDL", d26 / (localMboSetRemote.count() * 24));

    double d27 = localMboRemote.getDouble("Y_GRSSM");
    localMboRemote.setValue("y_SJ_GRSSM", d27 / (localMboSetRemote.count() * 24));

    double d28 = localMboRemote.getDouble("y_GREWM");
    localMboRemote.setValue("y_SJ_GREWM", d28 / (localMboSetRemote.count() * 24));

    double d29 = localMboRemote.getDouble("y_GRYWMYQ");
    localMboRemote.setValue("y_SJ_GRYWMYQ", d29 / (localMboSetRemote.count() * 24));

    double d30 = localMboRemote.getDouble("y_GRYWEQ");
    localMboRemote.setValue("y_SJ_GRYWEQ", d30 / (localMboSetRemote.count() * 24));

    double d31 = localMboRemote.getDouble("y_GRYWZJ");
    localMboRemote.setValue("y_SJ_GRYWZJ", d31 / (localMboSetRemote.count() * 24));

    double d32 = localMboRemote.getDouble("y_GRYQ");
    localMboRemote.setValue("y_SJ_GRYQ", d32 / (localMboSetRemote.count() * 24));

    double d33 = localMboRemote.getDouble("y_GREQ");
    localMboRemote.setValue("y_SJ_GREQ", d33 / (localMboSetRemote.count() * 24));

    double d34 = localMboRemote.getDouble("y_GRZJ");
    localMboRemote.setValue("y_SJ_GRZJ", d34 / (localMboSetRemote.count() * 24));

    localMboRemote.setValue("TJ_TIME", MXServer.getMXServer().getDate());

    this.app.getAppBean().reloadTable();

    this.app.getAppBean().refreshTable();
    super.SAVE();
    this.app.getAppBean().fireDataChangedEvent();

    this.app.getAppBean().fireStructureChangedEvent();
    this.app.getAppBean().fireChildChangedEvent();

    return 1;
  }
}